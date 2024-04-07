package com.agro360.form.stock;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.VariantBean;
import com.agro360.bo.mapper.stock.VariantMapper;
import com.agro360.dto.stock.VariantDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.common.EditActionEnumVd;

@Component
public class VariantForm {

	@Autowired
	VariantMapper mapper;

	public VariantBean initCreateFormBean(ClientContext ctx, String articleCode, Optional<String> copyFrom) {
		Objects.requireNonNull(articleCode);
		var bean = mapper.map(new VariantDto());
		bean.setAction(EditActionEnumVd.CREATE);
		return bean;
	}
}
