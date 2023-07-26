package com.agro360.service.rule.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.agro360.service.bean.common.AbstractBean;

@TestInstance(Lifecycle.PER_CLASS)
public class InTest {

	AbstractRule rule;

	final static String FIELD_NAME = "intField";

	@BeforeAll()
	void setup() {
		rule = new In();
	}

	@Test
	void shouldReturnFalseWhenValueIsNull() {
		// Given
		AbstractBean bean = new TestBean();

		// When
		boolean result = rule.apply(bean, FIELD_NAME, Map.of(In.PARAM_KEY, new Object[] {1}));

		// Then
		assertFalse(result);
	}

	@Test
	void shouldReturnFalseWhenValueIsNotNullAndParamsValuesIsEmpty() {
		// Given
		TestBean bean = new TestBean();
		bean.getIntField().setValue(0);

		// When
		boolean result = rule.apply(bean, FIELD_NAME, Map.of(In.PARAM_KEY, new Object[] {1}));

		// Then
		assertFalse(result);
	}

	@Test
	void shouldReturnFalseWhenValueIsNotNullAndParamsValuesContainsValue() {
		// Given
		TestBean bean = new TestBean();
		bean.getIntField().setValue(0);

		// When
		boolean result = rule.apply(bean, FIELD_NAME, Map.of(In.PARAM_KEY, new Object[] {0, 1}));

		// Then
		assertTrue(result);
	}
}