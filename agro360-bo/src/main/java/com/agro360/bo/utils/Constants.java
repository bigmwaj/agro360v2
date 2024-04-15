package com.agro360.bo.utils;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.dto.core.PartnerDto;
import com.agro360.vd.core.PartnerTypeEnumVd;

public class Constants {

	public static final Function<String, Supplier<RuntimeException>> NOT_FOUND_EXP_SPLR = (
			String msg) -> () -> new RuntimeException(msg);

	public static final BiFunction<String, String, String> FULL_NAME_FN = (lastname, firstname) -> String
			.format("%s %s", firstname, lastname);
	
	public static final Function<PartnerBean, String> PARTNER_BEAN2STR = bean -> 
	PartnerTypeEnumVd.PERSON.equals(bean.getPartnerType().getValue()) 
	? FULL_NAME_FN.apply(bean.getFirstName().getValue(), bean.getLastName().getValue())
	: bean.getName().getValue();
	
	public static final Function<PartnerDto, String> PARTNER_DTO2STR = dto -> 
	PartnerTypeEnumVd.PERSON.equals(dto.getPartnerType()) 
	? FULL_NAME_FN.apply(dto.getFirstName(), dto.getLastName())
			: dto.getName();
	

}
