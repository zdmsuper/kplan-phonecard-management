package com.kplan.phonecard.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;

public class ExterNalDataExcel {
	@ExcelProperty(value = {"订单号"}, index = 0)
	private String orderNo;
	@ExcelProperty(value = {"用户姓名"}, index = 1)
	private String userName;
	@ExcelProperty(value = {"联系电话"}, index =2)
	private String phoneNum;
	@ExcelProperty(value = {"地市编码"}, index = 3)
	private String dirsCode;
	@ExcelProperty(value = {"详细地址"}, index = 4)
	private String address;
	@ExcelProperty(value = {"入网身份证姓名"}, index = 5)
	private String reUserName;
	@ExcelProperty(value = {"入网身份证号码"}, index = 6)
	private String userId;
	@ExcelProperty(value = {"产品编码"}, index = 7)
	private String procDuctCode;
	@ExcelProperty(value = {"省份编码"}, index = 8)
	private String provinceCode;
	@ExcelProperty(value = {"地市编码"}, index = 9)
	private String cityCode;
	@ExcelProperty(value = {"订购号码"}, index = 10)
	private String phone;
	@ExcelProperty(value = {"触点编码"}, index = 11)
	private String contCode;
	@ExcelProperty(value = {"发展人编码"}, index = 12)
	private String personCode;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getDirsCode() {
		return dirsCode;
	}
	public void setDirsCode(String dirsCode) {
		this.dirsCode = dirsCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReUserName() {
		return reUserName;
	}
	public void setReUserName(String reUserName) {
		this.reUserName = reUserName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProcDuctCode() {
		return procDuctCode;
	}
	public void setProcDuctCode(String procDuctCode) {
		this.procDuctCode = procDuctCode;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getContCode() {
		return contCode;
	}
	public void setContCode(String contCode) {
		this.contCode = contCode;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	
	
	
}
