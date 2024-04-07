package com.agro360.operation.utils;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateInput {

	public String namespace();

	public String initNamespace();
	
}
