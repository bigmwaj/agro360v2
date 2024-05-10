package com.agro360.service.common;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInput {

	public String metadataBeanName();
	
	public String validatorBeanName();
	
}
