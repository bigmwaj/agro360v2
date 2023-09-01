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
import com.agro360.service.bean.stock.ArticleSearchBean;
import com.agro360.service.mapper.common.AbstractMapper;
import com.agro360.vd.stock.TypeArticleEnumVd;

@Component
public class ArticleMapper extends AbstractMapper {

	public final static String OPTION_MAP_VARIANT_KEY = "MAP_VARIANT";

	public final static String OPTION_MAP_CONVERSION_KEY = "MAP_CONVERSION";

	@Autowired
	private IArticleDao dao;

	@Autowired
	private IUniteDao uniteDao;

	@Autowired
	private IVariantDao variantDao;

	@Autowired
	private IConversionDao conversionDao;

	@Autowired
	private UniteMapper uniteMapper;

	@Autowired
	private VariantMapper variantMapper;

	@Autowired
	ConversionMapper conversionMapper;
	
	public ArticleSearchBean mapToSearchBean() {
		var bean = new ArticleSearchBean();
		setMap(bean.getTypeArticle()::setValueOptions, TypeArticleEnumVd.values(), TypeArticleEnumVd::getLibelle);
		return bean;
	}

	public ArticleBean mapToBean(ArticleDto dto, Map<String, Object> options) {
		var articleCode = dto.getArticleCode();
		var bean = new ArticleBean();
		var uniteValueOptions = StockSharedMapperHelper.getAllAsValueOptions(uniteDao);

		bean.getArticleCode().setValue(dto.getArticleCode());
		bean.getDescription().setValue(dto.getDescription());
		
		bean.getTypeArticle().setValue(dto.getTypeArticle());
		setMap(bean.getTypeArticle()::setValueOptions, TypeArticleEnumVd.values(), TypeArticleEnumVd::getLibelle);

		if (dto.getUnite() != null) {
			bean.setUnite(uniteMapper.mapToBean(dto.getUnite()));
		}
		bean.getUnite().getUniteCode().setValueOptions(uniteValueOptions);

		var mapVariant = options.getOrDefault(OPTION_MAP_VARIANT_KEY, null);
		if (Objects.nonNull(articleCode) && mapVariant instanceof Boolean && (Boolean) mapVariant) {
			var ex = Example.of(new VariantDto());
			ex.getProbe().setArticle(new ArticleDto());
			ex.getProbe().getArticle().setArticleCode(dto.getArticleCode());

			var variantBeans = variantDao.findAll(ex).stream().map(variantMapper::mapToBean).toList();
			bean.getVariants().addAll(variantBeans);
		}

		var mapConversion = options.getOrDefault(OPTION_MAP_CONVERSION_KEY, null);
		if (Objects.nonNull(articleCode) && mapConversion instanceof Boolean && (Boolean) mapConversion) {
			var ex = Example.of(new ConversionDto());
			ex.getProbe().setArticle(new ArticleDto());
			ex.getProbe().getArticle().setArticleCode(dto.getArticleCode());

			var conversionBeans = conversionDao.findAll(ex)
					.stream().map(conversionMapper::mapToBean)
					.peek(e -> e.getUnite().getUniteCode().setValueOptions(uniteValueOptions))
					.toList();
			bean.getConversions().addAll(conversionBeans);
		}

		return bean;
	}

	public ArticleBean mapToBean(ArticleDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public ArticleDto mapToDto(ArticleBean bean) {
		ArticleDto dto = StockSharedMapperHelper.mapToDto(dao, bean);
		dto.setUnite(StockSharedMapperHelper.mapToDto(uniteDao, bean.getUnite()));
		
		setDtoValue(dto::setDescription,bean.getDescription());
		setDtoValue(dto::setTypeArticle,bean.getTypeArticle());

		return dto;
	}
}
