package com.kplan.phonecard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.dto.RegistUserInfoDTO;

@Controller
@RequestMapping("/regist")
public class RegistController extends AbstractBaseController {
	private static final Logger logger = LoggerFactory.getLogger(RegistController.class);

	@Autowired
	protected AuthenticationManager authenticationManager;

	@RequestMapping("")
	public String regist() {
		ManagerInfo customerUserDetails = this.getCurrentUserDetails().orElse(null);
		if (customerUserDetails != null) {
			return this.redirect("/index");
		}
		return "regist";
	}

	@RequestMapping("/doRegist")
	public String doRegist(@Validated RegistUserInfoDTO userInfoDTO) {
		ManagerInfo customerUserDetails = this.getCurrentUserDetails().orElse(null);
		if (customerUserDetails != null) {
			return this.redirect("/index");
		}
		String loginId = "";
		String loginPwd = "";
		try {
			// insert user data into db
			return this.autoLogin(userInfoDTO);
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
		}

		// return new ModelAndView(new RedirectView(""));
		return "regist";
	}

	private String autoLogin(RegistUserInfoDTO userInfoDTO) {

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userInfoDTO.getLoginId(),
				userInfoDTO.getLoginPwd());
		token.setDetails(new WebAuthenticationDetails(super.getRequest()));
		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		super.getRequest().getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());
//		return new ModelAndView(new RedirectView("/login/success"));
		return this.redirect("/login/success");
	}
}
