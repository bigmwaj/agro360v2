package com.agro360.operation.logic.stock;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.VariantBean;
import com.agro360.bo.mapper.stock.VariantMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IVariantDao;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.VariantDto;
import com.agro360.dto.stock.VariantPk;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class VariantOperation extends AbstractOperation<VariantDto, VariantPk> {


	@Autowired
	IVariantDao dao;
	
	@Autowired
	IArticleDao articleDao;

	@Autowired
	VariantMapper mapper;

	@Override
	protected IDao<VariantDto, VariantPk> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "stock/article/variant";
	}
	
	@RuleNamespace("stock/article/variant/create")
	public void createVariant(ClientContext ctx, ArticleBean article, VariantBean bean) {
		var dto = new VariantDto();
		var articleDto = articleDao.getReferenceById(article.getArticleCode().getValue());
		
		setDtoValue(dto::setVariantCode, bean.getVariantCode());
		setDtoValue(dto::setDescription, bean.getDescription());
		
		dto.setArticle(articleDto);
		
		super.save(dto);		
	}
	
	@RuleNamespace("stock/article/variant/update")
	public void updateVariant(ClientContext ctx, ArticleBean article, VariantBean bean) {
		
		var dto = dao.getReferenceById(buildPK(article, bean));

		setDtoChangedValue(dto::setDescription, bean.getDescription());
		
		super.save(dto);
		
	}
	
	@RuleNamespace("stock/article/variant/delete")
	public void deleteVariant(ClientContext ctx, ArticleBean article, VariantBean bean) {
		var dto = dao.getReferenceById(buildPK(article, bean));
		
		super.delete(dto);
	}
	
	public List<VariantBean> findVariantsByArticleCode(ClientContext ctx, String articleCode) {
		var example = Example.of(new VariantDto());
		example.getProbe().setArticle(new ArticleDto());
		example.getProbe().getArticle().setArticleCode(articleCode);
		
		return dao.findAll(example).stream().map(mapper::map).collect(Collectors.toList());
	}
	
	private VariantPk buildPK(ArticleBean article, VariantBean bean) {
		var articleCode = article.getArticleCode().getValue();
		var variantCode = bean.getVariantCode().getValue();
		
		return new VariantPk(articleCode, variantCode);
	}
}
