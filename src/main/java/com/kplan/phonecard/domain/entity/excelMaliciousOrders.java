package com.kplan.phonecard.domain.entity;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;

public class excelMaliciousOrders {
	@ExcelProperty(value="用户姓名")
	private String userName;
	@ExcelProperty(value="联系电话")
	private String phone;
	@ExcelProperty(value="身份证")
	private String userId;
	@ExcelProperty(value="省份")
	private String province;
	@ExcelProperty(value="地市")
	private String city;
	@ExcelProperty(value="区县")
	private String dir;
	@ExcelProperty(value="产品名称")
	private String proDuctName;
	@ExcelProperty(value="恶意标签")
	private String maLiciousTag;
	@ExcelProperty(value="最后操作人")
	private String operatorUser;
	@ExcelProperty(value="备注")
	private String remarks;
	@ExcelProperty(value="创建时间")
	private Date creaDate;
	@ExcelProperty(value="处理时间")
	private Date proDate;
	@ExcelProperty(value="订单状态")
	private String orderStatus;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getProDuctName() {
		return proDuctName;
	}
	public void setProDuctName(String proDuctName) {
		this.proDuctName = proDuctName;
	}
	public String getMaLiciousTag() {
		return maLiciousTag;
	}
	public void setMaLiciousTag(String maLiciousTag) {
		this.maLiciousTag = maLiciousTag;
	}
	public String getOperatorUser() {
		return operatorUser;
	}
	public void setOperatorUser(String operatorUser) {
		this.operatorUser = operatorUser;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getCreaDate() {
		return creaDate;
	}
	public void setCreaDate(Date creaDate) {
		this.creaDate = creaDate;
	}
	public Date getProDate() {
		return proDate;
	}
	public void setProDate(Date proDate) {
		this.proDate = proDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
