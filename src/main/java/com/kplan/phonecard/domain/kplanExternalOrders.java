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
@Table(name = "kplan_external_orders")
public class kplanExternalOrders extends BaseDomain{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="kplan_external_orders_sequence")
	@SequenceGenerator(name="kplan_external_orders_sequence",sequenceName="kplan_external_orders_sequence",allocationSize=1)
	private Integer id;//	int8	64	0	True	BM?
	@Column(name = "orderno", unique = true, length = 16)
	private String orderNo;//	varchar	16	0	False		订单号
	@Column(name = "unicomno", unique = true, length = 16)
	private String unicomNo;//	varchar	16	0	False		联通订单号
	@Column(name = "username", unique = true, length = 16)
	private String userName;//	varchar	16	0	False		用户姓名
	@Column(name = "phonenum", unique = true, length = 16)
	private String phoneNum;//	varchar	16	0	False		联系电话
	@Column(name = "provincecode", unique = true, length = 16)
	private String provinceCode;//	varchar	16	0	False		订单省份代码
	@Column(name = "provincename", unique = true, length = 16)
	private String provinceName;//	varchar	16	0	False		订单省份名称
	@Column(name = "citycode", unique = true, length = 16)
	private String cityCode;//	varchar	16	0	False		订单地市代码
	@Column(name = "cityname", unique = true, length = 16)
	private String cityName;//	varchar	16	0	False		订单地市名称
	@Column(name = "districtcode", unique = true, length = 16)
	private String districtCode;//	varchar	16	0	False		订单区县代码
	@Column(name = "districtname", unique = true, length = 16)
	private String districtName	;//varchar	16	0	False		订单区县名称
	@Column(name = "address", unique = true, length = 1000)
	private String address;//	varchar	1000	0	False		订单详细地址
	@Column(name = "userid", unique = true, length = 24)
	private String userId;//	varchar	24	0	False		身份证号码
	@Column(name = "procductcode", unique = true, length = 32)
	private String procductCode;//	varchar	32	0	False		产品编码
	@Column(name = "phoneprovincecode", unique = true, length = 16)
	private String phoneprovincecode;//	varchar	16	0	False		订购号码省份代码
	@Column(name = "phonecitycode", unique = true, length = 16)
	private String phoneCityCode;//	varchar	16	0	False		订购电话地市代码
	@Column(name = "personcode", unique = true, length = 16)
	private String personCode	;//varchar	16	0	False		发展人编码
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creaddate", length = 7)
	private Date creadDate;//	timestamp	6	0	False		创建时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastdate", length = 7)
	private Date lastDate;//	timestamp	6	0	False		最后操作时间
	@Column(name = "remarks", unique = true, length = 640)
	private String remarks;//	varchar	640	0	False		备注
	@Column(name = "operator", unique = true, length = 32)
	private String operator;//	varchar	32	0	False		操作人
	@Column(name = "company", unique = true, length = 64)
	private String comPany;//	varchar	64	0	False		所属分公司
	@Column(name = "orderstatus", precision = 22, scale = 0)
	private Integer orderStatus;//	int2	16	0	False		订单状态
	@Column(name = "phone", unique = true, length = 16)
	private String phone;//	varchar	16	0	False		订购号码
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getUnicomNo() {
		return unicomNo;
	}
	public void setUnicomNo(String unicomNo) {
		this.unicomNo = unicomNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProcductCode() {
		return procductCode;
	}
	public void setProcductCode(String procductCode) {
		this.procductCode = procductCode;
	}
	
	public String getPhoneprovincecode() {
		return phoneprovincecode;
	}
	public void setPhoneprovincecode(String phoneprovincecode) {
		this.phoneprovincecode = phoneprovincecode;
	}
	public String getPhoneCityCode() {
		return phoneCityCode;
	}
	public void setPhoneCityCode(String phoneCityCode) {
		this.phoneCityCode = phoneCityCode;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public Date getCreadDate() {
		return creadDate;
	}
	public void setCreadDate(Date creadDate) {
		this.creadDate = creadDate;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getComPany() {
		return comPany;
	}
	public void setComPany(String comPany) {
		this.comPany = comPany;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
