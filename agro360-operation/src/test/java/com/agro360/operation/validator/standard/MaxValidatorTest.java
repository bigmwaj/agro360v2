package com.agro360.operation.validator.standard;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.message.Message;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.common.MessageTypeEnumVd;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class MaxValidatorTest {

	@InjectMocks
	private MaxValidator validator;
	
	private ClientContext ctx;

	private LigneBean bean;
	
	@Test
	void test_should_generate_error() {
		// Given
		bean = new LigneBean();
		bean.getPrixUnitaire().setMax(10.0);
		bean.getPrixUnitaire().setValue(BigDecimal.valueOf(10.01));
		
		// When
		validator.validate(ctx, bean, "prixUnitaire");
		
		// Then
		assertTrue(bean.getPrixUnitaire().getMessages().stream().map(Message::getType).allMatch(MessageTypeEnumVd.ERROR::equals));
		
	}
	
	@Test
	void test_should_not_generate_error() {
		// Given
		bean = new LigneBean();
		bean.getPrixUnitaire().setMax(10.);
		bean.getPrixUnitaire().setValue(BigDecimal.valueOf(10));
		
		// When
		validator.validate(ctx, bean, "prixUnitaire");
		
		// Then
		assertTrue(bean.getPrixUnitaire().getMessages().isEmpty());
		
	}
}