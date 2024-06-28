package com.agro360.operation.logic.stock;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ArticleSearchBean;
import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.core.IPartnerDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class ArticleOperation extends AbstractOperation<ArticleDto, String> {

	@Autowired
	IArticleDao dao;

	@Autowired
	IUniteDao uniteDao;
	
	@Autowired
	IPartnerDao partnerDao;

	@Override
	protected IDao<ArticleDto, String> getDao() {
		return dao;
	}

	@RuleNamespace("stock/article/create")
	public void createArticle(ClientContext ctx, ArticleBean bean) {
		var dto = new ArticleDto();
		
		var unite = uniteDao.getReferenceById(bean.getUnite().getUniteCode().getValue());
		dto.setUnite(unite);
		
		var fabriquantCode = bean.getFabriquant().getPartnerCode().getValue();
		if( fabriquantCode != null && partnerDao.existsById(fabriquantCode) ) {
			var partner = partnerDao.getReferenceById(fabriquantCode);
			dto.setFabriquant(partner);
		}
		
		var distributeurCode = bean.getDistributeur().getPartnerCode().getValue();
		if( distributeurCode != null && partnerDao.existsById(distributeurCode) ) {
			var partner = partnerDao.getReferenceById(distributeurCode);
			dto.setDistributeur(partner);
		}
		
		setDtoValue(dto::setArticleCode, bean.getArticleCode());
		setDtoValue(dto::setType, bean.getType());
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setOrigine, bean.getOrigine());
		
		dto = super.save(ctx, dto);		
	}

	@RuleNamespace("stock/article/update")
	public void updateArticle(ClientContext ctx, ArticleBean bean) {
		var dto = dao.getReferenceById(bean.getArticleCode().getValue());

		setDtoChangedValue(dto::setType, bean.getType());
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		setDtoChangedValue(dto::setOrigine, bean.getOrigine());

		var fabriquantCode = bean.getFabriquant().getPartnerCode().getValue();
		var fabriquant = dto.getFabriquant();
		if( fabriquantCode == null ) {
			dto.setFabriquant(null);
		}else {
			var fabriquantCurrCode = fabriquant == null ? null : fabriquant.getPartnerCode();
			if( fabriquantCode != null 
					&& !fabriquantCode.equals(fabriquantCurrCode) 
					&& partnerDao.existsById(fabriquantCode) ) {
				var partner = partnerDao.getReferenceById(fabriquantCode);
				dto.setFabriquant(partner);
			}
		}
		
		var distributeurCode = bean.getDistributeur().getPartnerCode().getValue();
		var distributeur = dto.getDistributeur();
		if( distributeurCode == null ) {
			dto.setDistributeur(null);
		}else {
			var distributeurCurrCode = distributeur == null ? null : distributeur.getPartnerCode();
			if( distributeurCode != null 
					&& !distributeurCode.equals(distributeurCurrCode) 
					&& partnerDao.existsById(distributeurCode) ) {
				var partner = partnerDao.getReferenceById(distributeurCode);
				dto.setDistributeur(partner);
			}
		}
		
		dto = super.save(ctx, dto);		
	}
	
	@RuleNamespace("stock/article/delete")
	public void deleteArticle(ClientContext ctx, ArticleBean bean) {
		var dto = dao.getReferenceById(bean.getArticleCode().getValue());
		dao.delete(dto);
	}

	public ArticleBean findArticleByCode(ClientContext ctx, String articleCode) {
		var dto = dao.getReferenceById(articleCode);
		return StockMapper.map(dto);	
	}
	
	public List<UniteBean> findUnitesArticleByCode(ClientContext ctx, String articleCode) {
		return dao.findUnitesArticleByCode(articleCode).stream()
				.map(StockMapper::map)
				.collect(Collectors.toList());
	}
	
	public List<ArticleBean> findArticlesByCriteria(ClientContext ctx, ArticleSearchBean searchBean) {
		var article = searchBean.getArticleCode().getValue();
		if( article != null ) {
			article = article.toUpperCase();
		}
		var type = searchBean.getType().getValue();
        var length = dao.countArticlesByCriteria(type, article);
        searchBean.setLength(length);
        return dao.findArticlesByCriteria(searchBean.getOffset(), searchBean.getLimit(), type, article)
        		.stream().map(StockMapper::map)
        		.collect(Collectors.toList());
	}
}
