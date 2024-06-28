package com.agro360.operation.rule.common;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class TrueRuleTest{

	@InjectMocks
	private TrueRule rule;

	@InjectMocks
	@Qualifier("IsTrue")
	private TrueRule ruleFromConfig;

	private ClientContext ctx;

	private AbstractBean bean;

	@Test
	void test_eval() {
		// Given

		// When
		var eval = rule.eval(ctx, bean);

		// Then
		assertTrue(eval);
	}

	@Test
	void test_eval2() {
		// Given

		// When
		var eval = ruleFromConfig.eval(ctx, bean);

		// Then
		assertTrue(eval);
	}
}