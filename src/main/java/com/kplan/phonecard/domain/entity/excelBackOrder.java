package com.kplan.phonecard.domain.entity;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;

public class excelBackOrder {
	@ExcelProperty(value="订单号")
	private String ORDER_NO;// AS 订单号,
	@ExcelProperty(value="订单状态")
	private String ORDER_STATUS;// AS 订单状态,
	@ExcelProperty(value="产品名称")
	private String procduct_name;// as 产品名称,
	@ExcelProperty(value="姓名")
	private String USER_NAME;// AS 姓名,
	@ExcelProperty(value=" 联系电话")
	private String PHONE_NUM;// AS 联系电话,
	@ExcelProperty(value="身份证")
	private String USER_ID;// AS 身份证,
	@ExcelProperty(value="处理时间")
	private Date PRO_DATE;// AS 处理时间,
	@ExcelProperty(value="配送地址,")
	private String distribution_addres;// AS 配送地址,
	@ExcelProperty(value="配送省份")
	private String post_province;// AS 配送省份,
	@ExcelProperty(value="配送地市")
	private String post_city;// AS 配送地市,
	@ExcelProperty(value="配送区县")
	private String post_district;// AS 配送区县,
	@ExcelProperty(value="备注")
	private String REMARKS;// AS 备注 ,
	@ExcelProperty(value="恶意标签")
	private String malicious_order;// AS 恶意标签,
	@ExcelProperty(value="反诈标签")
	private String back_info;// AS 反诈标签,
	@ExcelProperty(value="操作记录")
	private String reject_info;// AS 操作记录
	public String getORDER_NO() {
		return ORDER_NO;
	}
	public void setORDER_NO(String oRDER_NO) {
		ORDER_NO = oRDER_NO;
	}
	public String getORDER_STATUS() {
		return ORDER_STATUS;
	}
	public void setORDER_STATUS(String oRDER_STATUS) {
		ORDER_STATUS = oRDER_STATUS;
	}
	public String getProcduct_name() {
		return procduct_name;
	}
	public void setProcduct_name(String procduct_name) {
		this.procduct_name = procduct_name;
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	public String getPHONE_NUM() {
		return PHONE_NUM;
	}
	public void setPHONE_NUM(String pHONE_NUM) {
		PHONE_NUM = pHONE_NUM;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public Date getPRO_DATE() {
		return PRO_DATE;
	}
	public void setPRO_DATE(Date pRO_DATE) {
		PRO_DATE = pRO_DATE;
	}
	public String getDistribution_addres() {
		return distribution_addres;
	}
	public void setDistribution_addres(String distribution_addres) {
		this.distribution_addres = distribution_addres;
	}
	public String getPost_province() {
		return post_province;
	}
	public void setPost_province(String post_province) {
		this.post_province = post_province;
	}
	public String getPost_city() {
		return post_city;
	}
	public void setPost_city(String post_city) {
		this.post_city = post_city;
	}
	public String getPost_district() {
		return post_district;
	}
	public void setPost_district(String post_district) {
		this.post_district = post_district;
	}
	public String getREMARKS() {
		return REMARKS;
	}
	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}
	public String getMalicious_order() {
		return malicious_order;
	}
	public void setMalicious_order(String malicious_order) {
		this.malicious_order = malicious_order;
	}
	public String getBack_info() {
		return back_info;
	}
	public void setBack_info(String back_info) {
		this.back_info = back_info;
	}
	public String getReject_info() {
		return reject_info;
	}
	public void setReject_info(String reject_info) {
		this.reject_info = reject_info;
	}
	
	
}
