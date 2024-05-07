package com.agro360.form.stock;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.VariantBean;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class VariantForm {

	@MetadataBeanName("stock/variant")
	public VariantBean initCreateFormBean(ClientContext ctx, String articleCode, Optional<String> copyFrom) {
		Objects.requireNonNull(articleCode);
		var bean = new VariantBean();
		bean.setAction(ClientOperationEnumVd.CREATE);
		return bean;
	}
}
