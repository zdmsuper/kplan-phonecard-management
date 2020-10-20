package com.kplan.phonecard.config;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.github.dozermapper.core.CustomConverter;
import com.github.dozermapper.core.CustomFieldMapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.classmap.ClassMap;
import com.github.dozermapper.core.fieldmap.FieldMap;
import com.github.dozermapper.spring.DozerBeanMapperFactoryBean;

@Configuration
public class DozerConfig {
	@Bean
	List<CustomConverter> customConverters() {
		CustomConverter converter = new CustomConverter() {

			@Override
			public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue,
					Class<?> destinationClass, Class<?> sourceClass) {
				if (sourceFieldValue instanceof String) {
					String str = (String) sourceFieldValue;
				}
				return sourceFieldValue;
			}
		};

		return Arrays.asList(converter);
	}

	@Bean
	public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean(@Value("classpath*:dozer/*.xml") Resource[] resources)
			throws Exception {

		final DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean = new DozerBeanMapperFactoryBean();
		dozerBeanMapperFactoryBean.setMappingFiles(resources);
		dozerBeanMapperFactoryBean.setCustomConverters(this.customConverters());
		return dozerBeanMapperFactoryBean;
	}


}
