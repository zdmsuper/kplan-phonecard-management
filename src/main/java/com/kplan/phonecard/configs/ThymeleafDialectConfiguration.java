package com.kplan.phonecard.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
public class ThymeleafDialectConfiguration {
//	@Value("${rule.contextPath}")
//	private String context;

	@Bean
	public SpringSecurityDialect springDataDialect() {
		return new SpringSecurityDialect();
	}

}