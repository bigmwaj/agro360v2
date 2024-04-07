package com.agro360.bo.mapper.finance;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.bean.finance.CompteSearchBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.dto.finance.CompteDto;

@Component
public class CompteMapper extends AbstractMapper {

	public CompteSearchBean mapToSearchBean() {
		var bean = new CompteSearchBean();
		return bean;
	}
	
	public CompteBean map(CompteDto dto) {
		var bean = new CompteBean();
		bean.getCompteCode().setValue(dto.getCompteCode());

		bean.getDescription().setValue(dto.getDescription());

		return bean;
	}
}
