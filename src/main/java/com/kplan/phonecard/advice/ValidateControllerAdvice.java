package com.kplan.phonecard.advice;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.kplan.phonecard.vo.ResultMessageDTO;

/**
 * 统一处理验证失败异常 使用此切片后@Valid注解验证的参数后不用再加Errors或Bindingesult
 * 
 * @author yg.huang
 * @version v1.0 DATE 2016/11/25
 */
@ControllerAdvice
public class ValidateControllerAdvice {
	Logger logger = LoggerFactory.getLogger(ValidateControllerAdvice.class);

	/**
	 * bean校验未通过异常
	 *
	 * @see javax.validation.Valid
	 * @see org.springframework.validation.Validator
	 * @see org.springframework.validation.DataBinder
	 */
	@ExceptionHandler(BindException.class)
	public String validExceptionHandler(BindException e, WebRequest request, HttpServletResponse response) {

		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		for (FieldError error : fieldErrors) {
			logger.error(error.getField() + ":" + error.getDefaultMessage());
		}
		request.setAttribute("fieldErrors", fieldErrors, WebRequest.SCOPE_REQUEST);
		if (AjaxUtils.isAjaxRequest(request) && CollectionUtils.isNotEmpty(fieldErrors)) {
			String errormsg = fieldErrors.stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.joining(System.lineSeparator() + "<br>"));
			ResultMessageDTO<Object> result = ResultMessageDTO.failed(errormsg);
			response.setContentType("application/json;charset=UTF-8");
			AjaxUtils.writeJson(result, response);

			/*
			 * Map<String, Object> attrMap = new HashMap<String, Object>(); String[]
			 * atrrNames = request.getAttributeNames(WebRequest.SCOPE_REQUEST); for (String
			 * attr : atrrNames) { Object value = request.getAttribute(attr,
			 * WebRequest.SCOPE_REQUEST); if (value instanceof Serializable) {
			 * attrMap.put(attr, value); }
			 * 
			 * } AjaxUtils.writeJson(attrMap, response);
			 */
			return null;
		}

		return "/validError";
	}

}