package com.agro360.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
	"com.agro360.web.controller",
	"com.agro360.web.mapper",
	"com.agro360.service.mapper",
	"com.agro360.service.rule",
	"com.agro360.service.envelop",
	"com.agro360.service.logic",
})
@EntityScan(basePackages = {
	"com.agro360.dto",
})
@EnableJpaRepositories(basePackages = {
	"com.agro360.dao",
})
@SpringBootApplication
public class Agro360Application {

	public static void main(String[] args) {
		SpringApplication.run(Agro360Application.class, args);
	}
	
}
