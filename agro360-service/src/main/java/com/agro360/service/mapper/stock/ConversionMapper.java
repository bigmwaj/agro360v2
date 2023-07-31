package com.agro360.service.mapper.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IConversionDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.stock.ConversionDto;
import com.agro360.dto.stock.ConversionPk;
import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.ConversionBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class ConversionMapper extends AbstractMapper {

	@Autowired
	private IConversionDao dao;

	@Autowired
	private IUniteDao uniteDao;

	@Autowired
	private IArticleDao articleDao;

	@Autowired
	private UniteMapper uniteMapper;

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
		ConversionPk pk;
		if ( null == articleCode && null == uniteCode && dao.existsById(pk = new ConversionPk(articleCode, uniteCode)) ) {
			dto = dao.getById(pk);
		}else {
			dto = new ConversionDto();
		}

		setDtoValue(dto::setFacteur, bean.getFacteur());
		
		dto.setUnite(StockSharedMapperHelper.mapToDto(uniteDao, bean.getUnite()));
		dto.setArticle(StockSharedMapperHelper.mapToDto(articleDao, articleBean));
		return dto;
	}
}
