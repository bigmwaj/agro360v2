package com.agro360.operation.logic.stock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.InventaireBean;
import com.agro360.bo.bean.stock.InventaireSearchBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IConversionDao;
import com.agro360.dao.stock.IInventaireDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.stock.ConversionDto;
import com.agro360.dto.stock.ConversionPk;
import com.agro360.dto.stock.InventaireDto;
import com.agro360.dto.stock.InventairePk;
import com.agro360.dto.stock.VariantDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class InventaireOperation extends AbstractOperation<InventaireDto, InventairePk> {

	private static final String FACTEUR_SOURCE_BASE = "F1";
	
	private static final String FACTEUR_BASE_CIBLE = "F2";
	
	@Autowired
	private IInventaireDao dao;
	
	@Autowired
	private IMagasinDao magasinDao;
	
	@Autowired
	private IArticleDao articleDao;
	
	@Autowired
	private IUniteDao uniteDao;

	@Override
	protected IDao<InventaireDto, InventairePk> getDao() {
		return dao;
	}

	@RuleNamespace("stock/inventaire/create")
	public void createInventaire(ClientContext ctx, InventaireBean bean) {
		var dto = new InventaireDto();
		
		var magasinCode = bean.getMagasin().getMagasinCode().getValue();
		var magasin = magasinDao.getReferenceById(magasinCode);
		dto.setMagasin(magasin);
		
		var articleCode = bean.getArticle().getArticleCode().getValue();
		var article = articleDao.getReferenceById(articleCode);
		dto.setArticle(article);
		
		var uniteAchatCode = bean.getUniteAchat().getUniteCode().getValue();
		var uniteAchat = uniteDao.getReferenceById(uniteAchatCode);
		dto.setUniteAchat(uniteAchat);
		
		var uniteVenteCode = bean.getUniteVente().getUniteCode().getValue();
		var uniteVente = uniteDao.getReferenceById(uniteVenteCode);
		dto.setUniteVente(uniteVente);
		
		setDtoValue(dto::setVariantCode, bean.getVariantCode());
		setDtoValue(dto::setQuantite, bean.getQuantite());
		setDtoValue(dto::setPrixUnitaireAchat, bean.getPrixUnitaireAchat());
		setDtoValue(dto::setPrixUnitaireVente, bean.getPrixUnitaireVente());
		
		super.save(dto);		
	}

	@Autowired
	private IConversionDao conversionDao;
	
	private Map<String, Double> getFacteurs(ClientContext ctx, String articleCode, String uniteSource, String uniteBase, String uniteCible) {
		// Conversion de la source vers la base
		Double facteurSourceBase = null;
		if( uniteBase.equals(uniteSource) ) {
			facteurSourceBase = Double.valueOf(1);
		}else {
			var pk = new ConversionPk(articleCode, uniteSource);
			var msgTpl = "Il n'existe aucun facteur de conversion de l'unite %s vers l'unité %s pour l'article %s";
			Supplier<RuntimeException> ex = () -> new RuntimeException(String.format(msgTpl, uniteSource, uniteCible, articleCode));
			facteurSourceBase = conversionDao.findById(pk).map(ConversionDto::getFacteur).orElseThrow(ex);
		}
		
		// Conversion de la base vers la cible
		Double facteurBaseCible = null;
		if( uniteBase.equals(uniteCible) ) {
			facteurBaseCible = Double.valueOf(1);
		}else {
			var pk = new ConversionPk(articleCode, uniteCible);
			var msgTpl = "Il n'existe aucun facteur de conversion de l'unite %s vers l'unité %s pour l'article %s";
			Supplier<RuntimeException> ex = () -> new RuntimeException(String.format(msgTpl, uniteSource, uniteCible, articleCode));
			facteurBaseCible = conversionDao.findById(pk).map(ConversionDto::getFacteur).orElseThrow(ex);
		}
		return Map.of(FACTEUR_SOURCE_BASE, facteurSourceBase, FACTEUR_BASE_CIBLE, facteurBaseCible);
	}
	
	@RuleNamespace("stock/inventaire/update-quantite")
	public void updateInventaireQuantite(ClientContext ctx, InventaireBean bean) {
		var dto = dao.getReferenceById(buildPK(bean));
		var uniteCible = dto.getUniteAchat().getUniteCode();
		var uniteSource = bean.getUniteAchat().getUniteCode().getValue();
		var quantite = bean.getQuantite().getValue();
		if( !uniteCible.equals(uniteSource) ) {
			var uniteBase = dto.getArticle().getUnite().getUniteCode();
			var articleCode = dto.getArticle().getArticleCode();
			var facteurMap = getFacteurs(ctx, articleCode, uniteSource, uniteBase, uniteCible);
			var f1 = facteurMap.get(FACTEUR_SOURCE_BASE);
			var f2 = facteurMap.get(FACTEUR_BASE_CIBLE);
			quantite =  new BigDecimal(quantite).multiply(new BigDecimal(f1)).divide(new BigDecimal(f2), 2, RoundingMode.HALF_UP).doubleValue();
		}
		
		dto.setQuantite(quantite);
		
		super.save(dto);		
	}
	
	@RuleNamespace("stock/inventaire/update-prix-vente")
	public void updateInventairePrixVente(ClientContext ctx, InventaireBean bean) {
		var dto = dao.getReferenceById(buildPK(bean));
		var uniteCible = dto.getUniteAchat().getUniteCode();
		var uniteSource = bean.getUniteAchat().getUniteCode().getValue();
		var puv = bean.getPrixUnitaireVente().getValue();
		
		if( !uniteCible.equals(uniteSource) ) {
			var uniteBase = dto.getArticle().getUnite().getUniteCode();
			var articleCode = dto.getArticle().getArticleCode();
			var facteurMap = getFacteurs(ctx, articleCode, uniteSource, uniteBase, uniteCible);
			var f1 = facteurMap.get(FACTEUR_SOURCE_BASE);
			var f2 = facteurMap.get(FACTEUR_BASE_CIBLE);
			puv =  puv.multiply(new BigDecimal(f1)).divide(new BigDecimal(f2));
		}
		
		dto.setPrixUnitaireVente(puv);
		super.save(dto);		
	}
	
	@RuleNamespace("stock/inventaire/update-unite-vente")
	public void updateInventaireUniteVente(ClientContext ctx, InventaireBean bean) {
		var dto = dao.getReferenceById(buildPK(bean));
		var uniteVenteCode = bean.getUniteVente().getUniteCode().getValue();
		var uniteVente = uniteDao.getReferenceById(uniteVenteCode);
		dto.setUniteVente(uniteVente);
		super.save(dto);		
	}
	
	@RuleNamespace("stock/inventaire/update-unite-achat")
	public void updateInventaireUniteAchat(ClientContext ctx, InventaireBean bean) {
		var dto = dao.getReferenceById(buildPK(bean));
		var uniteAchatCode = bean.getUniteAchat().getUniteCode().getValue();
		var uniteAchat = uniteDao.getReferenceById(uniteAchatCode);
		dto.setUniteAchat(uniteAchat);
		super.save(dto);		
	}

	public InventaireBean findInventaireByCode(ClientContext ctx, String magasinCode, String articleCode, String variantCode) {
		var dto = dao.getReferenceById(buildPK(magasinCode, articleCode, variantCode));
		return StockMapper.map(dto);	
	}
	
	@Deprecated
	public InventaireBean findInventaireByCode(ClientContext ctx, String magasinCode, String articleCode, String variantCode, String uniteCode) {
		var dto = dao.getReferenceById(buildPK(magasinCode, articleCode, variantCode));
		
		return StockMapper.map(dto);	
	}
		
	public List<InventaireBean> findInventairesByCriteria(ClientContext ctx, InventaireSearchBean searchBean) {
		var article = searchBean.getArticleCode().getValue();
		if( article != null ) {
			article = article.toUpperCase();
		}
		
		var magasin = searchBean.getMagasinCode().getValue();
		if( magasin != null ) {
			magasin = magasin.toUpperCase();
		}
        var length = dao.countInventairesByCriteria(magasin, article);
        searchBean.setLength(length);
        return dao.findInventairesByCriteria(searchBean.getOffset(), searchBean.getLimit(), magasin, article)
        		.stream().map(StockMapper::map)
        		.collect(Collectors.toList());
	}
	
	private InventairePk buildPK(String magasinCode, String articleCode, String variantCode) {
		return new InventairePk(magasinCode, articleCode, variantCode);
	}

	private InventairePk buildPK(InventaireBean bean) {
		var magasinCode = bean.getMagasin().getMagasinCode().getValue();
		var articleCode = bean.getArticle().getArticleCode().getValue();
		var variantCode = bean.getVariantCode().getValue();
		
		return buildPK(magasinCode, articleCode, variantCode);
	}

	public List<ArticleBean> findNonStockedArticles(ClientContext ctx, String magasinCode) {
		return dao.findNonStockedArticles(magasinCode)
				.stream()
				.map(StockMapper::map)
				.collect(Collectors.toList());
	}
	
	private InventaireBean mapFromVariant(ClientContext ctx, String magasinCode, VariantDto dto) {
		var bean = new InventaireBean();
		bean.getMagasin().getMagasinCode().setValue(magasinCode);
		bean.getArticle().getArticleCode().setValue(dto.getArticleCode());
		bean.getVariantCode().setValue(dto.getVariantCode());
		bean.getAlias().setValue(dto.getAlias());
		bean.getVariantDescription().setValue(dto.getDescription());
		return bean;
	}

	public List<InventaireBean> findNonStockedArticleVariants(ClientContext ctx, String magasinCode, String articleCode) {
		Function<VariantDto, InventaireBean> map = e -> mapFromVariant(ctx, magasinCode, e);
		return dao.findNonStockedArticleVariants(magasinCode, articleCode)
				.stream()
				.map(map)
				.collect(Collectors.toList());
	}
	
	public BigDecimal getPrixUnitaireAchat(ClientContext ctx, String magasinCode, String articleCode, String variantCode, String uniteCode) {
		var pk = buildPK(magasinCode, articleCode, variantCode);
		var dto = dao.getReferenceById(pk);
		var uniteCible = uniteCode;
		var uniteSource = dto.getUniteAchat().getUniteCode();
		var pu = dto.getPrixUnitaireAchat();
		
		if( !uniteCible.equals(uniteSource) ) {
			var uniteBase = dto.getArticle().getUnite().getUniteCode();
			var facteurMap = getFacteurs(ctx, articleCode, uniteSource, uniteBase, uniteCible);
			var f1 = facteurMap.get(FACTEUR_BASE_CIBLE);
			var f2 = facteurMap.get(FACTEUR_SOURCE_BASE);
			pu = pu.multiply(new BigDecimal(f1)).divide(new BigDecimal(f2), 2, RoundingMode.HALF_UP);
		}
		return pu;
	}
	
	public BigDecimal getPrixUnitaireVente(ClientContext ctx, String magasinCode, String articleCode, String variantCode, String uniteCode) {
		var pk = buildPK(magasinCode, articleCode, variantCode);
		var dto = dao.getReferenceById(pk);
		var uniteCible = uniteCode;
		var uniteSource = dto.getUniteVente().getUniteCode();
		var pu = dto.getPrixUnitaireVente();
		
		if( !uniteCible.equals(uniteSource) ) {
			var uniteBase = dto.getArticle().getUnite().getUniteCode();
			var facteurMap = getFacteurs(ctx, articleCode, uniteSource, uniteBase, uniteCible);
			var f1 = facteurMap.get(FACTEUR_BASE_CIBLE);
			var f2 = facteurMap.get(FACTEUR_SOURCE_BASE);
			pu = pu.multiply(new BigDecimal(f1)).divide(new BigDecimal(f2), 2, RoundingMode.HALF_UP);
		}
		return pu;
	}
}
