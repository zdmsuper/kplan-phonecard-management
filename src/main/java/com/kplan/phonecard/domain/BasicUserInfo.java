package com.kplan.phonecard.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kplan.phonecard.base.BaseDomain;
import com.kplan.phonecard.enums.GenderEnum;

/**
 * 基础信息
 * 
 * @author sam
 *
 */
@Entity
public class BasicUserInfo extends BaseDomain {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE,generator="BasicUserInfo_sequence")
    @SequenceGenerator(sequenceName="BasicUserInfo_sequence", name="BasicUserInfo_sequence",allocationSize = 1)

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 凭证ID
	 */
	private String credentialId;
	/**
	 * 登录名
	 */
	private String loginId;

	/**
	 * 用户真实姓名
	 */
	private String userRealName;
	/**
	 * 用户性别
	 */
	@Convert(converter = GenderEnum.EnumConvert.class)
	private GenderEnum gender;
	/**
	 * 出生日期
	 */
	private Date birthDay;
	/**
	 * 家庭地址
	 */
	private String homeAddress;

	/**
	 * 现住址
	 */
	private String currentAddress;
	/**
	 * 邮箱
	 */

	private String email;
	/**
	 * 电话
	 */
	private String phone;
	
	/**
	 * 身份证
	 */
	private String cardId;
	
	private Integer jurisdiction;


	@Lob
	@Column(length = 20000000)
	private String headImg;

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}



	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public Integer getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(Integer jurisdiction) {
		this.jurisdiction = jurisdiction;
	}
	
}
