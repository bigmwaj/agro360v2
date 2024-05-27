package com.agro360.operation.logic.stock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
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

	@Autowired
	private IInventaireDao dao;
	
	@Autowired
	private IMagasinDao magasinDao;
	
	@Autowired
	private IArticleDao articleDao;
	
	@Autowired
	private IUniteDao uniteDao;

	@Autowired
	private IConversionDao conversionDao;

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
		
		var uniteStockCode = bean.getUniteStock().getUniteCode().getValue();
		var uniteStock = uniteDao.getReferenceById(uniteStockCode);
		dto.setUniteStock(uniteStock);
		
		setDtoValue(dto::setVariantCode, bean.getVariantCode());
		setDtoValue(dto::setQuantite, bean.getQuantite());
		setDtoValue(dto::setPrixUnitaireAchat, bean.getPrixUnitaireAchat());
		setDtoValue(dto::setPrixUnitaireVente, bean.getPrixUnitaireVente());
		
		super.save(ctx, dto);		
	}
	
	@RuleNamespace("stock/inventaire/update-quantite-stock")
	public void updateInventaireQuantite(ClientContext ctx, InventaireBean bean) {
		var dto = dao.getReferenceById(buildPK(bean));
		var uniteStock = dto.getUniteStock().getUniteCode();
		var uniteInput = bean.getUniteStock().getUniteCode().getValue();
		var qteInput = bean.getQuantite().getValue();
		if( !uniteStock.equals(uniteInput) ) {
			var articleCode = dto.getArticle().getArticleCode();
			var facteur = getFacteur(ctx, articleCode, uniteInput, uniteStock);
			qteInput = new BigDecimal(qteInput).multiply(facteur).doubleValue();
		}
		
		dto.setQuantite(qteInput);
		
		super.save(ctx, dto);		
	}
	
	@RuleNamespace("stock/inventaire/update-prix-vente")
	public void updateInventairePrixVente(ClientContext ctx, InventaireBean bean) {
		var dto = dao.getReferenceById(buildPK(bean));
		var uniteVente = dto.getUniteVente().getUniteCode();
		var uniteInput = bean.getUniteVente().getUniteCode().getValue();
		var pu = bean.getPrixUnitaireVente().getValue();
		
		if( !uniteVente.equals(uniteInput) ) {
			var articleCode = dto.getArticle().getArticleCode();
			var facteur = getFacteur(ctx, articleCode, uniteInput, uniteVente);
			pu =  pu.multiply(facteur);
		}
		
		dto.setPrixUnitaireVente(pu);
		super.save(ctx, dto);		
	}
	
	@RuleNamespace("stock/inventaire/update-unite-vente")
	public void updateInventaireUniteVente(ClientContext ctx, InventaireBean bean) {
		var dto = dao.getReferenceById(buildPK(bean));
		var uniteVenteInput = bean.getUniteVente().getUniteCode().getValue();
		var uniteVente = uniteDao.getReferenceById(uniteVenteInput);
		dto.setUniteVente(uniteVente);
		super.save(ctx, dto);		
	}
	
	@RuleNamespace("stock/inventaire/update-unite-achat")
	public void updateInventaireUniteAchat(ClientContext ctx, InventaireBean bean) {
		var dto = dao.getReferenceById(buildPK(bean));
		var uniteAchatInput = bean.getUniteAchat().getUniteCode().getValue();
		var uniteAchat = uniteDao.getReferenceById(uniteAchatInput);
		dto.setUniteAchat(uniteAchat);
		super.save(ctx, dto);		
	}
	
	@RuleNamespace("stock/inventaire/update-unite-stock")
	public void updateInventaireUniteStock(ClientContext ctx, InventaireBean bean) {
		var dto = dao.getReferenceById(buildPK(bean));
		var uniteStockCurr = dto.getUniteStock().getUniteCode();
		var qteCurr = dto.getQuantite();
		var uniteStockInput = bean.getUniteStock().getUniteCode().getValue();
		var uniteStock = uniteDao.getReferenceById(uniteStockInput);
		
		var facteur = getFacteur(ctx, dto.getArticle().getArticleCode(), uniteStockCurr, uniteStockInput);
		qteCurr = facteur.multiply(BigDecimal.valueOf(qteCurr)).doubleValue();
		
		dto.setUniteStock(uniteStock);
		dto.setQuantite(qteCurr);
		super.save(ctx, dto);		
	}

	public InventaireBean findInventaireByCode(ClientContext ctx, String magasinCode, String articleCode, String variantCode) {
		var dto = dao.getReferenceById(buildPK(magasinCode, articleCode, variantCode));
		return StockMapper.map(dto);	
	}
		
	public List<InventaireBean> findInventairesByCriteria(ClientContext ctx, InventaireSearchBean searchBean) {
		var article = searchBean.getArticleCode().getValue();
		article = searchBean.getNullOrUpperCase(article, "%");
		
		var magasin = searchBean.getMagasinCode().getValue();
		magasin = searchBean.getNullOrUpperCase(magasin);

		var length = dao.countInventairesByCriteria(magasin, article);
        searchBean.setLength(length);
        return dao.findInventairesByCriteria(searchBean.getOffset(), searchBean.getLimit(), magasin, article)
        		.stream().map(StockMapper::map)
        		.collect(Collectors.toList());
	}

	public List<ArticleBean> findNonStockedArticles(ClientContext ctx, String magasinCode) {
		return dao.findNonStockedArticles(magasinCode)
				.stream()
				.map(StockMapper::map)
				.collect(Collectors.toList());
	}

	public List<InventaireBean> findNonStockedArticleVariants(ClientContext ctx, String magasinCode, String articleCode) {
		Function<VariantDto, InventaireBean> map = e -> mapFromVariant(ctx, magasinCode, e);
		return dao.findNonStockedArticleVariants(magasinCode, articleCode)
				.stream()
				.map(map)
				.collect(Collectors.toList());
	}
	
	public BigDecimal getPrixUnitaireAchat(ClientContext ctx, String magasinCode, String articleCode, String variantCode, String uniteCommande) {
		var pk = buildPK(magasinCode, articleCode, variantCode);
		var dto = dao.getReferenceById(pk);
		var uniteAchat = dto.getUniteAchat().getUniteCode();
		var pu = dto.getPrixUnitaireAchat();
		
		if( !uniteCommande.equals(uniteAchat) ) {
			var facteur = getFacteur(ctx, articleCode, uniteAchat, uniteCommande);
			pu = pu.multiply(facteur);
		}
		return pu;
	}
	
	public BigDecimal getPrixUnitaireVente(ClientContext ctx, String magasinCode, String articleCode, String variantCode, String uniteCommande) {
		var pk = buildPK(magasinCode, articleCode, variantCode);
		var dto = dao.getReferenceById(pk);
		var uniteVente = dto.getUniteVente().getUniteCode();
		var pu = dto.getPrixUnitaireVente();
		
		if( !uniteVente.equals(uniteCommande) ) {
			var facteur = getFacteur(ctx, articleCode, uniteVente, uniteCommande);
			pu = pu.multiply(facteur);
		}
		return pu;
	}
	
	/**
	 * Détermine le facteur de conversion d'une unité source vers une unité cible de l'article correspondant.
	 * Ceci permettra de convertir une quantité exprimée en unité source vers une quantité en unité cible
	 * de même les prix correspondants
	 * @param ctx
	 * @param articleCode
	 * @param uniteSource
	 * @param uniteCible
	 * @return
	 */
	protected BigDecimal getFacteur(ClientContext ctx, String articleCode, String uniteSource, String uniteCible) {
		if( uniteSource.equals(uniteCible) ) {
			return BigDecimal.ONE;
		}
		
		var article = articleDao.getReferenceById(articleCode);
		var uniteBase = article.getUnite().getUniteCode();
		
		// conversion unité source vers unité de base
		BigDecimal sourceVersBase = getFacteurBase(articleCode, uniteBase, uniteSource);
		
		// Facteur conversion de l'unité cible vers unité de base
		BigDecimal cibleVersBase = getFacteurBase(articleCode, uniteBase, uniteCible);
		
		return sourceVersBase.divide(cibleVersBase, 4, RoundingMode.CEILING);
	}
	
	protected BigDecimal getFacteurBase(String articleCode, String uniteBase, String unite) {
		var msgTpl = "Aucun facteur de conversion n'est défini entre l'unité %s et l'unité de base %s de l'article %s";
		Supplier<RuntimeException> ex;
		
		// conversion unité vers unité de base
		BigDecimal sourceVersBase;
		if( uniteBase.equals(unite)) {
			sourceVersBase = BigDecimal.ONE;
		}else {
			var conversionPk = new ConversionPk(articleCode, unite);
			ex = () -> new RuntimeException(String.format(msgTpl, unite, uniteBase, articleCode));
			sourceVersBase = conversionDao.findById(conversionPk)
					.map(ConversionDto::getFacteur)
					.map(BigDecimal::valueOf)
					.orElseThrow(ex);
		}
		
		return sourceVersBase;
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
	
	private InventaireBean mapFromVariant(ClientContext ctx, String magasinCode, VariantDto dto) {
		var bean = new InventaireBean();
		bean.getMagasin().getMagasinCode().setValue(magasinCode);
		bean.getArticle().getArticleCode().setValue(dto.getArticleCode());
		bean.getVariantCode().setValue(dto.getVariantCode());
		bean.getAlias().setValue(dto.getAlias());
		bean.getVariantDescription().setValue(dto.getDescription());
		return bean;
	}
}
