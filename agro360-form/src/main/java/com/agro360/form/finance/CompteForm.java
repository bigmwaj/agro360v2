package com.agro360.form.finance;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.bean.finance.CompteSearchBean;
import com.agro360.bo.mapper.finance.CompteMapper;
import com.agro360.dto.finance.CompteDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.finance.CompteOperation;
import com.agro360.vd.common.EditActionEnumVd;

@Component
public class CompteForm {

	@Autowired
	CompteMapper mapper;
	
	@Autowired
	CompteOperation operation;

	public CompteSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = mapper.mapToSearchBean();
		return bean;
	}

	public CompteBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findCompteByCode(ctx, e))
				.orElse(mapper.map(new CompteDto()));
		bean.getCompteCode().setValue(null);
		bean.setAction(EditActionEnumVd.CREATE);
		
		return bean;
	}
}
