package com.kplan.phonecard.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.kplan.phonecard.base.BaseDomain;
@Entity
@Table(name = "core_orders_market_k")
public class CoreOrdersMarketk extends  BaseDomain{
	@Id
	@Column(name = "order_no", unique = true, nullable = false, precision = 22, scale = 0 )
	private String id	;
	@Column(name = "order_source", unique = true, length = 32)
	private String order_source	;
	@Column(name = "product_name", unique = true, length = 32)
	private String product_name	;
	@Column(name = "product_code", unique = true, length = 32)
	private String product_code	;
	@Column(name = "province_code", unique = true, length = 32)
	private String province_code;
	@Column(name = "city_code", unique = true, length = 32)
	private String city_code;
	@Column(name = "district_code", unique = true, length = 32)
	private String district_code;
	@Column(name = "receiver_name", unique = true, length = 32)
	private String receiver_name;
	@Column(name = "receiver_phone", unique = true, length = 32)
	private String receiver_phone;
	@Column(name = "receiver_address", unique = true, length = 256)
	private String receiver_address	;
	@Column(name = "access_name", unique = true, length = 32)
	private String access_name	;
	@Column(name = "access_id_number", unique = true, length = 32)
	private String access_id_number;
	@Column(name = "recommend_name", unique = true, length = 32)
	private String recommend_name;
	@Column(name = "recommend_number", unique = true, length = 32)
	private String recommend_number;
	@Column(name = "order_number", unique = true, length = 32)
	private String order_number	;
	@Column(name = "order_status", precision = 22, scale = 0)
	private Integer order_status;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 7)
	private Date createtime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "export_time", length = 7)
	private Date export_time;
	@Column(name = "export_person", unique = true, length = 32)
	private String export_person;
	@Column(name = "remarks", unique = true, length = 32)
	private String remarks;
	@Column(name = "ip", unique = true, length = 32)
	private String ip	;
	@Column(name = "city", unique = true, length = 32)
	private String city	;
	@Column(name = "count", precision = 22, scale = 0)
	private Integer count;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "auth_time", length = 7)
	private Date auth_time;
	@Column(name = "export_status", unique = true,   precision = 32, scale = 0)
	private Integer export_status;
	@Column(name = "province_name", unique = true, length = 32)
	private String province_name;
	@Column(name = "city_name", unique = true, length = 32)
	private String city_name;
	@Column(name = "district_name", unique = true, length = 64)
	private String district_name;
	@Column(name = "visit_code", precision = 22, scale = 0)
	private Integer visit_code;
	@Column(name = "mall_order_no", unique = true, length = 32)
	private String mall_order_no;
	@Column(name = "fail_reasons", unique = true, length = 32)
	private String fail_reasons;
	@Column(name = "external_id", unique = true, length = 64)
	private String external_id	;
	@Column(name = "track_status", precision = 22, scale = 0)
	private Integer track_status;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "track_time", length = 7)
	private Date 	track_time;
	@Column(name = "operator", unique = true, length = 32)
	private String operator;
	@Column(name = "initial_status", precision = 22, scale = 0)
	private Integer initial_status;//	int4	32
	@Column(name = "different_nets", precision = 22, scale = 0)
	private Integer different_nets	;//int4	32
	@Column(name = "business_type", unique = true, length = 32)
	private String business_type;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "external_export_time", length = 7)
	private Date external_export_time;
	@Column(name = "external_export_person", unique = true, length = 32)
	private String external_export_person;//	varchar	32
	@Column(name = "external_company", unique = true, length = 32)
	private String external_company	;//varchar	64
	@Column(name = "place_order_time", unique = true, length = 32)
	private String place_order_time	;//varchar	32
	@Column(name = "token", unique = true, length = 0)
	private String token;
	@Transient
	public String getOrder_no() {
		return id;
	}
	 
	 
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getOrder_source() {
		return order_source;
	}


	public void setOrder_source(String order_source) {
		this.order_source = order_source;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getDistrict_code() {
		return district_code;
	}
	public void setDistrict_code(String district_code) {
		this.district_code = district_code;
	}
	public String getReceiver_name() {
		return receiver_name;
	}
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}
	public String getReceiver_phone() {
		return receiver_phone;
	}
	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
	}
	public String getReceiver_address() {
		return receiver_address;
	}
	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}
	public String getAccess_name() {
		return access_name;
	}
	public void setAccess_name(String access_name) {
		this.access_name = access_name;
	}
	public String getAccess_id_number() {
		return access_id_number;
	}
	public void setAccess_id_number(String access_id_number) {
		this.access_id_number = access_id_number;
	}
	public String getRecommend_name() {
		return recommend_name;
	}
	public void setRecommend_name(String recommend_name) {
		this.recommend_name = recommend_name;
	}
	public String getRecommend_number() {
		return recommend_number;
	}
	public void setRecommend_number(String recommend_number) {
		this.recommend_number = recommend_number;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public Integer getOrder_status() {
		return order_status;
	}
	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}
	
	
	public Date getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public Date getExport_time() {
		return export_time;
	}
	public void setExport_time(Date export_time) {
		this.export_time = export_time;
	}
	public String getExport_person() {
		return export_person;
	}
	public void setExport_person(String export_person) {
		this.export_person = export_person;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Date getAuth_time() {
		return auth_time;
	}
	public void setAuth_time(Date auth_time) {
		this.auth_time = auth_time;
	}
	public Integer getExport_status() {
		return export_status;
	}
	public void setExport_status(Integer export_status) {
		this.export_status = export_status;
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
	public Integer getVisit_code() {
		return visit_code;
	}
	public void setVisit_code(Integer visit_code) {
		this.visit_code = visit_code;
	}
	public String getMall_order_no() {
		return mall_order_no;
	}
	public void setMall_order_no(String mall_order_no) {
		this.mall_order_no = mall_order_no;
	}
	public String getFail_reasons() {
		return fail_reasons;
	}
	public void setFail_reasons(String fail_reasons) {
		this.fail_reasons = fail_reasons;
	}
	public String getExternal_id() {
		return external_id;
	}
	public void setExternal_id(String external_id) {
		this.external_id = external_id;
	}
	public Integer getTrack_status() {
		return track_status;
	}
	public void setTrack_status(Integer track_status) {
		this.track_status = track_status;
	}
	public Date getTrack_time() {
		return track_time;
	}
	public void setTrack_time(Date track_time) {
		this.track_time = track_time;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Integer getInitial_status() {
		return initial_status;
	}
	public void setInitial_status(Integer initial_status) {
		this.initial_status = initial_status;
	}
	public Integer getDifferent_nets() {
		return different_nets;
	}
	public void setDifferent_nets(Integer different_nets) {
		this.different_nets = different_nets;
	}
	public String getBusiness_type() {
		return business_type;
	}
	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}
	public String getExternal_export_person() {
		return external_export_person;
	}
	public void setExternal_export_person(String external_export_person) {
		this.external_export_person = external_export_person;
	}
	public String getExternal_company() {
		return external_company;
	}
	public void setExternal_company(String external_company) {
		this.external_company = external_company;
	}
	public String getPlace_order_time() {
		return place_order_time;
	}
	public void setPlace_order_time(String place_order_time) {
		this.place_order_time = place_order_time;
	}
	public Date getExternal_export_time() {
		return external_export_time;
	}
	public void setExternal_export_time(Date external_export_time) {
		this.external_export_time = external_export_time;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
//	@Override
//	public Long getId() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public void setId(Long id) {
//		// TODO Auto-generated method stub
//		
//	}

	
	
}
