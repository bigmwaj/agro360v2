package com.agro360.operation.rule.module.core;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.core.CategoryBean;
import com.agro360.bo.bean.core.PartnerCategoryBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.operation.rule.exception.RuleException;

@Component
public class IsRootCategory extends AbstractRule {
	
	@Override
	public boolean eval(ClientContext ctx, AbstractBean bean) {
		String categoryCode = null;
		if( bean instanceof PartnerCategoryBean ) {
			categoryCode = ((PartnerCategoryBean)bean).getCategoryCode().getValue();
		}else if( bean instanceof CategoryBean ) {
			categoryCode = ((CategoryBean) bean).getCategoryCode().getValue();
		}else {
			throw new RuleException("Cette règle s'applique pour les PartnerCategoryBean et CategoryBean");
		}
		return "ROOT".equals(categoryCode);
	}
}