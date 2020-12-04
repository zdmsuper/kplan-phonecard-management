package com.kplan.phonecard.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kplan.phonecard.base.BaseDomain;


@Entity
@Table(name = "customer_service_log")
public class CustomerServiceLog extends BaseDomain{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="customer_service_log_id_seq")
	@SequenceGenerator(name="customer_service_log_id_seq",sequenceName="customer_service_log_id_seq",allocationSize=1)
	private Integer id;//	int4	32	0	True	BM?
	@Column(name = "operator", unique = true, length = 32)
	private String operator;//	varchar	0	0	True		记录操作人姓名（登录账号）
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 7)
	private Date create_time;//	timestamp	6	0	True		记录操作时间
	@Column(name = "business_type", unique = true, length = 32)
	private String business_type;//	varchar	0	0	True		例：“宽带”，“低消”，“号卡”，“流量包”，“金融业务”
	@Column(name = "operation_type", unique = true, length = 32)
	private String operation_type;//	varchar	0	0	True		例：”用户回访”，”营销外呼”，“CB受理”，“客服投诉查单”，”红包审核”
	@Column(name = "operation_result", unique = true, length = 32)
	private String operation_result;//	varchar	0	0	True		例：“成功”，“失败”，“转二次回访”
	@Column(name = "order_no", unique = true, length = 32)
	private String order_no;//	varchar	0	0	True		处理订单的订单号
	@Column(name = "product_name", unique = true, length = 32)
	private String product_name;//	varchar	0	0	False		处理订单的产品名称
	@Column(name = "phone", unique = true, length = 32)
	private String phone;//	varchar	0	0	False		处理订单的用户号码
	@Column(name = "province_name", unique = true, length = 32)
	private String province_name;//	varchar	0	0	False		处理订单的省份名称
	@Column(name = "city_name", unique = true, length = 32)
	private String city_name;//	varchar	0	0	False		处理订单的地市面那个车
	@Column(name = "district_name", unique = true, length = 32)
	private String district_name	;//varchar	0	0	False		处理订单的区县名称
	@Column(name = "remarks", unique = true, length = 32)
	private String remarks;//	varchar	0	0	False		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getBusiness_type() {
		return business_type;
	}
	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}
	public String getOperation_type() {
		return operation_type;
	}
	public void setOperation_type(String operation_type) {
		this.operation_type = operation_type;
	}
	public String getOperation_result() {
		return operation_result;
	}
	public void setOperation_result(String operation_result) {
		this.operation_result = operation_result;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getDistrict_name() {
		return district_name;
	}
	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

}
