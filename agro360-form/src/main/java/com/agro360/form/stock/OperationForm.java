package com.agro360.form.stock;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.OperationSearchBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.form.common.AbstractForm;
import com.agro360.operation.context.ClientContext;

@Component("stock/OperationForm")
public class OperationForm extends AbstractForm{

	public OperationSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = StockMapper.buildOperationSearchBean();
		return bean;
	}
}
