package com.agro360.form.common;

import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.agro360.operation.utils.RuleNamespace;

@Aspect
@Configuration
public class FormMetadataProxy {
	
	private static Logger logger = LoggerFactory.getLogger(FormMetadataProxy.class);
	
	private String getServiceName(JoinPoint joinPoint) {
		return String.format("%s.%s", 
        		Objects.toString(joinPoint.getTarget().getClass().getSimpleName()),
        		Objects.toString(joinPoint.getSignature().getName()));
	}
	
	@AfterReturning(
			value = "execution(public * com.agro360.form..*.*(..)) && @annotation(ruleNamespace)", 
			returning = "retVal",
			argNames = "joinPoint,retVal,ruleNamespace")
    public void applyMetadata(JoinPoint joinPoint, Object retVal, RuleNamespace ruleNamespace) {
        logger.debug("{} -> Post validation start ...", getServiceName(joinPoint));
        logger.debug("{} -> Rule validation namespace: {}", getServiceName(joinPoint), ruleNamespace.value());
        
        applyMetadata(ruleNamespace.value(), retVal, joinPoint.getArgs());

        logger.debug("{} -> Post validation end.", getServiceName(joinPoint));
    }
	
	private void applyMetadata(String ruleNamespace, Object retVal, Object ...args ) {
		
	}
	
}
