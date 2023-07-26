package com.agro360.service.mapper.achat;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.achat.ILigneDao;
import com.agro360.dto.achat.LigneDto;
import com.agro360.service.bean.achat.LigneBean;
import com.agro360.service.mapper.common.AstractLigneMapper;

@Component(value = "achat/LigneMapper")
public class LigneMapper extends AstractLigneMapper {

	@Autowired
	ILigneDao dao;

	public LigneBean mapToBean(LigneDto dto, Map<String, Object> options) {
		var bean = new LigneBean();

		return bean;
	}

	public LigneDto mapToDto(LigneBean bean) {
		var dto = new LigneDto();

		return dto;
	}
}
