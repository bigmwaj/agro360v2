package com.agro360.operation.metadata;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;

import lombok.Data;

@Data
public class BeanMetadataConfig {

	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}

	private List<AbstractRule> editable;
	
	private Map<String, FieldMetadataConfig> fields;
	
	private Map<String, BeanMetadataConfig> beans;
	
	private Map<String, BeanMetadataConfig> beanList;

	public void applyMetadata(ClientContext ctx, AbstractBean bean) {
		// Le développeur peut initialiser le root
		if( bean.getRootBean() == null ) {
			bean.setRootBean(bean);
		}
		
		// Le développeur peut initialiser le owner
		if( bean.getOwnerBean() == null ) {
			bean.setOwnerBean(bean);
		}
		applyMetadata(ctx, bean, true);
	}
	
	private void applyMetadata(ClientContext ctx, AbstractBean bean, boolean isOwnerEditable) {
		
		if( editable != null && !editable.isEmpty() ) {
			isOwnerEditable = isOwnerEditable && evalRules(ctx, bean, editable);
		}
		
		if( fields != null && !fields.isEmpty() ) {
			for (var fieldConfig : fields.entrySet()) {
				fieldConfig.getValue().applyMetadata(ctx, bean, fieldConfig.getKey(), isOwnerEditable);
			}
		}
		
		if( beans != null && !beans.isEmpty() ) {
			for (var beanConfig : beans.entrySet()) {
				var childBean = bean.getBean(beanConfig.getKey());
				childBean.setOwnerBean(bean);
				childBean.setRootBean(bean.getRootBean());
				
				beanConfig.getValue().applyMetadata(ctx, childBean, isOwnerEditable);
			}
		}
		
		if( beanList != null && !beanList.isEmpty() ) {
			for (var beanConfig : beanList.entrySet()) {
				var beans = bean.getBeans(beanConfig.getKey());
				for (var childBean : beans) {
					childBean.setOwnerBean(bean);
					childBean.setRootBean(bean.getRootBean());
					
					beanConfig.getValue().applyMetadata(ctx, childBean, isOwnerEditable);					
				}
			}
		}
	}

	private boolean evalRules(ClientContext ctx, AbstractBean bean, List<AbstractRule> rules) {
		Predicate<AbstractRule> evalRule = e -> e.eval(ctx, bean);
		return rules.stream().allMatch(evalRule);
	}
}
