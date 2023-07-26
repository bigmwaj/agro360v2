package com.agro360.service.mapper.common;

import java.util.Map;

import com.agro360.dto.achat.LigneDto;
import com.agro360.service.bean.achat.LigneBean;

public abstract class AstractLigneMapper extends AbstractMapper {

	public LigneBean mapToBean(LigneDto dto, Map<String, Object> options) {
		var bean = new LigneBean();

		return bean;
	}

	public LigneDto mapToDto(LigneBean bean) {
		var dto = new LigneDto();

		return dto;
	}
}
