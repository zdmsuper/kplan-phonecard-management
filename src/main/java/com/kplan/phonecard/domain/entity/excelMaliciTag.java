package com.kplan.phonecard.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;

public class excelMaliciTag {
	@ExcelProperty(value="联通订单号")
	private String unicomNo;
	@ExcelProperty(value="恶意标签")
	private String maliciTag;
	@ExcelProperty(value="省份")
	private String privicn;
	public String getUnicomNo() {
		return unicomNo;
	}
	public void setUnicomNo(String unicomNo) {
		this.unicomNo = unicomNo;
	}
	public String getMaliciTag() {
		return maliciTag;
	}
	public void setMaliciTag(String maliciTag) {
		this.maliciTag = maliciTag;
	}
	public String getPrivicn() {
		return privicn;
	}
	public void setPrivicn(String privicn) {
		this.privicn = privicn;
	}
	
	
	
}
