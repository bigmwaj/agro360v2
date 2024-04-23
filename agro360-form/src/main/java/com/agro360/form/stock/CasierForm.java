package com.agro360.form.stock;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.CasierBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class CasierForm {

	public CasierBean initCreateFormBean(ClientContext ctx, String magasinCode, Optional<String> copyFrom) {
		var bean = new CasierBean();
		bean.setAction(ClientOperationEnumVd.CREATE);
		return bean;
	}
}
