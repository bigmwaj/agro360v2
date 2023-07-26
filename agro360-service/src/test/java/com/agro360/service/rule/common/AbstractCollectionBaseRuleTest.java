package com.agro360.service.rule.common;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.context.ApplicationContext;

import com.agro360.service.bean.common.AbstractBean;

public abstract class AbstractCollectionBaseRuleTest {

	AbstractRule rule;
	
	ApplicationContext appCtxMock;

	final static String FIELD_NAME = "stringField";
	
	protected abstract AbstractRule getRuleInstance();

	@BeforeAll()
	void setup() {
		rule = getRuleInstance();
		appCtxMock = mock(ApplicationContext.class);
		rule.setAppCtx(appCtxMock);
	}

	@Test
	void shouldThrowRuleExceptionWhenParamDoesNotExist() {
		// Given
		AbstractBean bean = new TestBean();

		// When
		Executable exe = () -> rule.apply(bean, FIELD_NAME, Map.of());
		
		// Then
		assertThrows(RuleException.class, exe);

	}
	
	@Test
	void shouldThrowRuleExceptionWhenParamIsNotMapType() {
		// Given
		AbstractBean bean = new TestBean();

		// When
		Executable exe = () -> rule.apply(bean, FIELD_NAME, Map.of(Any.PARAM_KEY, new Object[] {1}));

		// Then
		assertThrows(RuleException.class, exe);
	}
	
	@Test
	void shouldThrowRuleExceptionWhenParamMapIsEmpty() {
		// Given
		AbstractBean bean = new TestBean();

		// When
		Executable exe = () -> rule.apply(bean, FIELD_NAME, Map.of(Any.PARAM_KEY, Collections.emptyMap()));
		
		// Then
		assertThrows(RuleException.class, exe);
	}

}