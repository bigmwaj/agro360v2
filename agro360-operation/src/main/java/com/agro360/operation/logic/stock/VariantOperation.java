package com.agro360.operation.logic.stock;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.VariantBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IVariantDao;
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

	@Override
	protected IDao<VariantDto, VariantPk> getDao() {
		return dao;
	}
	
	@RuleNamespace("stock/article/variant/create")
	public void createVariant(ClientContext ctx, ArticleBean article, VariantBean bean) {
		var dto = new VariantDto();
		
		setDtoValue(dto::setArticleCode, article.getArticleCode());
		setDtoValue(dto::setVariantCode, bean.getVariantCode());
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setAlias, bean.getAlias());
		
		super.save(dto);		
	}
	
	@RuleNamespace("stock/article/variant/update")
	public void updateVariant(ClientContext ctx, ArticleBean article, VariantBean bean) {
		
		var dto = dao.getReferenceById(buildPK(article, bean));

		setDtoChangedValue(dto::setDescription, bean.getDescription());
		setDtoChangedValue(dto::setAlias, bean.getAlias());
		
		super.save(dto);
		
	}
	
	@RuleNamespace("stock/article/variant/delete")
	public void deleteVariant(ClientContext ctx, ArticleBean article, VariantBean bean) {
		var dto = dao.getReferenceById(buildPK(article, bean));
		
		super.delete(dto);
	}
	
	public List<VariantBean> findVariantsByArticleCode(ClientContext ctx, String articleCode) {
		return dao.findAllByArticleCode(articleCode).stream()
				.map(StockMapper::map)
				.collect(Collectors.toList());
	}
	
	private VariantPk buildPK(ArticleBean article, VariantBean bean) {
		var articleCode = article.getArticleCode().getValue();
		var variantCode = bean.getVariantCode().getValue();
		
		return new VariantPk(articleCode, variantCode);
	}

	public VariantBean findVariantByAlias(ClientContext ctx, String alias) {
		var msgFormat = "Aucun variant n'a été trouvé ayant pour alias <strong>%s</strong>";
		Supplier<RuntimeException> exSplr= () -> new RuntimeException(String.format(msgFormat, alias));
		return dao.findOneByAliasIgnoreCase(alias).map(StockMapper::map).orElseThrow(exSplr);
	}
	
	public List<VariantBean> findVariantsByQuery(ClientContext ctx, String query) {
		query = getNullOrUpperCase(query, "%");
		return dao.findByQuery(query).stream().map(StockMapper::map).collect(Collectors.toList());
	}
}
