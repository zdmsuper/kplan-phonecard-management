package com.kplan.phonecard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.kplan.phonecard.base.BaseDomain;

@Entity
@Table(name = "unicom_post_city_code")
public class UnicomPostCityCode extends  BaseDomain{
	@Column(name = "province_code", unique = true, length = 32)
	private String province_code;
	@Column(name = "city_code", unique = true, length = 32)
	private String city_code;
	@Id
	@Column(name = "district_code", unique = true, nullable = false, precision = 22, scale = 0)
	private String id;
	@Column(name = "province_name", unique = true, length = 32)
	private String province_name;
	@Column(name = "city_name", unique = true, length = 32)
	private String city_name;
	@Column(name = "district_name", unique = true, length = 32)
	private String district_name;
	@Column(name = "post_province_code", unique = true, length = 32)
	private String post_province_code;
	@Column(name = "post_city_code", unique = true, length = 32)
	private String post_city_code	;
	@Column(name = "city_level", unique = true, length = 32)
	private String city_level;
	
	
	
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
	@Transient
	public String getDistrict_code() {
		return id;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getPost_province_code() {
		return post_province_code;
	}
	public void setPost_province_code(String post_province_code) {
		this.post_province_code = post_province_code;
	}
	public String getPost_city_code() {
		return post_city_code;
	}
	public void setPost_city_code(String post_city_code) {
		this.post_city_code = post_city_code;
	}
	public String getCity_level() {
		return city_level;
	}
	public void setCity_level(String city_level) {
		this.city_level = city_level;
	}
	
	

}
