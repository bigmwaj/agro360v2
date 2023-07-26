package com.agro360.service.mapper.stock;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.stock.UniteDto;
import com.agro360.service.bean.stock.UniteBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class UniteMapper extends AbstractMapper {

	@Autowired
	IUniteDao dao;

	public UniteBean mapToBean(UniteDto dto) {
		var bean = new UniteBean();
		bean.getUniteCode().setValue(dto.getUniteCode());
		bean.getDescription().setValue(dto.getDescription());
		bean.getAbreviation().setValue(dto.getAbreviation());
		return bean;
	}

	public UniteDto mapToDto(UniteBean bean) {
		var uniteCode = bean.getUniteCode().getValue();
		var dto = new UniteDto();
		dto.setUniteCode(uniteCode);

		if (Objects.nonNull(uniteCode) && dao.existsById(uniteCode)) {
			dto = dao.findById(uniteCode).orElseThrow();
		}
		dto.setDescription(bean.getDescription().getValue());
		dto.setAbreviation(bean.getAbreviation().getValue());
		return dto;
	}
}
