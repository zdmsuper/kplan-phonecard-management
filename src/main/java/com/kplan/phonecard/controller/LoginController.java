package com.kplan.phonecard.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.vo.ResultMessageDTO;

@Controller
@RequestMapping("/login")
public class LoginController extends AbstractBaseController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping("")
	public String login() {
		//this.getCurrentUserDetails().map(ManagerInfo::getBasicUserInfo)
		ManagerInfo customerUserDetails = this.getCurrentUserDetails().orElse(null);
		if (customerUserDetails != null) {
			return this.redirect("/index");
		}

		// logger.info("=====");
		return "login";
	}

	@RequestMapping("/success")
	public String success() {
		// 登录成功后进入这个请求
		return this.redirect("/index");
	}

	@RequestMapping("/fail")
	public String fail(Map<String, Object> model) {
		// 登录失败后进入这个请求
		AuthenticationException e = (AuthenticationException) super.getRequest()
				.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		// logger.error(e.getMessage(), e);
		if (e instanceof BadCredentialsException) {
			model.put("error", "用户名或密码错误");
		} else {
			model.put("error", e.getMessage());
		}

		return "login";
	}

	@RequestMapping("/doLogout")
	public String doLogout() {
		// 成功退出后进入这个请求
		return null;
	}

	@RequestMapping("/changePwd")
	public String changePwd() {
		// 成功退出后进入这个请求
		return "login/changePwd";
	}

	@RequestMapping("/doChangePwd")
	@ResponseBody
	public ResultMessageDTO<Object> doChangePwd(@RequestParam("oldPwd") String oldPwd,
			@RequestParam("newPwd") String newPwd, @RequestParam("rePwd") String rePwd) {
		if (StringUtils.isBlank(oldPwd)) {
			return ResultMessageDTO.failed("原密码不能为空");
		}
		if (StringUtils.isBlank(newPwd)) {
			return ResultMessageDTO.failed("新密码不能为空");
		}
		if (StringUtils.isBlank(rePwd)) {
			return ResultMessageDTO.failed("重复新密码不能为空");
		}
		if (!newPwd.equals(rePwd)) {
			return ResultMessageDTO.failed("新密码与重复密码不相同");
		}
		if (oldPwd.equals(newPwd)) {
			return ResultMessageDTO.failed("原密码不能与新密码相同");
		}
		ManagerInfo item = this.managerInfoService.findById(this.getCurrentUserDetails().orElse(new ManagerInfo()).getId())
				.orElse(new ManagerInfo());
//		if (!this.passwordEncoder.encode(oldPwd).equals(item.getLoginPwd())) {
//			return ResultMessageDTO.failed("原密码错误");
//		}
		item.setLoginPwd(this.passwordEncoder.encode(newPwd));
		this.managerInfoService.update(item);

		return ResultMessageDTO.success();
	}

	@RequestMapping("/detail")
	public String detail(Map<String, Object> map) {
		ManagerInfo item = this.managerInfoService.findById(this.getCurrentUserDetails().orElse(new ManagerInfo()).getId())
				.orElse(new ManagerInfo());
		map.put("item", item);
		return "login/detail";
	}

	@RequestMapping("/invalidSession")
	public String invalidSession() {
		// 成功退出后进入这个请求
		return "login";
	}

	@RequestMapping("/logout")
	public String logout() {
		// 成功退出后进入这个请求
		return "index";
	}

}
