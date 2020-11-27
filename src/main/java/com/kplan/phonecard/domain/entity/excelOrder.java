package com.kplan.phonecard.domain.entity;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;

public class excelOrder {
@ExcelProperty(value="订单号")
private String orderNo;
@ExcelProperty(value="身份证")
private String userId;
@ExcelProperty(value="联系电话")
private String mobile;
@ExcelProperty(value="用户姓名")
private String userName;
@ExcelProperty(value="订购号码")
private String phone;
@ExcelProperty(value="省份")
private String provicnName;
@ExcelProperty(value="地市")
private String cityName;
@ExcelProperty(value="区县")
private String dirName;
@ExcelProperty(value="详细地址")
private String address;
@ExcelProperty(value="产品名称")
private String procductName;
@ExcelProperty(value="处理工号")
private String proChannel;
@ExcelProperty(value="订单来源")
private String orderSurce;
@ExcelProperty(value="订单状态")
private String orderStatus;
@ExcelProperty(value="跟单时间")
private Date tracTime;
@ExcelProperty(value="订单时间")
private Date creaTime;
@ExcelProperty(value="恶意标签")
private String maliciousTag;
@ExcelProperty(value="操作类型")
private String operatorType;
@ExcelProperty(value="操作人")
private String operator;
@ExcelProperty(value="备注")
private String remarks;
@ExcelProperty(value="说明")
private String respon;
public String getUserId() {
	return userId;
}
public String getOrderNo() {
	return orderNo;
}
public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
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
public String getProvicnName() {
	return provicnName;
}
public void setProvicnName(String provicnName) {
	this.provicnName = provicnName;
}
public String getCityName() {
	return cityName;
}
public void setCityName(String cityName) {
	this.cityName = cityName;
}
public String getDirName() {
	return dirName;
}
public void setDirName(String dirName) {
	this.dirName = dirName;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getProcductName() {
	return procductName;
}
public void setProcductName(String procductName) {
	this.procductName = procductName;
}
public String getProChannel() {
	return proChannel;
}
public void setProChannel(String proChannel) {
	this.proChannel = proChannel;
}
public String getOrderSurce() {
	return orderSurce;
}
public void setOrderSurce(String orderSurce) {
	this.orderSurce = orderSurce;
}
public String getOrderStatus() {
	return orderStatus;
}
public void setOrderStatus(String orderStatus) {
	this.orderStatus = orderStatus;
}
public Date getTracTime() {
	return tracTime;
}
public void setTracTime(Date tracTime) {
	this.tracTime = tracTime;
}
public String getMaliciousTag() {
	return maliciousTag;
}
public void setMaliciousTag(String maliciousTag) {
	this.maliciousTag = maliciousTag;
}
public String getOperatorType() {
	return operatorType;
}
public void setOperatorType(String operatorType) {
	this.operatorType = operatorType;
}
public String getOperator() {
	return operator;
}
public void setOperator(String operator) {
	this.operator = operator;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
public String getRespon() {
	return respon;
}
public void setRespon(String respon) {
	this.respon = respon;
}
public Date getCreaTime() {
	return creaTime;
}
public void setCreaTime(Date creaTime) {
	this.creaTime = creaTime;
}

}
