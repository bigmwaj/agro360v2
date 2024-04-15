package com.agro360.operation.logic.stock;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ArticleTaxeBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.finance.ITaxeDao;
import com.agro360.dao.stock.IArticleTaxeDao;
import com.agro360.dto.stock.ArticleTaxeDto;
import com.agro360.dto.stock.ArticleTaxePk;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class ArticleTaxeOperation extends AbstractOperation<ArticleTaxeDto, ArticleTaxePk> {

	@Autowired
	IArticleTaxeDao dao;

	@Autowired
	ITaxeDao taxeDao;

	@Override
	protected IDao<ArticleTaxeDto, ArticleTaxePk> getDao() {
		return dao;
	}
	
	private ArticleTaxePk buildPK(ArticleBean article, ArticleTaxeBean bean) {
		var articleCode = article.getArticleCode().getValue();
		var taxeCode = bean.getTaxe().getTaxeCode().getValue();
		
		return new ArticleTaxePk(articleCode, taxeCode);
	}

	@RuleNamespace("stock/article/taxe/create")
	public void createArticleTaxe(ClientContext ctx, ArticleBean article, ArticleTaxeBean bean) {
		var dto = new ArticleTaxeDto();
		
		var taxe = taxeDao.getReferenceById(bean.getTaxe().getTaxeCode().getValue());
		dto.setTaxe(taxe);
		
		setDtoValue(dto::setArticleCode, article.getArticleCode());
		
		dto = super.save(dto);		
	}
	
	@RuleNamespace("stock/article/taxe/delete")
	public void deleteArticleTaxe(ClientContext ctx, ArticleBean article, ArticleTaxeBean bean) {
		var dto = dao.getReferenceById(buildPK(article, bean));
		dao.delete(dto);
	}

	public List<ArticleTaxeBean> findArticleTaxesByArticleCode(ClientContext ctx, String articleCode) {
		return dao.findAllByArticleCode(articleCode)
				.stream()
				.map(StockMapper::map)
				.collect(Collectors.toList());
	}

}
