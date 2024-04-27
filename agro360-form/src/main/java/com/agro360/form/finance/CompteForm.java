package com.agro360.form.finance;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.bean.finance.CompteSearchBean;
import com.agro360.bo.mapper.FinanceMapper;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.finance.CompteOperation;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class CompteForm {
	
	@Autowired
	CompteOperation operation;

	public CompteSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = FinanceMapper.buildCompteSearchBean();
		return bean;
	}

	@MetadataBeanName("finance/compte")
	public CompteBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findCompteByCode(ctx, e))
				.orElse(new CompteBean());
		bean.getCompteCode().setValue(null);
		bean.setAction(ClientOperationEnumVd.CREATE);
		return bean;
	}
	
	@MetadataBeanName("finance/compte")
	public List<CompteBean> initUpdateFormBean(ClientContext ctx, List<CompteBean> beans) {
		Consumer<CompteBean> apply = e -> {
			e.setAction(ClientOperationEnumVd.UPDATE); 
		};
		
		beans.stream().forEach(apply);
		return beans;
	}
}
