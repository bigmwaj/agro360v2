package com.agro360.service.rule.utils;

import com.agro360.service.rule.common.lookup.AbstractBeanLookup;
import com.agro360.service.rule.common.lookup.RootBeanLookup;

public class LookupUtil {

	public static AbstractBeanLookup findLookup(String lookupClassName) {
		if (lookupClassName == null) {
			return new RootBeanLookup();
		}
		try {
			var klass = LookupUtil.class.getClassLoader().loadClass(lookupClassName);
			return (AbstractBeanLookup) klass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
