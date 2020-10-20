package com.kplan.phonecard.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kplan.phonecard.domain.ManagerInfo;

@Controller
@RequestMapping("/index")
public class IndexController extends AbstractBaseController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping
	public String index(Map<String, Object> map) {
		try {
			map.put("basicUserInfo", this.getCurrentUserDetails().map(ManagerInfo::getBasicUserInfo).orElse(null));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return "index.html";

	}

}
