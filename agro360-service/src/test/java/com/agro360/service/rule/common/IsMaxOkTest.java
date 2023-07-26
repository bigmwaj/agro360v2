package com.agro360.service.rule.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.agro360.service.bean.common.AbstractBean;

@TestInstance(Lifecycle.PER_CLASS)
public class IsMaxOkTest {

	AbstractRule rule;

	final static String FIELD_NAME = "intField";

	@BeforeAll()
	void setup() {
		rule = new IsMaxOk();
	}

	@Test
	void shouldReturnTrueWhenValueIsNull() {
		// Given
		AbstractBean bean = new TestBean();

		// When
		boolean result = rule.apply(bean, FIELD_NAME, Collections.emptyMap());

		// Then
		assertTrue(result);
	}

	@Test
	void shouldReturnFalseWhenValueMoreThanParam() {
		// Given
		TestBean bean = new TestBean();
		bean.getIntField().setValue(10);

		// When
		boolean result = rule.apply(bean, FIELD_NAME, Map.of(IsMaxOk.PARAM_KEY, 0));

		// Then
		assertFalse(result);
	}

	@Test
	void shouldReturnTrueWhenValueLessThanParam() {
		// Given
		TestBean bean = new TestBean();
		bean.getIntField().setValue(0);

		// When
		boolean result = rule.apply(bean, FIELD_NAME, Map.of(IsMaxOk.PARAM_KEY, 10));

		// Then
		assertTrue(result);
	}

	@Test
	void shouldReturnTrueWhenValueEqualsParam() {
		// Given
		TestBean bean = new TestBean();
		bean.getIntField().setValue(10);

		// When
		boolean result = rule.apply(bean, FIELD_NAME, Map.of(IsMaxOk.PARAM_KEY, 10));

		// Then
		assertTrue(result);
	}

	@Test
	void shouldReturnFalseWhenValueEqualsParamAndStrictIsOn() {
		// Given
		TestBean bean = new TestBean();
		bean.getIntField().setValue(10);

		// When
		boolean result = rule.apply(bean, FIELD_NAME, Map.of(IsMaxOk.PARAM_KEY, 10, IsMaxOk.PARAM_STRICT_KEY, true));

		// Then
		assertFalse(result);
	}
}