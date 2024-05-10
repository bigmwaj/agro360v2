package com.agro360.service.common;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.metadata.BeanMetadataConfig;

@Aspect
@Configuration
public class UserInputValidatorProxy {
	
	private static Logger logger = LoggerFactory.getLogger(UserInputValidatorProxy.class);
	
	@Autowired
	private BeanFactory beanFactory;
	
	private String getServiceName(JoinPoint joinPoint) {
		return String.format("%s.%s", 
        		Objects.toString(joinPoint.getTarget().getClass().getSimpleName()),
        		Objects.toString(joinPoint.getSignature().getName()));
	}

	@Before(value = "execution(public * com.agro360.service.logic..*.*(..)) && @annotation(userInput)", 
			argNames = "joinPoint,userInput")
    public void preValidate(JoinPoint joinPoint, UserInput userInput) {
        logger.debug("{} -> Post validation start ...", getServiceName(joinPoint));
        logger.debug("{} -> Rule validation metadata bean name: {}", getServiceName(joinPoint), userInput.metadataBeanName());
        
        var serviceName = getServiceName(joinPoint);
        logger.debug("{} -> Apply metadata.", serviceName);
        
        var beanName =  userInput.metadataBeanName();
        
        var args = joinPoint.getArgs();
        if( args.length == 0 || !(args[0] instanceof ClientContext )) {
        	var msgTpl = "Le premier argument du service %s doit être de type %s";
        	throw new RuntimeException(String.format(msgTpl, serviceName, ClientContext.class));
        }
        
        var ctx = (ClientContext) args[0];
        
        var inputData = args[1];
        
        if( inputData instanceof AbstractBean ) {
        	var bean = (AbstractBean) inputData;
        	applyMetadataAndValidate(ctx, beanName, bean);
        }else if( inputData instanceof Collection ) {
        	var beans = (Collection<?>) inputData;
        	var anyBean = beans.stream().findAny().orElse(null);
        	if( anyBean != null && !(anyBean instanceof AbstractBean)) {
        		var msgTpl = "La valeur de retour du service %s doit être une collection de type %ss";
            	throw new RuntimeException(String.format(msgTpl, serviceName, AbstractBean.class));
        	}
        	var castedBeans = beans.stream()
        			.map(AbstractBean.class::cast)
        			.collect(Collectors.toList());
        	applyMetadataAndValidate(ctx, beanName, castedBeans);
        }else {
        	var msgTpl = "La valeur de retour du service %s doit être de type %s ou %s";
        	throw new RuntimeException(String.format(msgTpl, serviceName, AbstractBean.class, Collection.class));
        }

        logger.debug("{} -> Post validation end.", getServiceName(joinPoint));
	}

	private void applyMetadataAndValidate(ClientContext ctx, String beanName, AbstractBean bean) {
		var config = beanFactory.getBean(beanName, BeanMetadataConfig.class);
		config.applyMetadata(ctx, bean);
		applyMetadataAndValidate(ctx, config, bean);
	}
	
	private void applyMetadataAndValidate(ClientContext ctx, String beanName, Collection<AbstractBean> beans) {
		var config = beanFactory.getBean(beanName, BeanMetadataConfig.class);
		for (var bean : beans) {
			applyMetadataAndValidate(ctx, config, bean);
		}
	}
	
	private void applyMetadataAndValidate(ClientContext ctx, BeanMetadataConfig config, AbstractBean bean) {
		config.applyMetadata(ctx, bean);
	}
	
}
