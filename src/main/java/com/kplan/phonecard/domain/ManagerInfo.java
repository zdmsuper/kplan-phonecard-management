package com.kplan.phonecard.domain;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.kplan.phonecard.base.BaseDomain;
import com.kplan.phonecard.enums.UserStatusEnum;

@Entity
public  class ManagerInfo extends BaseDomain {

	public ManagerInfo() {
		this.setBasicUserInfo(new BasicUserInfo());
	}

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE,generator="ManagerInfo_sequence")
    @SequenceGenerator(sequenceName="ManagerInfo_sequence", name="ManagerInfo_sequence",allocationSize = 1)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "user_info_id")
	private BasicUserInfo basicUserInfo;
	/**
	 * 用户状态
	 */
	@Convert(converter = UserStatusEnum.EnumConvert.class)
	private UserStatusEnum userStatus;

	/**
	 * 登录密码
	 */
	private String loginPwd;

	public BasicUserInfo getBasicUserInfo() {
		return basicUserInfo;
	}

	public void setBasicUserInfo(BasicUserInfo basicUserInfo) {
		this.basicUserInfo = basicUserInfo;
	}

	public UserStatusEnum getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatusEnum userStatus) {
		this.userStatus = userStatus;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

}
