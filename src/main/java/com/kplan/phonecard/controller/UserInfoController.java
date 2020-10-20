package com.kplan.phonecard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController extends AbstractBaseController{
	@RequestMapping("/list")
	public String list() {
		return "userInfo/list";
	}
}
