package com.agro360.operation.validator.standard;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.message.Message;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.common.MessageTypeEnumVd;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class MaxLengthValidatorTest {

	@InjectMocks
	private MaxLengthValidator validator;
	
	private ClientContext ctx;

	private PartnerBean buildBasedPartnerCode(String value) {
		var bean = new PartnerBean();
		bean.getPartnerCode().setMaxLength(10);
		bean.getPartnerCode().setValue(value);
		
		return bean;
	}
	
	@Test
	void test_validate() {
		// Given
		var less = buildBasedPartnerCode("12345678");
		var equals = buildBasedPartnerCode("1234567890");
		var more = buildBasedPartnerCode("12345678901");
		
		// When
		validator.validate(ctx, less, "partnerCode");
		validator.validate(ctx, more, "partnerCode");
		validator.validate(ctx, equals, "partnerCode");
		
		// Then
		assertAll(
			() -> assertTrue(less.getPartnerCode().getMessages().isEmpty()),
			() -> assertTrue(equals.getPartnerCode().getMessages().isEmpty()),
			() -> assertTrue(more.getPartnerCode().getMessages().stream().map(Message::getType).allMatch(MessageTypeEnumVd.ERROR::equals))
			
		);
		
	}
}