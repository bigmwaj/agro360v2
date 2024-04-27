package com.agro360.operation.logic.stock;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.InventaireBean;
import com.agro360.bo.bean.stock.InventaireSearchBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IInventaireDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dao.stock.IUniteDao;
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

	@RuleNamespace("stock/inventaire/update-quantite")
	public void updateInventaireQuantite(ClientContext ctx, InventaireBean bean) {
		var dto = dao.getReferenceById(buildPK(bean));

		setDtoValue(dto::setQuantite, bean.getQuantite());
		
		super.save(dto);		
	}
	
	@RuleNamespace("stock/inventaire/update-prix-vente")
	public void updateInventairePrixVente(ClientContext ctx, InventaireBean bean) {
		var dto = dao.getReferenceById(buildPK(bean));

		setDtoValue(dto::setPrixUnitaireVente, bean.getPrixUnitaireVente());
		
		super.save(dto);		
	}

	public InventaireBean findInventaireByCode(ClientContext ctx, String magasinCode, String articleCode, String variantCode) {
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
}
