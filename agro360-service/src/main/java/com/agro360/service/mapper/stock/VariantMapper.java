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
	IVariantDao dao;

	@Autowired
	IArticleDao articleDao;

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

		if (Objects.nonNull(articleCode) && Objects.nonNull(variantCode)) {
			var pk = new VariantPk(articleCode, variantCode);
			if (dao.existsById(pk)) {
				dto = dao.getById(pk);
			}
		}
		if (dto == null) {
			dto = new VariantDto();
		} else {
			var article = articleDao.findById(articleCode).orElseThrow();
			dto.setVariantCode(variantCode);
			dto.setArticle(article);
		}
		dto.setDescription(bean.getDescription().getValue());
		return dto;
	}
}
