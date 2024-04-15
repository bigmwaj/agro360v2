package com.agro360.form.stock;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.ArticleTaxeBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dto.stock.ArticleTaxeDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.common.EditActionEnumVd;

@Component
public class ArticleTaxeForm {

	public ArticleTaxeBean initCreateFormBean(ClientContext ctx, String articleCode, Optional<String> copyFrom) {
		var bean = StockMapper.map(new ArticleTaxeDto());
		bean.setAction(EditActionEnumVd.CREATE);
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
