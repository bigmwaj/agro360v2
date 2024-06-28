package com.agro360.operation.spring;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestConfiguration()
@ComponentScan(basePackages = {
	"com.agro360.operation.logic",
	"com.agro360.operation.rule",
	"com.agro360.operation.validator",
	"com.agro360.operation.spring",
})
@EntityScan(basePackages = {
	"com.agro360.dto",
})
@EnableJpaRepositories(basePackages = {
	"com.agro360.dao",
})
public class TestConfig {

}
