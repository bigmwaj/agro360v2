package com.agro360.service.mapper.stock;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IVariantDao;
import com.agro360.dto.stock.VariantDto;
import com.agro360.dto.stock.VariantPk;
import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.VariantBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class VariantMapper extends AbstractMapper {

	@Autowired
	private IVariantDao dao;

	@Autowired
	private IArticleDao articleDao;

	public VariantBean mapToBean(VariantDto dto) {
		var bean = new VariantBean();
		bean.getDescription().setValue(dto.getDescription());
		bean.getVariantCode().setValue(dto.getVariantCode());
		return bean;
	}

	public VariantDto mapToDto(ArticleBean articleBean, VariantBean bean) {
		VariantDto dto = null;
		var articleCode = articleBean.getArticleCode().getValue();
		var variantCode = bean.getVariantCode().getValue();
		
		VariantPk pk;

		if (Objects.nonNull(articleCode) && Objects.nonNull(variantCode) && dao.existsById(pk = new VariantPk(articleCode, variantCode))) {
			dto = dao.getReferenceById(pk);
		}else {
			dto = new VariantDto();
			dto.setVariantCode(variantCode);
		}
		
		dto.setArticle(StockSharedMapperHelper.mapToDto(articleDao, articleBean));
		setDtoValue(dto::setDescription,bean.getDescription());
		return dto;
	}
}
