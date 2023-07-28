package com.agro360.service.mapper.common;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dao.stock.IVariantDao;
import com.agro360.dto.common.AbstractLigneDto;
import com.agro360.dto.stock.VariantPk;
import com.agro360.service.bean.common.AbstractLigneBean;
import com.agro360.service.mapper.stock.ArticleMapper;
import com.agro360.service.mapper.stock.StockSharedMapperHelper;
import com.agro360.service.mapper.stock.UniteMapper;
import com.agro360.service.mapper.stock.VariantMapper;

public abstract class AstractLigneMapper<E extends AbstractLigneDto> extends AbstractMapper {

	@Autowired
	UniteMapper uniteMapper;

	@Autowired
	IUniteDao uniteDao;

	@Autowired
	ArticleMapper articleMapper;

	@Autowired
	IArticleDao articleDao;

	@Autowired
	VariantMapper variantMapper;

	@Autowired
	IVariantDao variantDao;

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

		if (dto.getUnite() != null) {
			bean.setUnite(uniteMapper.mapToBean(dto.getUnite()));
		}

		if (dto.getArticle() != null) {
			bean.setArticle(articleMapper.mapToBean(dto.getArticle()));
		}

		if (dto.getVariant() != null) {
			bean.setVariant(variantMapper.mapToBean(dto.getVariant()));
		}

		return bean;
	}

	public E mapToDto(AbstractLigneBean<E> bean, Supplier<E> newDtoSpl) {
		var id = bean.getLigneId().getValue();
		E dto = null;
		if (Objects.nonNull(id) && getDao().existsById(id)) {
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

		dto.setUnite(StockSharedMapperHelper.mapToDto(uniteDao, bean.getUnite()));
		dto.setArticle(StockSharedMapperHelper.mapToDto(articleDao, bean.getArticle()));

		String articleCode = bean.getArticle().getArticleCode().getValue();
		String variantCode = bean.getVariant().getVariantCode().getValue();
		VariantPk variantPk = null;
		if (null != articleCode && null != variantCode
				&& variantDao.existsById(variantPk = new VariantPk(articleCode, variantCode))) {
			dto.setVariant(variantDao.getById(variantPk));
		}
		return dto;
	}
}
