package com.agro360.operation.validator;

import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.agro360.operation.utils.RuleNamespace;

@Aspect
@Configuration
public class RuleValidatorProxy {
	
	private static Logger logger = LoggerFactory.getLogger(RuleValidatorProxy.class);
	
	private String getServiceName(JoinPoint joinPoint) {
		return String.format("%s.%s", 
        		Objects.toString(joinPoint.getTarget().getClass().getSimpleName()),
        		Objects.toString(joinPoint.getSignature().getName()));
	}
	
	private void preValidate(String ruleNamespace, Object ...args ) {
		
	}

	@Before(value = "execution(public * com.agro360.operation.logic..*.*(..)) && @annotation(ruleNamespace)", 
			argNames = "joinPoint,ruleNamespace")
    public void preValidate(JoinPoint joinPoint, RuleNamespace ruleNamespace) {
        logger.debug("{} -> Post validation start ...", getServiceName(joinPoint));
        logger.debug("{} -> Rule validation namespace: {}", getServiceName(joinPoint), ruleNamespace.value());
        
        preValidate(ruleNamespace.value(), joinPoint.getArgs());

        logger.debug("{} -> Post validation end.", getServiceName(joinPoint));
	}
	
	@AfterReturning(
			value = "execution(public * com.agro360.operation.logic..*.*(..)) && @annotation(ruleNamespace)", 
			returning = "retVal",
			argNames = "joinPoint,retVal,ruleNamespace")
    public void postValidate(JoinPoint joinPoint, Object retVal, RuleNamespace ruleNamespace) {
        logger.debug("{} -> Post validation start ...", getServiceName(joinPoint));
        logger.debug("{} -> Rule validation namespace: {}", getServiceName(joinPoint), ruleNamespace.value());
        
        postValidate(ruleNamespace.value(), retVal, joinPoint.getArgs());

        logger.debug("{} -> Post validation end.", getServiceName(joinPoint));
    }
	
	private void postValidate(String ruleNamespace, Object retVal, Object ...args ) {
		
	}
	
}
