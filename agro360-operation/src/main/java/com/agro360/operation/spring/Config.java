package com.agro360.operation.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:spring/*.xml", "classpath*:spring/metadata/*/*.xml"})
public class Config {

}
