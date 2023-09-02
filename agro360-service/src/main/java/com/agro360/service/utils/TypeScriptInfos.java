package com.agro360.service.utils;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ TYPE, FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeScriptInfos {

	public String type() default("");

	public boolean igroreSuperClassParam() default(true);
	
}
