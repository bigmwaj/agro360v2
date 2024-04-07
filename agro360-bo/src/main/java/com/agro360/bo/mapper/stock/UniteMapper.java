package com.agro360.bo.mapper.stock;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.UniteSearchBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.dto.stock.UniteDto;

@Component
public class UniteMapper extends AbstractMapper {

	public UniteSearchBean mapToSearchBean() {
		var bean = new UniteSearchBean();
		return bean;
	}

	public UniteBean map(UniteDto dto) {
		var bean = new UniteBean();
		bean.getUniteCode().setValue(dto.getUniteCode());
		bean.getDescription().setValue(dto.getDescription());
		return bean;
	}
}
