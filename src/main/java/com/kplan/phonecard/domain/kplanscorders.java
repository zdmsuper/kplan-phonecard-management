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
@Table(name = "kplan_sc_orders")
public class kplanscorders extends BaseDomain{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="kplan_sc_orders_id_seq")
	@SequenceGenerator(name="kplan_sc_orders_id_seq",sequenceName="kplan_sc_orders_id_seq",allocationSize=1)
	private Integer id;
	@Column(name = "order_no", unique = true, length = 64)
	private String order_no;//	varchar	64	0	False		订单号
	@Column(name = "product_name", unique = true, length = 16)
	private String product_name;//	varchar	16	0	False		产品名称
	@Column(name = "phone", unique = true, length = 16)
	private String phone;//	varchar	16	0	False		订购号码
//	@Column(name = "place_order_duration", unique = true, length = 16)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "place_order_duration", length = 7)
	private Date place_order_duration;//	varchar	16	0	False		下单时长
	@Column(name = "contacts_name", unique = true, length = 32)
	private String contacts_name;//	varchar	32	0	False		联系人
	@Column(name = "phone_num", unique = true, length = 16)
	private String phone_num;//	varchar	16	0	False		联系电话
	@Column(name = "province", unique = true, length = 16)
	private String province;//	varchar	16	0	False		省份
	@Column(name = "city", unique = true, length = 32)
	private String city;//	varchar	32	0	False		地市
	@Column(name = "district", unique = true, length = 32)
	private String district;//	varchar	32	0	False		区县
	@Column(name = "distribution_addres", unique = true, length = 128)
	private String 	distribution_addres;//	varchar	128	0	False		收货地址
	@Column(name = "distribution_type", unique = true, length = 32)
	private String distribution_type;//	varchar	32	0	False		配送方式
	@Column(name = "phone_qcellcore", unique = true, length = 16)
	private String phone_qcellcore;//	varchar	16	0	False		订购号码归属地
	@Column(name = "channel_name", unique = true, length = 32)
	private String channel_name;//	varchar	32	0	False		渠道名称
	@Column(name = "user_id", unique = true, length = 24)
	private String user_id;//	varchar	24	0	False		身份证
	@Column(name = "head_sign", unique = true, length = 128)
	private String head_sign;//	varchar	128	0	False		总部标记
	@Column(name = "platform_remarks", unique = true, length = 255)
	private String platform_remarks;//	varchar	255	0	False		中台备注
	@Column(name = "remarks_tag", unique = true, length = 255)
	private String remarks_tag;//	varchar	255	0	False		备注标签  用于填写订单中心备注
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cread_date", length = 7)
	private Date cread_date;//	timestamp	6	0	False		创建时间
	@Column(name = "order_status", precision = 32, scale = 0)
	private Integer order_status;//	int4	32	0	False		订单状态，入库状态默认0     3:订单处理中（信息爬取） 1：信息爬取完成
	@Column(name = "newest_status", precision = 32, scale = 0)
	private Integer newest_status;//	int4	32	0	False		最新状态
	@Column(name = "documentary", unique = true, length = 255)
	private String documentary;//	varchar	16	0	False		跟单人
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 7)
	private Date update_date;//	timestamp	6	0	False		更新时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lock_date", length = 7)
	private Date lock_date;//	timestamp	6	0	False		锁定时间
	@Column(name = "website_status", precision = 32, scale = 0)
	private Integer website_status;//	int2	16	0	False		本网异网状态 1：本网 2：异网
	@Column(name = "examine_status", precision = 32, scale = 0)
	private Integer examine_status;//	int2	16	0	False		审核状态
	@Column(name = "failed_tag", precision = 32, scale = 0)
	private Integer failed_tag;//	int2	16	0	False		失败标签
	@Column(name = "repeat_tag", precision = 32, scale = 0)
	private Integer repeat_tag;//	int2	16	0	False		重复标签
	@Column(name = "risk_addres", unique = true, length = 255)
	private String risk_addres;//	varchar	255	0	False		风险地址
	@Column(name = "risk_addres_tag", unique = true, length = 255)
	private String risk_addres_tag;//	varchar	128	0	False		风险地址标签

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	public Date getPlace_order_duration() {
		return place_order_duration;
	}
	public void setPlace_order_duration(Date place_order_duration) {
		this.place_order_duration = place_order_duration;
	}
	public String getContacts_name() {
		return contacts_name;
	}
	public void setContacts_name(String contacts_name) {
		this.contacts_name = contacts_name;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getDistribution_addres() {
		return distribution_addres;
	}
	public void setDistribution_addres(String distribution_addres) {
		this.distribution_addres = distribution_addres;
	}
	public String getDistribution_type() {
		return distribution_type;
	}
	public void setDistribution_type(String distribution_type) {
		this.distribution_type = distribution_type;
	}
	public String getPhone_qcellcore() {
		return phone_qcellcore;
	}
	public void setPhone_qcellcore(String phone_qcellcore) {
		this.phone_qcellcore = phone_qcellcore;
	}
	public String getChannel_name() {
		return channel_name;
	}
	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getHead_sign() {
		return head_sign;
	}
	public void setHead_sign(String head_sign) {
		this.head_sign = head_sign;
	}
	public String getPlatform_remarks() {
		return platform_remarks;
	}
	public void setPlatform_remarks(String platform_remarks) {
		this.platform_remarks = platform_remarks;
	}
	public String getRemarks_tag() {
		return remarks_tag;
	}
	public void setRemarks_tag(String remarks_tag) {
		this.remarks_tag = remarks_tag;
	}
	public Date getCread_date() {
		return cread_date;
	}
	public void setCread_date(Date cread_date) {
		this.cread_date = cread_date;
	}
	public Integer getOrder_status() {
		return order_status;
	}
	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}
	public Integer getNewest_status() {
		return newest_status;
	}
	public void setNewest_status(Integer newest_status) {
		this.newest_status = newest_status;
	}
	public String getDocumentary() {
		return documentary;
	}
	public void setDocumentary(String documentary) {
		this.documentary = documentary;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public Date getLock_date() {
		return lock_date;
	}
	public void setLock_date(Date lock_date) {
		this.lock_date = lock_date;
	}
	public Integer getWebsite_status() {
		return website_status;
	}
	public void setWebsite_status(Integer website_status) {
		this.website_status = website_status;
	}
	public Integer getExamine_status() {
		return examine_status;
	}
	public void setExamine_status(Integer examine_status) {
		this.examine_status = examine_status;
	}
	public Integer getFailed_tag() {
		return failed_tag;
	}
	public void setFailed_tag(Integer failed_tag) {
		this.failed_tag = failed_tag;
	}
	public Integer getRepeat_tag() {
		return repeat_tag;
	}
	public void setRepeat_tag(Integer repeat_tag) {
		this.repeat_tag = repeat_tag;
	}
	public String getRisk_addres() {
		return risk_addres;
	}
	public void setRisk_addres(String risk_addres) {
		this.risk_addres = risk_addres;
	}
	public String getRisk_addres_tag() {
		return risk_addres_tag;
	}
	public void setRisk_addres_tag(String risk_addres_tag) {
		this.risk_addres_tag = risk_addres_tag;
	}
	@Override
	public String toString() {
		return "kplan_sc_orders [id=" + id + ", order_no=" + order_no + ", product_name=" + product_name + ", phone="
				+ phone + ", place_order_duration=" + place_order_duration + ", contacts_name=" + contacts_name
				+ ", phone_num=" + phone_num + ", province=" + province + ", city=" + city + ", district=" + district
				+ ", distribution_addres=" + distribution_addres + ", distribution_type=" + distribution_type
				+ ", phone_qcellcore=" + phone_qcellcore + ", channel_name=" + channel_name + ", user_id=" + user_id
				+ ", head_sign=" + head_sign + ", platform_remarks=" + platform_remarks + ", remarks_tag=" + remarks_tag
				+ ", cread_date=" + cread_date + ", order_status=" + order_status + ", newest_status=" + newest_status
				+ ", documentary=" + documentary + ", update_date=" + update_date + ", lock_date=" + lock_date
				+ ", website_status=" + website_status + ", examine_status=" + examine_status + ", failed_tag="
				+ failed_tag + ", repeat_tag=" + repeat_tag + "]";
	}
	
}
