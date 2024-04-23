package com.agro360.operation.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.action.common.AbstractAction;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;

import lombok.Data;

@Data
public class FieldMetadataConfig {

	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}

	private List<AbstractRule> required;
	
	private List<AbstractRule> visible;
	
	private List<AbstractRule> editable;

	private List<ConstraintConfig> maxLength;
	
	private List<ConstraintConfig> label;

	private List<ConstraintConfig> max;

	private List<ConstraintConfig> min;

	private List<ConstraintConfig> valueOptions;

	private List<ConstraintConfig> tooltip;

	private List<ConstraintConfig> icon;

	public void applyMetadata(ClientContext ctx, AbstractBean bean, String fieldName, boolean isOwnerEditable) {
		applyMetadataToBooleanAttributes(ctx, bean, fieldName, isOwnerEditable);
		applyMetadataToOthersAttributes(ctx, bean, fieldName);
	}
	
	private void applyMetadataToBooleanAttributes(ClientContext ctx, AbstractBean bean, String fieldName, boolean isOwnerEditable) {
		
		var attrMap = new HashMap<String, List<AbstractRule>>();
		attrMap.put("required", required);
		attrMap.put("visible", visible);
		attrMap.put("editable", editable);
		
		Predicate<Entry<String, List<AbstractRule>>> isNotNull = e -> e.getValue() != null;
		
		Predicate<Entry<String, List<AbstractRule>>> isNotEmpty = e -> !e.getValue().isEmpty();
		
		Consumer<Entry<String, List<AbstractRule>>> apply;
		apply = e -> applyRules(ctx, bean, fieldName, e.getKey(), e.getValue(), isOwnerEditable);
		
		attrMap.entrySet().stream()
			.filter(isNotNull)
			.filter(isNotEmpty)
			.forEach(apply);
	}
	
	private void applyMetadataToOthersAttributes(ClientContext ctx, AbstractBean bean, String fieldName) {
		
		var attrMap = new HashMap<String, List<ConstraintConfig>>();
		attrMap.put("label", label);
		attrMap.put("maxLength", maxLength);
		attrMap.put("max", max);
		attrMap.put("min", min);
		attrMap.put("valueOptions", valueOptions);
		attrMap.put("tooltip", tooltip);
		attrMap.put("icon", icon);
		
		Predicate<Entry<String, List<ConstraintConfig>>> isNotNull = e -> e.getValue() != null;
		
		Predicate<Entry<String, List<ConstraintConfig>>> isNotEmpty = e -> !e.getValue().isEmpty();
		
		Consumer<Entry<String, List<ConstraintConfig>>> apply;
		apply = e -> applyConstraints(ctx, bean, fieldName, e.getKey(), e.getValue());
		
		attrMap.entrySet().stream()
			.filter(isNotNull)
			.filter(isNotEmpty)
			.forEach(apply);
	}
	
	private void applyConstraints(ClientContext ctx, AbstractBean bean, String fieldName, String attrName, List<ConstraintConfig> constraints) {
		
		Predicate<ConstraintConfig> rulesListIsNull = e -> Objects.isNull(e.getRules());
		
		Predicate<ConstraintConfig> rulesListIsEmpty = e -> e.getRules().isEmpty();
		
		Predicate<ConstraintConfig> rulesListIsEvalToTrue = e -> evalRules(ctx, bean, e.getRules());
		
		Predicate<List<AbstractAction<?>>> actionsListIsNotNull = e -> !Objects.isNull(e);
		
		Predicate<List<AbstractAction<?>>> actionsListIsNotEmpty = e -> !e.isEmpty();
		
		Consumer<AbstractAction<?>> performAction = e -> e.performAction(ctx, bean, fieldName, attrName);
		constraints.stream()
			.filter(rulesListIsNull.or(rulesListIsEmpty).or(rulesListIsEvalToTrue))
			.map(ConstraintConfig::getActions)
			.filter(actionsListIsNotNull.and(actionsListIsNotEmpty))
			.flatMap(List::stream)
			.forEach(performAction);
		
	}
	
	private void applyRules(ClientContext ctx, AbstractBean bean, String fieldName, String attrName, List<AbstractRule> constraints, boolean isOwnerEditable) {
		
		Predicate<AbstractRule> evalRule = e -> e.eval(ctx, bean);
		var eval = false;
		if( constraints != null && !constraints.isEmpty() ) {
			 eval = constraints.stream().allMatch(evalRule);
		}
		
		if( "editable".equals(attrName) ) {
			eval = eval && isOwnerEditable;
		}
		
		bean.getField(fieldName).setAttribute(attrName, eval);
		
	}
	
	private boolean evalRules(ClientContext ctx, AbstractBean bean, List<AbstractRule> rules) {
		Predicate<AbstractRule> evalRule = e -> e.eval(ctx, bean);
		return rules.stream().allMatch(evalRule);
	}

}
