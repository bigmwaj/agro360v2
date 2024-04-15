package com.agro360.form.finance;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.finance.RubriqueBean;
import com.agro360.bo.bean.finance.RubriqueSearchBean;
import com.agro360.bo.mapper.FinanceMapper;
import com.agro360.dto.finance.RubriqueDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.finance.RubriqueOperation;
import com.agro360.vd.common.EditActionEnumVd;

@Component
public class RubriqueForm {

	@Autowired
	RubriqueOperation operation;

	public RubriqueSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = FinanceMapper.buildRubriqueSearchBean();
		return bean;
	}

	public RubriqueBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findRubriqueByCode(ctx, e))
				.orElse(FinanceMapper.map(new RubriqueDto()));
		bean.getRubriqueCode().setValue(null);
		bean.setAction(EditActionEnumVd.CREATE);
		
		return bean;
	}
}
