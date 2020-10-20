package com.kplan.phonecard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kplan.phonecard.vo.ResultMessageDTO;

@Controller
@RequestMapping("/test")
public class TestController extends AbstractBaseController {
	@RequestMapping("/list")
	public String list() {

		return "test/list";
	}

	@RequestMapping("/addshopowner")
	public String addshopowner() {

		return "test/addshopowner";
	}

	
	@RequestMapping("/addmember")
	public String addmember() {

		return "test/addmember";
	}

	@RequestMapping("/saveOrUpdateMember")
	@ResponseBody
	public ResultMessageDTO<Object> saveOrUpdateMember() {

		return ResultMessageDTO.success();

	}
}
