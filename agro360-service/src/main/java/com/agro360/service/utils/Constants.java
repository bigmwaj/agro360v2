package com.agro360.service.utils;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Constants {

	public static final Function<String, Supplier<RuntimeException>> NOT_FOUND_EXP_SPLR = (
			String msg) -> () -> new RuntimeException(msg);

	public static final BiFunction<String, String, String> FULL_NAME_FN = (lastname, firstname) -> String
			.format("%s %s", firstname, lastname);
	

}
