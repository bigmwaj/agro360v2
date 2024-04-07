package com.agro360.operation.logic.stock;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ConversionBean;
import com.agro360.bo.mapper.stock.ConversionMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IConversionDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.ConversionDto;
import com.agro360.dto.stock.ConversionPk;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class ConversionOperation extends AbstractOperation<ConversionDto, ConversionPk> {

	@Autowired
	IConversionDao dao;

	@Autowired
	IUniteDao uniteDao;

	@Autowired
	IArticleDao articleDao;

	@Autowired
	ConversionMapper mapper;
	
	@Override
	protected String getRulePath() {
		return "stock/article/conversion";
	}
	
	@RuleNamespace("stock/article/conversion/create")
	public void createConversion(ClientContext ctx, ArticleBean article, ConversionBean bean) {
		var dto = new ConversionDto();
		var articleDto = articleDao.getReferenceById(article.getArticleCode().getValue());
		var uniteDto = uniteDao.getReferenceById(article.getUnite().getUniteCode().getValue());
		
		setDtoValue(dto::setFacteur, bean.getFacteur());
		
		dto.setArticle(articleDto);
		dto.setUnite(uniteDto);
		
		super.save(dto);
	}
	
	@RuleNamespace("stock/article/conversion/update")
	public void updateConversion(ClientContext ctx, ArticleBean article, ConversionBean bean) {
		var dto = dao.getReferenceById(buildPK(article, bean));

		setDtoChangedValue(dto::setFacteur, bean.getFacteur());
		
		super.save(dto);
	}
	
	@RuleNamespace("stock/article/conversion/delete")
	public void deleteConversion(ClientContext ctx, ArticleBean article, ConversionBean bean) {
		var dto = dao.getReferenceById(buildPK(article, bean));
		super.delete(dto);
	}

	private ConversionPk buildPK(ArticleBean article, ConversionBean bean) {
		var articleCode = article.getArticleCode().getValue();
		var uniteCode = bean.getUnite().getUniteCode().getValue();
		
		return new ConversionPk(articleCode, uniteCode);
	}
	
	
	public List<ConversionBean> findConversionsByArticleCode(ClientContext ctx, String articleCode) {
		var example = Example.of(new ConversionDto());
		example.getProbe().setArticle(new ArticleDto());
		example.getProbe().getArticle().setArticleCode(articleCode);
		
		return dao.findAll(example).stream().map(mapper::map).collect(Collectors.toList());
	}
	
	@Override
	protected IDao<ConversionDto, ConversionPk> getDao() {
		return dao;
	}

}
