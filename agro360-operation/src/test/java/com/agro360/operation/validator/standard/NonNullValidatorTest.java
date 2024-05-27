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
public class NonNullValidatorTest {

	@InjectMocks
	private NonNullValidator validator;
	
	private ClientContext ctx;

	private PartnerBean buildBasedPartnerCode(String value) {
		var bean = new PartnerBean();
		bean.getPartnerCode().setRequired(true);
		bean.getPartnerCode().setValue(value);
		
		return bean;
	}
	
	@Test
	void test_validate() {
		// Given
		var _null = buildBasedPartnerCode(null);
		var _empty = buildBasedPartnerCode("");
		var _blank = buildBasedPartnerCode("  ");
		var _notNullNotBlank = buildBasedPartnerCode(" -- ");
		
		// When
		validator.validate(ctx, _null, "partnerCode");
		validator.validate(ctx, _empty, "partnerCode");
		validator.validate(ctx, _blank, "partnerCode");
		validator.validate(ctx, _notNullNotBlank, "partnerCode");
		
		// Then
		assertAll(
			() -> assertTrue(_null.getPartnerCode().getMessages().stream().map(Message::getType).allMatch(MessageTypeEnumVd.ERROR::equals)),
			() -> assertTrue(_empty.getPartnerCode().getMessages().stream().map(Message::getType).allMatch(MessageTypeEnumVd.ERROR::equals)),
			() -> assertTrue(_blank.getPartnerCode().getMessages().stream().map(Message::getType).allMatch(MessageTypeEnumVd.ERROR::equals)),
			() -> assertTrue(_notNullNotBlank.getPartnerCode().getMessages().isEmpty())
		);
		
	}
}