package com.agro360.service.rule.helper;

import java.io.IOException;
import java.util.Collection;

import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanContext;
import com.agro360.service.rule.constraint.common.AbstractConstraint;
import com.agro360.service.rule.lookup.common.AbstractBeanLookup;
import com.agro360.service.rule.lookup.common.RootBeanLookup;
import com.ctc.wstx.api.WstxInputProperties;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class RuleXmlHelper {

	protected BeanMetadataHelper loadMetadataFromXml(String xmlName)  throws IOException{
		var xml = getClass().getClassLoader().getResourceAsStream( "rules/" + xmlName + ".xml");
		
		var xmlMapper = new XmlMapper();
		
		xmlMapper.getFactory().getXMLInputFactory().setProperty(
		    WstxInputProperties.P_UNDECLARED_ENTITY_RESOLVER,
		    new XMLResolver() {
		        @Override
		        public Object resolveEntity(String publicId, String systemId, String baseUri, String ns) throws XMLStreamException {
		            // replace the entity with a string of your choice, e.g.
		            switch (ns) {
		                case "nbsp": 
		                    return " ";
		                default: 
		                    return "";
		            }
		            // some useful tool is org.apache.commons.text.StringEscapeUtils
		            // e.g.
		            // return StringEscapeUtils.escapeXml10(StringEscapeUtils.unescapeHtml4('&' + ns + ';'));
		        }
		    }
		);
		
		return xmlMapper.readValue(xml, BeanMetadataHelper.class);
	}
	
	public void applyRules(BeanContext ctx, AbstractBean bean) {
		try {
			var rule = loadMetadataFromXml(ctx.getRuleName());
			applyRules(ctx, rule, bean, bean);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void applyRules(BeanContext ctx, BeanMetadataHelper rule, AbstractBean root, AbstractBean bean) {
		bean.setVisible(isVisible(ctx, rule, root, bean));
		bean.setEditable(isEditable(ctx, rule, root, bean));
		
		if( rule.getField() != null ) {
			rule.getField().stream().forEach(e -> applyRules(ctx, e, root, bean));
		}
		
		if( rule.getBean() != null ) {
			rule.getBean().stream().forEach(e -> applyRules(ctx, e, root, bean.getBean(e.getName())));
		}

		if( rule.getBeanList() != null ) {
			rule.getBeanList().stream().forEach(e -> applyRules(ctx, e, root, bean.getBeans(e.getName())));
		}
	}
	
	private void applyRules(BeanContext ctx, BeanMetadataHelper rule, AbstractBean root, Collection<AbstractBean> beans) {
		beans.stream().forEach(e -> applyRules(ctx, rule, root, e));
	}
	
	private void applyRules(BeanContext ctx, FieldMetadataHelper fieldRule, AbstractBean root, AbstractBean bean) {
		var fieldMetadata = bean.getField(fieldRule.getName());
		
		fieldMetadata.setVisible(isVisible(ctx, fieldRule, root, bean));
		fieldMetadata.setEditable(isEditable(ctx, fieldRule, root, bean));
		fieldMetadata.setRequired(isRequired(ctx, fieldRule, root, bean));
	}
	
	private AbstractBeanLookup findLookup(String lookupName) {
		if( lookupName == null ) {
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
	
	private boolean eval(BeanContext ctx, RuleHelper rule, AbstractBean root, AbstractBean bean) {
		var constraint = findConstraint(rule.getRuleClass());
		var beanLookup = findLookup(rule.getLookupClass());
		var targetBean = beanLookup.lookup(root, bean);
		
		var val = constraint.apply(ctx, targetBean);
		
		return val;
	}
	
	private boolean eval(BeanContext ctx, ConstraintHelper constraint, AbstractBean root, AbstractBean bean) {
		return constraint.getRule().stream()
				.map(e -> eval(ctx, e, root, bean))
				.noneMatch(Boolean.FALSE::equals);
	}
	
	private boolean isVisible(BeanContext ctx, BeanMetadataHelper rule, AbstractBean root, AbstractBean bean) {
		if( rule.getVisibleContraints() != null && !rule.getVisibleContraints().isEmpty() ) {
			return rule.getVisibleContraints().stream()
					.map(e -> eval(ctx, e, root, bean))
					.noneMatch(Boolean.FALSE::equals);
		}
		return rule.isVisible();
	}
	
	private boolean isEditable(BeanContext ctx, BeanMetadataHelper rule, AbstractBean root, AbstractBean bean) {
		if( rule.getEditableContraints() != null && !rule.getEditableContraints().isEmpty() ) {
			return rule.getEditableContraints().stream()
					.map(e -> eval(ctx, e, root, bean))
					.noneMatch(Boolean.FALSE::equals);
		}
		return rule.isEditable();
	}
	
	private boolean isVisible(BeanContext ctx, FieldMetadataHelper field, AbstractBean root, AbstractBean bean) {
		if( field.getVisibleContraints() != null && !field.getVisibleContraints().isEmpty() ) {
			return field.getVisibleContraints().stream()
					.map(e -> eval(ctx, e, root, bean))
					.noneMatch(Boolean.FALSE::equals);
		}
		return field.isVisible();
	}
	
	private boolean isEditable(BeanContext ctx, FieldMetadataHelper field, AbstractBean root, AbstractBean bean) {
		if( field.getEditableContraints() != null && !field.getEditableContraints().isEmpty() ) {
			return field.getEditableContraints().stream()
					.map(e -> eval(ctx, e, root, bean))
					.noneMatch(Boolean.FALSE::equals);
		}
		return field.isEditable();
	}
	
	private boolean isRequired(BeanContext ctx, FieldMetadataHelper fieldRule, AbstractBean root, AbstractBean bean) {
		if( fieldRule.getRequiredContraints() != null && !fieldRule.getRequiredContraints().isEmpty() ) {
			return fieldRule.getRequiredContraints().stream()
					.map(e -> eval(ctx, e, root, bean))
					.noneMatch(Boolean.FALSE::equals);
		}
		return fieldRule.isRequired();
	}
}
