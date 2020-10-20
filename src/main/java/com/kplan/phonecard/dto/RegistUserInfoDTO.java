package com.kplan.phonecard.dto;

import javax.validation.constraints.NotBlank;

public class RegistUserInfoDTO {
	@NotBlank(message = "用户名不能为空")
	private String loginId;
	@NotBlank(message = "密码不能为空")
	private String loginPwd;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

}
