package com.kplan.phonecard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.kplan.phonecard.repository.MyRepositoryFactoryBean;

@SpringBootApplication
//@ComponentScan(basePackages = { "com.membership.core" })
//@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
@EnableJpaRepositories(repositoryFactoryBeanClass = MyRepositoryFactoryBean.class)
public class KplanPhonecardManagementApplication {
	public static void main(String[] args) {
		System.setProperty("derby.stream.error.file","logs/derby.log");
		SpringApplication.run(KplanPhonecardManagementApplication.class);
	}

}
