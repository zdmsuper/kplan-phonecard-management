package com.kplan.phonecard.domain.entity;

import javax.persistence.Convert;

import com.kplan.phonecard.enums.KplanSeconDarytracStatusEnum;

public class OrderStatistics {
	private String orderNum;
	private String orderName;
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	
}
