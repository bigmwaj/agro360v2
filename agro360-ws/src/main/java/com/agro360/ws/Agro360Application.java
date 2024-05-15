package com.agro360.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@ComponentScan(basePackages = {
	"com.agro360.ws.controller",
	"com.agro360.ws.security",
	"com.agro360.ws.filter",

	"com.agro360.operation.logic",
	"com.agro360.operation.rule",
	"com.agro360.operation.validator",
	"com.agro360.operation.spring",
	
	"com.agro360.form",
	
	"com.agro360.service",
})
@EntityScan(basePackages = {
	"com.agro360.dto",
})
@EnableJpaRepositories(basePackages = {
	"com.agro360.dao",
})
@SpringBootApplication
@EnableWebSecurity
public class Agro360Application {

	public static void main(String[] args) {
		SpringApplication.run(Agro360Application.class, args);
	}
}
