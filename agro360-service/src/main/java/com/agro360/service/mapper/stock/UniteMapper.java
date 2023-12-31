package com.agro360.service.mapper.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.stock.UniteDto;
import com.agro360.service.bean.stock.UniteBean;
import com.agro360.service.bean.stock.UniteSearchBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class UniteMapper extends AbstractMapper {

	@Autowired
	private IUniteDao dao;
	
	public UniteSearchBean mapToSearchBean() {
		var bean = new UniteSearchBean();
		return bean;
	}

	public UniteBean mapToBean(UniteDto dto) {
		var bean = new UniteBean();
		bean.getUniteCode().setValue(dto.getUniteCode());
		bean.getDescription().setValue(dto.getDescription());
		return bean;
	}

	public UniteDto mapToDto(UniteBean bean) {
		var dto = StockSharedMapperHelper.mapToDto(dao, bean);
		setDtoValue(dto::setDescription, bean.getDescription());
		return dto;
	}
}
