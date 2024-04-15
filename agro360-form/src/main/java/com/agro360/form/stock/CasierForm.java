package com.agro360.form.stock;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.CasierBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dto.stock.CasierDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.common.EditActionEnumVd;

@Component
public class CasierForm {

	public CasierBean initCreateFormBean(ClientContext ctx, String magasinCode, Optional<String> copyFrom) {
		var bean = StockMapper.map(new CasierDto());
		bean.setAction(EditActionEnumVd.CREATE);
		return bean;
	}
}
