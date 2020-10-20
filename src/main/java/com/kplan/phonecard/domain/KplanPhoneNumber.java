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
@Table(name = "kplan_phone_number")
public class KplanPhoneNumber extends  BaseDomain{
	@Id
	@Column(name = "phone", unique = true, nullable = false, precision = 22, scale = 0)
	private String id;
	@Column(name = "province_name", unique = true, length = 32)
	private String province_name;//	varchar	32	0	False		省份名称
	@Column(name = "province_code", unique = true, length = 8)
	private String province_code;//	varchar	8	0	False		省份编码
	@Column(name = "city_name", unique = true, length = 32)
	private String city_name;//	varchar	32	0	False		地市名称
	@Column(name = "city_code", unique = true, length = 8)
	private String city_code;//	varchar	8	0	False		地市编码
	@Column(name = "district_code", unique = true, length = 16)
	private String district_code;//	varchar	16	0	False		区县代码
	@Column(name = "district_name", unique = true, length = 32)
	private String district_name;//	varchar	32	0	False		区县名称
	@Column(name = "phone_num", unique = true, length = 16)
	private String phone_num;//	varchar	16	0	False		使用的号码
	@Column(name = "use_not", precision = 22, scale = 0)
	private Integer use_not;//	int4;	32	0	False		是否使用0 未使用 1 已使用
	@Column(name = "is_effective", precision = 22, scale = 0)
	private Integer is_effective;//	int4	32	0	False		是否有效0 有效 1无效
	@Column(name = "contain_four", precision = 22, scale = 0)
	private Integer contain_four;//	int4	32	0	False		是否包含数字4
	@Column(name = "contain_eight", precision = 22, scale = 0)
	private Integer contain_eight;//	int4	32	0	False		是否包含数字8
	@Column(name = "beautiful_num", unique = true, length = 16)
	private String beautiful_num;//	varchar	16	0	False		包含的数字靓号
	@Column(name = "rule_name", unique = true, length = 16)
	private String rule_name;//	varchar	8	0	False		规则名称
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cread_date", length = 7)
	private Date cread_date;//	timestamp	6	0	False		创建时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_date", length = 7)
	private Date last_date;//	timestamp	6	0	False		最后更新时间
	@Column(name = "priority_name", precision = 22, scale = 0)
	private Integer priority_name;//	int4	32	0	False		优先级
	@Column(name = "phone_source", unique = true, length = 16)
	private String phone_source;//	varchar	32	0	False		靓号来源
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "occupy_date", length = 7)
	private Date occupy_date;//占用时间
	@Column(name = "render_starindex", precision = 22, scale = 0)
	private Integer render_starindex;//	int4	32	0	False		渲染开始位置
	@Column(name = "render_num", precision = 22, scale = 0)
	private Integer render_num;//	int4	32	0	False		渲染多少位
	@Transient
	public String getPhone() {
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
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
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
	public String getDistrict_name() {
		return district_name;
	}
	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public Integer getUse_not() {
		return use_not;
	}
	public void setUse_not(Integer use_not) {
		this.use_not = use_not;
	}
	public Integer getIs_effective() {
		return is_effective;
	}
	public void setIs_effective(Integer is_effective) {
		this.is_effective = is_effective;
	}
	public Integer getContain_four() {
		return contain_four;
	}
	public void setContain_four(Integer contain_four) {
		this.contain_four = contain_four;
	}
	public Integer getContain_eight() {
		return contain_eight;
	}
	public void setContain_eight(Integer contain_eight) {
		this.contain_eight = contain_eight;
	}
	public String getBeautiful_num() {
		return beautiful_num;
	}
	public void setBeautiful_num(String beautiful_num) {
		this.beautiful_num = beautiful_num;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	public Date getCread_date() {
		return cread_date;
	}
	public void setCread_date(Date cread_date) {
		this.cread_date = cread_date;
	}
	public Date getLast_date() {
		return last_date;
	}
	public void setLast_date(Date last_date) {
		this.last_date = last_date;
	}
	public Integer getPriority_name() {
		return priority_name;
	}
	public void setPriority_name(Integer priority_name) {
		this.priority_name = priority_name;
	}
	public String getPhone_source() {
		return phone_source;
	}
	public void setPhone_source(String phone_source) {
		this.phone_source = phone_source;
	}
	public Date getOccupy_date() {
		return occupy_date;
	}
	public void setOccupy_date(Date occupy_date) {
		this.occupy_date = occupy_date;
	}
	public Integer getRender_starindex() {
		return render_starindex;
	}
	public void setRender_starindex(Integer render_starindex) {
		this.render_starindex = render_starindex;
	}
	public Integer getRender_num() {
		return render_num;
	}
	public void setRender_num(Integer render_num) {
		this.render_num = render_num;
	}
	
	

}
