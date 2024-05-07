package com.agro360.form.finance;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.finance.RubriqueBean;
import com.agro360.bo.bean.finance.RubriqueSearchBean;
import com.agro360.bo.mapper.FinanceMapper;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.finance.RubriqueOperation;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class RubriqueForm {

	@Autowired
	RubriqueOperation operation;
	
	@MetadataBeanName("finance/rubrique-search")
	public RubriqueSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = FinanceMapper.buildRubriqueSearchBean();
		return bean;
	}

	@MetadataBeanName("finance/rubrique")
	public RubriqueBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findRubriqueByCode(ctx, e))
				.orElse(new RubriqueBean());
		bean.getRubriqueCode().setValue(null);
		bean.setAction(ClientOperationEnumVd.CREATE);
		return bean;
	}
	
	@MetadataBeanName("finance/rubrique")
	public List<RubriqueBean> initUpdateFormBean(ClientContext ctx, List<RubriqueBean> beans) {
		Consumer<RubriqueBean> apply = e -> {
			e.setAction(ClientOperationEnumVd.UPDATE); 
		};
		
		beans.stream().forEach(apply);
		return beans;
	}
}
