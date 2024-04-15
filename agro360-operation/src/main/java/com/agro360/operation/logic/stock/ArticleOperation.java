package com.agro360.operation.logic.stock;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ArticleSearchBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dao.common.IDao;
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

	@Override
	protected IDao<ArticleDto, String> getDao() {
		return dao;
	}

	@RuleNamespace("stock/article/create")
	public void createArticle(ClientContext ctx, ArticleBean bean) {
		var dto = new ArticleDto();
		
		var unite = uniteDao.getReferenceById(bean.getUnite().getUniteCode().getValue());
		dto.setUnite(unite);
		
		setDtoValue(dto::setArticleCode, bean.getArticleCode());
		setDtoValue(dto::setType, bean.getType());
		setDtoValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(dto);		
	}

	@RuleNamespace("stock/article/update")
	public void updateArticle(ClientContext ctx, ArticleBean bean) {
		var dto = dao.getReferenceById(bean.getArticleCode().getValue());

		setDtoChangedValue(dto::setType, bean.getType());
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(dto);		
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
	
	public List<ArticleBean> findArticlesByCriteria(ClientContext ctx, ArticleSearchBean searchBean) {
		var example = Example.of(new ArticleDto());
		if( searchBean.getArticleCode().getValue() != null ) {
			example.getProbe().setArticleCode(searchBean.getArticleCode().getValue());
		}
		if( searchBean.getType().getValue() != null ) {
			example.getProbe().setType(searchBean.getType().getValue());
		}
		return dao.findAll(example).stream().map(StockMapper::map).collect(Collectors.toList());
	}
}