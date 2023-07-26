package com.agro360.service.mapper.stock;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IConversionDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.ConversionDto;
import com.agro360.dto.stock.ConversionPk;
import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.ConversionBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class ConversionMapper extends AbstractMapper {

	@Autowired
	IConversionDao dao;

	@Autowired
	IUniteDao uniteDao;

	@Autowired
	IArticleDao articleDao;

	@Autowired
	UniteMapper uniteMapper;

	public ConversionBean mapToBean(ConversionDto dto) {
		var bean = new ConversionBean();
		bean.getFacteur().setValue(dto.getFacteur());

		if (dto.getUnite() != null) {
			bean.setUnite(uniteMapper.mapToBean(dto.getUnite()));
		}
		return bean;
	}

	public ConversionDto mapToDto(ArticleBean articleBean, ConversionBean bean) {
		ConversionDto dto = null;

		var articleCode = articleBean.getArticleCode().getValue();
		String uniteCode = null;
		
		if (bean.getUnite() != null) {
			uniteCode = bean.getUnite().getUniteCode().getValue();
		}
		
		if (Objects.nonNull(articleCode) && Objects.nonNull(uniteCode)) {
			var pk = new ConversionPk(articleCode, uniteCode);
			if (dao.existsById(pk)) {
				dto = dao.getById(pk);
			}
		}
		if (dto == null) {
			dto = new ConversionDto();
		} else {
			var unite = uniteDao.findById(uniteCode).orElseThrow();
			var article = articleDao.findById(articleCode).orElseThrow();

			dto.setUnite(unite);
			dto.setArticle(article);

			dto.setArticle(new ArticleDto());
			dto.getArticle().setArticleCode(articleBean.getArticleCode().getValue());
		}
		dto.setFacteur(bean.getFacteur().getValue());
		return dto;
	}
}
