package com.agro360.bo.mapper.stock;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.CasierBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.dto.stock.CasierDto;

@Component
public class CasierMapper extends AbstractMapper {

	public CasierBean map(CasierDto dto) {
		var bean = new CasierBean();
		bean.getCasierCode().setValue(dto.getCasierCode());
		bean.getDescription().setValue(dto.getDescription());
		return bean;
	}
}
