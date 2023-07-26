package com.agro360.service.mapper.achat;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.achat.IBonCommandeDao;
import com.agro360.dto.achat.BonCommandeDto;
import com.agro360.service.bean.achat.BonCommandeBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class BonCommandeMapper extends AbstractMapper {

	public final static String OPTION_MAP_VARIANT_KEY = "MAP_VARIANT";

	public final static String OPTION_MAP_CONVERSION_KEY = "MAP_CONVERSION";

	@Autowired
	IBonCommandeDao dao;

	public BonCommandeBean mapToBean(BonCommandeDto dto, Map<String, Object> options) {
		var bean = new BonCommandeBean();

		return bean;
	}

	public BonCommandeDto mapToDto(BonCommandeBean bean) {
		var dto = new BonCommandeDto();

		return dto;
	}
}
