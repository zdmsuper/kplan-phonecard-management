package com.kplan.phonecard.configs;

import java.io.File;
import java.io.FileNotFoundException;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

@Configuration
public class OtherConfig {
	@Bean
	MultipartConfigElement multipartConfigElement() throws FileNotFoundException {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		File tmpDir = ResourceUtils.getFile("tmp");
		if (!tmpDir.exists()) {
			tmpDir.mkdirs();
		}
		factory.setLocation(tmpDir.getAbsolutePath());
		System.out.println(tmpDir.getAbsolutePath());
		return factory.createMultipartConfig();
	}
}
