package com.agro360.form.stock;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.ConversionBean;
import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.UniteSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.UniteOperation;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class ConversionForm {

	@Autowired
	UniteOperation uniteService;

	public ConversionBean initCreateFormBean(ClientContext ctx, String articleCode, Optional<String> copyFrom) {
		var bean = new ConversionBean();
		bean.setAction(ClientOperationEnumVd.CREATE);
		initUniteOption(ctx, bean);
		return bean;
	}
	
	void initUniteOption(ClientContext ctx, ConversionBean bean) {
		Function<UniteBean, Object> codeFn = e -> e.getUniteCode().getValue();
		Function<UniteBean, String> libelleFn = e -> e.getDescription().getValue();
		
		
		var options = uniteService.findUnitesByCriteria(ctx, new UniteSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));
		
		bean.getUnite().getUniteCode().setValueOptions(options);
	}
}
