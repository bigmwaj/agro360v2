package com.agro360.form.stock;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.UniteSearchBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dto.stock.UniteDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.UniteOperation;
import com.agro360.vd.common.EditActionEnumVd;

@Component
public class UniteForm {

	
	@Autowired
	UniteOperation operation;

	public UniteSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = StockMapper.buildUniteSearchBean();
		return bean;
	}

	public UniteBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findUniteByCode(ctx, e))
				.orElse(StockMapper.map(new UniteDto()));
		bean.getUniteCode().setValue(null);
		bean.setAction(EditActionEnumVd.CREATE);
		
		return bean;
	}
}
