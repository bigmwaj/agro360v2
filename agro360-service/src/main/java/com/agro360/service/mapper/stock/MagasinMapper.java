package com.agro360.service.mapper.stock;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.agro360.dao.stock.ICasierDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dto.stock.CasierDto;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.bean.stock.MagasinSearchBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class MagasinMapper extends AbstractMapper {

	public final static String OPTION_MAP_CASIER_KEY = "MAP_CASIER";

	@Autowired
	IMagasinDao dao;

	@Autowired
	ICasierDao casierDao;

	@Autowired
	CasierMapper casierMapper;
	
	public MagasinSearchBean mapToSearchBean() {
		var bean = new MagasinSearchBean();
		return bean;
	}

	public MagasinBean mapToBean(MagasinDto dto, Map<String, Object> options) {
		var magasinCode = dto.getMagasinCode();
		var mapCasier = options.getOrDefault(OPTION_MAP_CASIER_KEY, null);
		var bean = new MagasinBean();

		bean.getMagasinCode().setValue(dto.getMagasinCode());
		bean.getDescription().setValue(dto.getDescription());

		if (Objects.nonNull(magasinCode) && mapCasier instanceof Boolean && (Boolean) mapCasier) {
			var ex = Example.of(new CasierDto());
			ex.getProbe().setMagasin(new MagasinDto());
			ex.getProbe().getMagasin().setMagasinCode(dto.getMagasinCode());

			var casierBeans = casierDao.findAll(ex).stream().map(casierMapper::mapToBean).toList();
			bean.getCasiers().addAll(casierBeans);
		}
		return bean;
	}

	public MagasinBean mapToBean(MagasinDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public MagasinDto mapToDto(MagasinBean bean) {
		MagasinDto dto = StockSharedMapperHelper.mapToDto(dao, bean);

		setDtoValue(dto::setDescription, bean.getDescription());
		return dto;
	}
}
