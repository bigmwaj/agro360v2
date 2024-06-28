package com.agro360.form.finance;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.bean.finance.CompteSearchBean;
import com.agro360.bo.mapper.FinanceMapper;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.core.PartnerOperation;
import com.agro360.operation.logic.finance.CompteOperation;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class CompteForm {
	
	@Autowired
	private CompteOperation operation;
	
	@Autowired
	private PartnerOperation partnerOperation;

	@MetadataBeanName("finance/compte-search")
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
		bean.getPartner().getPartnerCode().setValueOptions(getPartnerOption(ctx));
		return bean;
	}
	
	@MetadataBeanName("finance/compte")
	public List<CompteBean> initUpdateFormBean(ClientContext ctx, List<CompteBean> beans) {
		final var  options = getPartnerOption(ctx);
		Consumer<CompteBean> apply = e -> {
			e.setAction(ClientOperationEnumVd.UPDATE);
			e.getPartner().getPartnerCode().setValueOptions(options);
		};
		
		beans.stream().forEach(apply);
		return beans;
	}
	
	private Map<Object, String> getPartnerOption(ClientContext ctx) {
		Function<PartnerBean, Object> codeFn = e -> e.getPartnerCode().getValue();
		Function<PartnerBean, String> libelleFn = PartnerBean::partnerBean2String;		
		
		return partnerOperation.findPartnersByCriteria(ctx, new PartnerSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));
	}
}
