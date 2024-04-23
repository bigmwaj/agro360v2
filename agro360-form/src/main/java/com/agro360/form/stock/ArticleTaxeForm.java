package com.agro360.form.stock;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.ArticleTaxeBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class ArticleTaxeForm {

	public ArticleTaxeBean initCreateFormBean(ClientContext ctx, String articleCode, Optional<String> copyFrom) {
		var bean = new ArticleTaxeBean();
		bean.setAction(ClientOperationEnumVd.CREATE);
		return bean;
	}
	
//	void initUniteOption(ClientContext ctx, ArticleTaxeBean bean) {
//		Function<UniteBean, Object> codeFn = e -> e.getUniteCode().getValue();
//		Function<UniteBean, String> libelleFn = e -> e.getDescription().getValue();
//		
//		
//		var options = uniteService.findUnitesByCriteria(ctx, new UniteSearchBean())
//				.stream().collect(Collectors.toMap(codeFn, libelleFn));
//		
//		bean.getUnite().getUniteCode().setValueOptions(options);
//	}
}
