package com.agro360.service.rule.helper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.function.Executable;

import com.agro360.service.rule.constraint.common.IsEditable;
import com.agro360.service.rule.lookup.common.RootBeanLookup;

@TestInstance(Lifecycle.PER_CLASS)
public class RuleXmlHelperTest {

	@Test
	void loadMetadataFromXmlTest() {
		// Given
		var helper = new RuleXmlHelper();
		var xmlName = "stock/article";

		// When
		var rules = helper.loadRulesFromXml(xmlName);
		
		// Then
		Executable editableConstraint = () -> assertAll(
			() -> assertNotNull(rules.getEditableContraints().get(0)),
			() -> assertNotNull(rules.getEditableContraints().get(0).getRule()),
			() -> assertEquals(rules.getEditableContraints().get(0).getRule().size(), 1),
			() -> assertEquals(rules.getEditableContraints().get(0).getRule().get(0).getRuleClass(), IsEditable.class.getName()),
			() -> assertEquals(rules.getEditableContraints().get(0).getRule().get(0).getLookupClass(), RootBeanLookup.class.getName())
		);
		
		Executable unite = () -> assertAll(
			() -> assertNotNull(rules.getBean()),
			() -> assertEquals(rules.getBean().size(), 1),
			() -> assertEquals(rules.getBean().get(0).getName(), "unite"),
			() -> assertTrue(rules.getBean().get(0).isEditable()),
			() -> assertTrue(rules.getBean().get(0).isVisible()),
			() -> assertNotNull(rules.getBean().get(0).getField()),
			() -> assertEquals(rules.getBean().get(0).getField().size(), 2)
		);
		
		assertAll(
			() -> assertNotNull(rules),
			() -> assertTrue(rules.isEditable()),
			() -> assertTrue(rules.isVisible()),
			() -> assertEquals(rules.getNamespace(), "default"),
			editableConstraint,
			unite
		);
	}
}
