package com.agro360.service.mapper.common;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.common.AbstractLigneDto;
import com.agro360.service.bean.common.AbstractLigneBean;
import com.agro360.service.mapper.stock.ArticleMapper;
import com.agro360.service.mapper.stock.StockSharedMapperHelper;
import com.agro360.service.mapper.stock.UniteMapper;

public abstract class AstractLigneMapper<E extends AbstractLigneDto> extends AbstractMapper {

	@Autowired
	private UniteMapper uniteMapper;

	@Autowired
	private IUniteDao uniteDao;

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private IArticleDao articleDao;

	protected abstract IDao<E, Long> getDao();

	public AbstractLigneBean<E> mapToBean(E dto, AbstractLigneBean<E> bean) {
		return mapToBean(dto, bean, Collections.emptyMap());
	}

	public AbstractLigneBean<E> mapToBean(E dto, AbstractLigneBean<E> bean, Map<String, Object> options) {
		bean.getLigneId().setValue(dto.getLigneId());
		bean.getNumero().setValue(dto.getNumero());
		bean.getTypeLigne().setValue(dto.getTypeLigne());
		bean.getDescription().setValue(dto.getDescription());
		bean.getQuantite().setValue(dto.getQuantite());
		bean.getPrixUnitaire().setValue(dto.getPrixUnitaire());
		bean.getVariantCode().setValue(dto.getVariantCode());

		if (dto.getUnite() != null) {
			bean.setUnite(uniteMapper.mapToBean(dto.getUnite()));
		}

		if (dto.getArticle() != null) {
			bean.setArticle(articleMapper.mapToBean(dto.getArticle()));
		}

		return bean;
	}

	public E mapToDto(AbstractLigneBean<E> bean, Supplier<E> newDtoSpl) {
		var id = bean.getLigneId().getValue();
		E dto = null;
		if ( null != id && getDao().existsById(id)) {
			dto = getDao().getById(id);
		} else {
			dto = newDtoSpl.get();
			dto.setLigneId(bean.getLigneId().getValue());
		}

		setDtoValue(dto::setNumero, bean.getNumero());
		setDtoValue(dto::setTypeLigne, bean.getTypeLigne());
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setQuantite, bean.getQuantite());
		setDtoValue(dto::setPrixUnitaire, bean.getPrixUnitaire());
		setDtoValue(dto::setVariantCode, bean.getVariantCode());

		dto.setUnite(StockSharedMapperHelper.mapToDto(uniteDao, bean.getUnite()));
		dto.setArticle(StockSharedMapperHelper.mapToDto(articleDao, bean.getArticle()));
		return dto;
	}
}
