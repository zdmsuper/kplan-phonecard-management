package com.kplan.phonecard.resolvers;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import com.kplan.phonecard.web.annotation.MultiRequestParam;

/**
 * 多RequestBody解析器
 *
 * @author 明明如月
 * @date 2018/08/27
 */
public class MultiRequestParamArgumentResolver extends ServletModelAttributeMethodProcessor {

	public MultiRequestParamArgumentResolver() {
		super(true);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation((Class) MultiRequestParam.class);
	}

	@Override
	protected String getRequestValueForAttribute(String attributeName, NativeWebRequest request) {
		return attributeName;
	}

	@Override
	protected Object createAttributeFromRequestValue(String sourceValue, String attributeName,
			MethodParameter parameter, WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {

		String fieldDefaultPrefix = this.buildFieldDefaultPrefix(sourceValue, attributeName, parameter);

		if (fieldDefaultPrefix != null) {
			request.setAttribute("fieldDefaultPrefix", fieldDefaultPrefix, 0);
		}
		return super.createAttributeFromRequestValue(sourceValue, attributeName, parameter, binderFactory, request);

	}

	@Override
	protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
		String fieldDefaultPrefix = (String) request.getAttribute("fieldDefaultPrefix", 0);
		if (fieldDefaultPrefix != null) {
			binder.setFieldDefaultPrefix(fieldDefaultPrefix);
		}
		super.bindRequestParameters(binder, request);
	}

	protected String buildFieldDefaultPrefix(String sourceValue, String attributeName, MethodParameter parameter) {

		String paramName = parameter.getParameterAnnotation(MultiRequestParam.class).value();

		final String name = ModelFactory.getNameForParameter(parameter);
		String fieldDefaultPrefix = null;
		if (name.equals(attributeName)) {
			if (paramName != null && paramName.trim().length() > 0) {
				fieldDefaultPrefix = paramName + ".";
			}
		}

		return fieldDefaultPrefix;

	}

}