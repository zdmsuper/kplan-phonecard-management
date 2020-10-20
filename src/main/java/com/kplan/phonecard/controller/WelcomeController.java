package com.kplan.phonecard.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kplan.phonecard.domain.BasicUserInfo;
import com.kplan.phonecard.domain.ManagerInfo;

@Controller
@RequestMapping("/welcome.html")
public class WelcomeController extends AbstractBaseController {
	@RequestMapping
	public String welcome(Map<String, Object> map) {
		BasicUserInfo basicUserInfo = this.getCurrentUserDetails().orElse(new ManagerInfo()).getBasicUserInfo();

		map.put("basicUserInfo", basicUserInfo);
		return "welcome.html";

	}

}
