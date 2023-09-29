package com.agro360.service.rule.constraint.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.agro360.service.bean.common.AbstractBean;

@TestInstance(Lifecycle.PER_CLASS)
public class IsNonNullTest {

	AbstractRule rule;

	final static String FIELD_NAME = "stringField";

	@BeforeAll()
	void setup() {
		rule = new IsNonNull();
	}

	@Test
	void shouldReturnFalseWhenValueIsNull() {
		// Given
		AbstractBean bean = new TestBean();

		// When
		boolean result = rule.apply(bean, FIELD_NAME, Collections.emptyMap());

		// Then
		assertFalse(result);
	}

	@Test
	void shouldReturnTrueWhenValueIsNotNull() {
		// Given
		TestBean bean = new TestBean();
		bean.getStringField().setValue("Test");

		// When
		boolean result = rule.apply(bean, FIELD_NAME, Collections.emptyMap());

		// Then
		assertTrue(result);
	}
}