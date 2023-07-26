package com.agro360.service.mapper.stock;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IConversionDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dao.stock.IVariantDao;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.ConversionDto;
import com.agro360.dto.stock.VariantDto;
import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class ArticleMapper extends AbstractMapper {

	public final static String OPTION_MAP_VARIANT_KEY = "MAP_VARIANT";

	public final static String OPTION_MAP_CONVERSION_KEY = "MAP_CONVERSION";

	@Autowired
	IArticleDao dao;
	
	@Autowired
	IUniteDao uniteDao;

	@Autowired
	IVariantDao variantDao;

	@Autowired
	IConversionDao conversionDao;
	
	@Autowired
	UniteMapper uniteMapper;

	@Autowired
	VariantMapper variantMapper;

	@Autowired
	ConversionMapper conversionMapper;

	public ArticleBean mapToBean(ArticleDto dto, Map<String, Object> options) {
		var bean = new ArticleBean();

		bean.getArticleCode().setValue(dto.getArticleCode());
		bean.getDescription().setValue(dto.getDescription());
		bean.getTypeArticle().setValue(dto.getTypeArticle());
		
		if( dto.getUnite() != null ) {
			bean.setUnite(uniteMapper.mapToBean(dto.getUnite()));
		}

		var mapVariant = options.getOrDefault(OPTION_MAP_VARIANT_KEY, null);
		if (Objects.nonNull(dto.getArticleCode()) && mapVariant instanceof Boolean && (Boolean) mapVariant) {
			var ex = Example.of(new VariantDto());
			ex.getProbe().setArticle(new ArticleDto());
			ex.getProbe().getArticle().setArticleCode(dto.getArticleCode());

			var variantBeans = variantDao.findAll(ex).stream().map(variantMapper::mapToBean).toList();
			bean.getVariants().addAll(variantBeans);
		}

		var mapConversion = options.getOrDefault(OPTION_MAP_CONVERSION_KEY, null);
		if (mapConversion instanceof Boolean && (Boolean) mapConversion) {
			var ex = Example.of(new ConversionDto());
			ex.getProbe().setArticle(new ArticleDto());
			ex.getProbe().getArticle().setArticleCode(dto.getArticleCode());

			var conversionBeans = conversionDao.findAll(ex).stream().map(conversionMapper::mapToBean)
					.toList();
			bean.getConversions().addAll(conversionBeans);
		}

		return bean;
	}

	public ArticleBean mapToBean(ArticleDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public ArticleDto mapToDto(ArticleBean bean) {
		var dto = new ArticleDto();
		var articleCode = bean.getArticleCode().getValue();
		dto.setArticleCode(articleCode);
		
		if (Objects.nonNull(articleCode) && dao.existsById(articleCode)) {
			dto = dao.findById(articleCode).orElseThrow();
		}else {
			var uniteCode = bean.getUnite().getUniteCode().getValue();
			if( Objects.nonNull(uniteCode) && dao.existsById(uniteCode)) {
				var unite = uniteDao.findById(uniteCode).orElseThrow();
				dto.setUnite(unite);
			}
		}
		
		dto.setDescription(bean.getDescription().getValue());
		dto.setTypeArticle(bean.getTypeArticle().getValue());

		return dto;
	}
}
