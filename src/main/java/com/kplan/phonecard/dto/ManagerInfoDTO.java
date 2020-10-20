package com.kplan.phonecard.dto;

import com.kplan.phonecard.domain.BasicUserInfo;

public class ManagerInfoDTO extends BaseDTO {
	private String base64HeadImg;
	private BasicUserInfo basicUserInfo;
	
	public BasicUserInfo getBasicUserInfo() {
		return basicUserInfo;
	}

	public void setBasicUserInfo(BasicUserInfo basicUserInfo) {
		this.basicUserInfo = basicUserInfo;
	}

	/**
	 * 登录密码
	 */
	private String loginPwd;


	public String getBase64HeadImg() {
		return base64HeadImg;
	}

	public void setBase64HeadImg(String base64HeadImg) {
		this.base64HeadImg = base64HeadImg;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

}
