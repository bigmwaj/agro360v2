package com.agro360.bo.mapper.stock;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.VariantBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.dto.stock.VariantDto;

@Component
public class VariantMapper extends AbstractMapper {

	public VariantBean map(VariantDto dto) {
		var bean = new VariantBean();
		bean.getDescription().setValue(dto.getDescription());
		bean.getVariantCode().setValue(dto.getVariantCode());
		return bean;
	}
}
