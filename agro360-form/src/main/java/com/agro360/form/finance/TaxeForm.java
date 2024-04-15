package com.agro360.form.finance;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.finance.TaxeBean;
import com.agro360.bo.mapper.FinanceMapper;
import com.agro360.dto.finance.TaxeDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.finance.TaxeOperation;
import com.agro360.vd.common.EditActionEnumVd;

@Component
public class TaxeForm {

	@Autowired
	TaxeOperation operation;

	public TaxeBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findTaxeByCode(ctx, e))
				.orElse(FinanceMapper.map(new TaxeDto()));
		bean.getTaxeCode().setValue(null);
		bean.setAction(EditActionEnumVd.CREATE);
		
		return bean;
	}
}
