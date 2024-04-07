package com.agro360.bo.mapper.finance;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.finance.RubriqueBean;
import com.agro360.bo.bean.finance.RubriqueSearchBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.dto.finance.RubriqueDto;

@Component
public class RubriqueMapper extends AbstractMapper {

	public RubriqueSearchBean mapToSearchBean() {
		var bean = new RubriqueSearchBean();
		
		return bean;
	}
	
	public RubriqueBean map(RubriqueDto dto) {
		var bean = new RubriqueBean();
		
		bean.getRubriqueCode().setValue(dto.getRubriqueCode());

		bean.getType().setValue(dto.getType());
		bean.getDescription().setValue(dto.getDescription());
		bean.getNom().setValue(dto.getNom());

		return bean;
	}
}
