package com.agro360.form.finance;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.finance.TaxeBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.finance.TaxeOperation;
import com.agro360.operation.metadata.BeanMetadataConfig;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class TaxeForm {

	@Autowired
	TaxeOperation operation;
	
	@Qualifier("finance/taxe")
	@Autowired
	private BeanMetadataConfig metadataConfig;

	public TaxeBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findTaxeByCode(ctx, e))
				.orElse(new TaxeBean());
		bean.getTaxeCode().setValue(null);
		bean.setAction(ClientOperationEnumVd.CREATE);
		
		metadataConfig.applyMetadata(ctx, bean);
		
		return bean;
	}
	
	public List<TaxeBean> initUpdateFormBean(ClientContext ctx, List<TaxeBean> beans) {
		Consumer<TaxeBean> apply = e -> {
			e.setAction(ClientOperationEnumVd.UPDATE); 
			metadataConfig.applyMetadata(ctx, e);
		};
		
		beans.stream().forEach(apply);
		return beans;
	}
}
