package com.agro360.service.rule.constraint.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class AllTest extends AbstractCollectionBaseRuleTest{

	@Override
	protected AbstractRule getRuleInstance() {
		return new All();
	}

	@Test
	void shouldReturnFalseWhenOneRuleIsFalse() {
		// Given
		TestBean bean = new TestBean();
		bean.getStringField().setValue("TEST");

		// When
		when(appCtxMock.getBean(IsMaxLengthOk.class.getName())).thenReturn(new IsMaxLengthOk());
		boolean result = rule.apply(bean, FIELD_NAME, Map.of(Any.PARAM_KEY, 
				Map.of(
					IsNonNull.class.getName(), Collections.emptyMap(),
					IsMaxLengthOk.class.getName(), Map.of(IsMaxLengthOk.PARAM_KEY, 2))));

		// Then
		assertFalse(result);
	}

	@Test
	void shouldReturnTrueWhenAllAreTrue() {
		// Given
		TestBean bean = new TestBean();
		bean.getStringField().setValue("TEST");

		// When
		when(appCtxMock.getBean(IsNonNull.class.getName())).thenReturn(new IsNonNull());
		when(appCtxMock.getBean(IsMaxLengthOk.class.getName())).thenReturn(new IsMaxLengthOk());
		boolean result = rule.apply(bean, FIELD_NAME, Map.of(Any.PARAM_KEY, 
				Map.of(
					IsNonNull.class.getName(), Collections.emptyMap(), 
					IsMaxLengthOk.class.getName(), Map.of(IsMaxLengthOk.PARAM_KEY, 5))));

		// Then
		assertTrue(result);
	}
}