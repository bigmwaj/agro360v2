package com.agro360.form.common;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
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
public class FormMetadataProxy {
	
	@Autowired
	private BeanFactory beanFactory;
	
	private static Logger logger = LoggerFactory.getLogger(FormMetadataProxy.class);
	
	private String getServiceName(JoinPoint joinPoint) {
		return String.format("%s.%s", 
        		Objects.toString(joinPoint.getTarget().getClass().getSimpleName()),
        		Objects.toString(joinPoint.getSignature().getName()));
	}
	
	@AfterReturning(
			value = "execution(public * com.agro360.form..*.*(..)) && @annotation(metadataBeanName)", 
			returning = "retVal",
			argNames = "joinPoint,retVal,metadataBeanName")
    public void applyMetadata(JoinPoint joinPoint, Object retVal, MetadataBeanName metadataBeanName) {
		var serviceName = getServiceName(joinPoint);
        logger.debug("{} -> Apply metadata.", serviceName);
        
        var beanName =  metadataBeanName.value();
        
        var args = joinPoint.getArgs();
        if( args.length == 0 || !(args[0] instanceof ClientContext )) {
        	var msgTpl = "Le premier argument du service %s doit être de type %s";
        	throw new RuntimeException(String.format(msgTpl, serviceName, ClientContext.class));
        }
        
        var ctx = (ClientContext) args[0];
        if( retVal instanceof AbstractBean ) {
        	var bean = (AbstractBean) retVal;
        	applyMetadata(ctx, beanName, bean);
        }else if( retVal instanceof Collection ) {
        	var beans = (Collection<?>) retVal;
        	var anyBean = beans.stream().findAny().orElse(null);
        	if( anyBean != null && !(anyBean instanceof AbstractBean)) {
        		var msgTpl = "La valeur de retour du service %s doit être une collection de type %ss";
            	throw new RuntimeException(String.format(msgTpl, serviceName, AbstractBean.class));
        	}
        	var castedBeans = beans.stream()
        			.map(AbstractBean.class::cast)
        			.collect(Collectors.toList());
        	applyMetadata(ctx, beanName, castedBeans);
        }else {
        	var msgTpl = "La valeur de retour du service %s doit être de type %s ou %s";
        	throw new RuntimeException(String.format(msgTpl, serviceName, AbstractBean.class, Collection.class));
        }
    }
	
	private void applyMetadata(ClientContext ctx, String beanName, AbstractBean bean) {
		var config = beanFactory.getBean(beanName, BeanMetadataConfig.class);
		config.applyMetadata(ctx, bean);
		applyMetadata(ctx, config, bean);
	}
	
	private void applyMetadata(ClientContext ctx, String beanName, Collection<AbstractBean> beans) {
		var config = beanFactory.getBean(beanName, BeanMetadataConfig.class);
		for (var bean : beans) {
			applyMetadata(ctx, config, bean);
		}
	}
	
	private void applyMetadata(ClientContext ctx, BeanMetadataConfig config, AbstractBean bean) {
		config.applyMetadata(ctx, bean);
	}
	
}
