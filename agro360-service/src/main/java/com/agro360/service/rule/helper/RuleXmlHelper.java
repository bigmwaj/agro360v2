package com.agro360.service.rule.helper;

import java.util.Collection;
import java.util.List;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanContext;
import com.agro360.service.rule.constraint.common.AbstractConstraint;
import com.agro360.service.rule.lookup.common.AbstractBeanLookup;
import com.agro360.service.rule.lookup.common.RootBeanLookup;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class RuleXmlHelper {

	public BeanHelper loadRulesFromXml(String xmlName) {
		try {
			var xmlPath = "rules/" + xmlName + ".xml";
			var xml = getClass().getClassLoader().getResourceAsStream(xmlPath);
			var xmlMapper = new XmlMapper();
			return xmlMapper.readValue(xml, BeanHelper.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void applyAllRules(BeanContext ctx, AbstractBean bean, BeanHelper beanWithRules) {
		applyAllRules(ctx, bean, bean, beanWithRules);
	}

	public void applyValidationRules(BeanContext ctx, AbstractBean bean, BeanHelper beanWithRules) {
		applyValidationRules(ctx, bean, bean, beanWithRules);
	}

	private void applyAllRules(BeanContext ctx, AbstractBean root, AbstractBean bean, BeanHelper beanWithRules) {
		bean.setVisible(isVisible(ctx, root, bean, beanWithRules));
		bean.setEditable(isEditable(ctx, root, bean, beanWithRules));

		if (beanWithRules.getField() != null) {
			beanWithRules.getField().stream().forEach(e -> applyAllRules(ctx, root, bean, e));
		}

		if (beanWithRules.getBean() != null) {
			beanWithRules.getBean().stream().forEach(e -> applyAllRules(ctx, root, bean.getBean(e.getName()), e));
		}

		if (beanWithRules.getBeanList() != null) {
			beanWithRules.getBeanList().stream().forEach(e -> applyAllRules(ctx, root, bean.getBeans(e.getName()), e));
		}
	}

	private void applyAllRules(BeanContext ctx, AbstractBean root, Collection<AbstractBean> beans, BeanHelper beanWithRules) {
		beans.stream().forEach(e -> applyAllRules(ctx, root, e, beanWithRules));
	}

	private void applyAllRules(BeanContext ctx, AbstractBean root, AbstractBean bean, FieldMetadataHelper fieldRule) {
		var fieldMetadata = bean.getField(fieldRule.getName());

		fieldMetadata.setVisible(isVisible(ctx, root, bean, fieldRule));
		fieldMetadata.setEditable(isEditable(ctx, root, bean, fieldRule));
		fieldMetadata.setRequired(isRequired(ctx, root, bean, fieldRule));
	}
	
	private void applyValidationRules(BeanContext ctx, AbstractBean root, AbstractBean bean, BeanHelper beanWithRules) {
		
		evalValidations(ctx, root, bean, beanWithRules.getValidation());
		
		if (beanWithRules.getField() != null) {
			beanWithRules.getField().stream().forEach(e -> applyValidationRules(ctx, root, bean, e));
		}
		
		if (beanWithRules.getBean() != null) {
			beanWithRules.getBean().stream().forEach(e -> applyValidationRules(ctx, root, bean.getBean(e.getName()), e));
		}
		
		if (beanWithRules.getBeanList() != null) {
			beanWithRules.getBeanList().stream().forEach(e -> applyValidationRules(ctx, root, bean.getBeans(e.getName()), e));
		}
	}
	
	private void applyValidationRules(BeanContext ctx, AbstractBean root, Collection<AbstractBean> beans, BeanHelper rule) {
		beans.stream().forEach(e -> applyValidationRules(ctx, root, e, rule));
	}

	private void applyValidationRules(BeanContext ctx, AbstractBean root, AbstractBean bean, FieldMetadataHelper fieldRule) {
		// TODO Add standard validation!
		evalValidations(ctx, root, bean, fieldRule.getValidation());
	}
	
	private boolean evalValidations(BeanContext ctx, AbstractBean root, AbstractBean bean, List<ValidationHelper> validations) {
		return validations.stream()
				.filter(e -> evalConstraints(ctx, root, bean, e.getConstraint()))
				.allMatch(e -> evalRules(ctx, root, bean, e.getRule()));
	}

	private AbstractBeanLookup findLookup(String lookupName) {
		if (lookupName == null) {
			return new RootBeanLookup();
		}
		try {
			var klass = getClass().getClassLoader().loadClass(lookupName);
			return (AbstractBeanLookup) klass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private AbstractConstraint findConstraint(String constraintName) {
		try {
			var klass = getClass().getClassLoader().loadClass(constraintName);
			return (AbstractConstraint) klass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private boolean evalRule(BeanContext ctx, AbstractBean root, AbstractBean bean, RuleHelper beanWithRules) {
		var constraint = findConstraint(beanWithRules.getRuleClass());
		var beanLookup = findLookup(beanWithRules.getLookupClass());
		var targetBean = beanLookup.lookup(root, bean);

		var val = constraint.apply(ctx, targetBean);

		return val;
	}

	private boolean evalRules(BeanContext ctx, AbstractBean root, AbstractBean bean, List<RuleHelper> rules) {
		return rules.stream().allMatch(e->evalRule(ctx, root, bean, e));
	}

	private boolean evalConstraint(BeanContext ctx, AbstractBean root, AbstractBean bean, ConstraintHelper constraint) {
		return constraint.getRule().stream().map(e -> evalRule(ctx, root, bean, e)).noneMatch(Boolean.FALSE::equals);
	}

	private boolean evalConstraints(BeanContext ctx, AbstractBean root, AbstractBean bean, List<ConstraintHelper> constraints) {
		return constraints.stream().anyMatch(e -> evalConstraint(ctx, root, bean, e));
	}

	private boolean isVisible(BeanContext ctx, AbstractBean root, AbstractBean bean, BeanHelper beanWithRules) {
		if (beanWithRules.getVisibleContraints() != null && !beanWithRules.getVisibleContraints().isEmpty()) {
			return beanWithRules.getVisibleContraints().stream().map(e -> evalConstraint(ctx, root, bean, e))
					.noneMatch(Boolean.FALSE::equals);
		}
		return beanWithRules.isVisible();
	}

	private boolean isEditable(BeanContext ctx, AbstractBean root, AbstractBean bean, BeanHelper beanWithRules) {
		if (beanWithRules.getEditableContraints() != null && !beanWithRules.getEditableContraints().isEmpty()) {
			return beanWithRules.getEditableContraints().stream().map(e -> evalConstraint(ctx, root, bean, e))
					.noneMatch(Boolean.FALSE::equals);
		}
		return beanWithRules.isEditable();
	}

	private boolean isVisible(BeanContext ctx, AbstractBean root, AbstractBean bean, FieldMetadataHelper fieldWithRules) {
		if (fieldWithRules.getVisibleContraints() != null && !fieldWithRules.getVisibleContraints().isEmpty()) {
			return fieldWithRules.getVisibleContraints().stream().map(e -> evalConstraint(ctx, root, bean, e))
					.noneMatch(Boolean.FALSE::equals);
		}
		return fieldWithRules.isVisible();
	}

	private boolean isEditable(BeanContext ctx, AbstractBean root, AbstractBean bean, FieldMetadataHelper fieldWithRules) {
		if (fieldWithRules.getEditableContraints() != null && !fieldWithRules.getEditableContraints().isEmpty()) {
			return fieldWithRules.getEditableContraints().stream().map(e -> evalConstraint(ctx, root, bean, e))
					.noneMatch(Boolean.FALSE::equals);
		}
		return fieldWithRules.isEditable();
	}

	private boolean isRequired(BeanContext ctx, AbstractBean root, AbstractBean bean, FieldMetadataHelper fieldWithRules) {
		if (fieldWithRules.getRequiredContraints() != null && !fieldWithRules.getRequiredContraints().isEmpty()) {
			return fieldWithRules.getRequiredContraints().stream().map(e -> evalConstraint(ctx, root, bean, e))
					.noneMatch(Boolean.FALSE::equals);
		}
		return fieldWithRules.isRequired();
	}
}
