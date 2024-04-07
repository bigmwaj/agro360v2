package com.agro360.bo.mapper.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.dto.stock.MagasinDto;

@Component
public class MagasinMapper extends AbstractMapper {

	public final static String OPTION_MAP_CASIER_KEY = "MAP_CASIER";

	@Autowired
	CasierMapper casierMapper;
	
	public MagasinSearchBean mapToSearchBean() {
		var bean = new MagasinSearchBean();
		return bean;
	}

	public MagasinBean map(MagasinDto dto) {
		var bean = new MagasinBean();

		bean.getMagasinCode().setValue(dto.getMagasinCode());
		bean.getDescription().setValue(dto.getDescription());

		return bean;
	}
}
