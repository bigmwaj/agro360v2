package com.agro360.operation.rule.utils;

import com.agro360.operation.rule.common.AbstractRule;

public class RuleUtil {

	public static AbstractRule findRule(String ruleClassName) {
		try {
			var klass = RuleUtil.class.getClassLoader().loadClass(ruleClassName);
			return (AbstractRule) klass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
