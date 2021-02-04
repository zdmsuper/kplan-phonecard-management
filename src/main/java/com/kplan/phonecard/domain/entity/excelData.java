package com.kplan.phonecard.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;

public class excelData {
	@ExcelProperty(value="订单号")
	private String orderNo;
	@ExcelProperty(value="联系人姓名")
	private String userName;
	@ExcelProperty(value="物流联系人姓名")
	private String logisticsUserName;
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
	public String getLogisticsUserName() {
		return logisticsUserName;
	}
	public void setLogisticsUserName(String logisticsUserName) {
		this.logisticsUserName = logisticsUserName;
	}
	
}
