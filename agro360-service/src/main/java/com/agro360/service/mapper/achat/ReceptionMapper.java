package com.agro360.service.mapper.achat;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.achat.IReceptionDao;
import com.agro360.dto.achat.ReceptionDto;
import com.agro360.service.bean.achat.ReceptionBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class ReceptionMapper extends AbstractMapper {

	@Autowired
	IReceptionDao dao;

	public ReceptionBean mapToBean(ReceptionDto dto, Map<String, Object> options) {
		var bean = new ReceptionBean();

		return bean;
	}

	public ReceptionDto mapToDto(ReceptionBean bean) {
		var dto = new ReceptionDto();

		return dto;
	}
}
