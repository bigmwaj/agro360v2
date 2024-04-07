package com.agro360.bo.mapper.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.ConversionBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.dto.stock.ConversionDto;

@Component
public class ConversionMapper extends AbstractMapper {

	@Autowired
	private UniteMapper uniteMapper;

	public ConversionBean map(ConversionDto dto) {
		var bean = new ConversionBean();
		bean.getFacteur().setValue(dto.getFacteur());

		if (dto.getUnite() != null) {
			bean.setUnite(uniteMapper.map(dto.getUnite()));
		}
		return bean;
	}
}
