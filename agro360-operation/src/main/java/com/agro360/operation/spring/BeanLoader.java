package com.agro360.operation.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath*:spring/*.xml", "classpath*:spring/metadata/*/*.xml"})
@Configuration
public class BeanLoader {

}
