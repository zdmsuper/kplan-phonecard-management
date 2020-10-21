package com.kplan.phonecard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kplan.phonecard.base.BaseDomain;
@Entity
@Table(name = "kplan_channel_number")
public class KplanChannelNumber extends BaseDomain{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="kplan_channel_number_id_seq")
	@SequenceGenerator(name="kplan_channel_number_id_seq",sequenceName="kplan_channel_number_id_seq",allocationSize=1)
	private Integer id;//	int4	32
	@Column(name = "agent_name", unique = true, length = 32)
	private String agent_name;//	varchar	32
	@Column(name = "agent_code", unique = true, length = 32)
	private String agent_code;//	varchar	32
	@Column(name = "channel_code", unique = true, length = 32)
	private String channel_code	;//varchar	32
	@Column(name = "kplan_contact_name", unique = true, length = 32)
	private String kplan_contact_name;//	varchar	32
	@Column(name = "kplan_contact_code", unique = true, length = 32)
	private String kplan_contact_code;//	varchar	32
	@Column(name = "contact_level", precision = 22, scale = 0)
	private Integer contact_level;//int4	32
	@Column(name = "order_source", unique = true, length = 32)
	private String order_source;//	varchar	64
	@Column(name = "kplan_contact_key", unique = true, length = 255)
	private String kplan_contact_key;
	@Column(name = "open_status", precision = 22, scale = 0)
	private Integer open_status;
	@Column(name = "delivery_order", precision = 22, scale = 0)
	private Integer delivery_order;//	int4	32	0	False		送单量 -1：不限量
	@Column(name = "delivery_num", precision = 22, scale = 0)
	private Integer delivery_num;//	int4	32	0	False		已送单数量，每天24点自动重置
	@Column(name = "province_code_pool", unique = true, length = 1000)
	private String  province_code_pool;//	varchar	1000	0	False		省份配置池 省份编码以英文，分割
	@Column(name = "procduct_code_pool", unique = true, length = 1000)
	private String  procduct_code_pool;//	varchar	1000	0	False		产品配置池，产品之间以英文，分割
	@Column(name = "priority_level", precision = 22, scale = 0)
	private Integer priority_level;//	int2	16	0	False		优先级
	@Column(name = "priority_type", unique = true, length = 16)
	private String priority_type;//	varchar	16	0	False		策略类型 省份：province 地市：city
	@Column(name = "default_tag", precision = 22, scale = 0)
	private Integer default_tag;//	int2	16	0	False		默认工号 1：默认 0：非默认


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAgent_name() {
		return agent_name;
	}
	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	public String getAgent_code() {
		return agent_code;
	}
	public void setAgent_code(String agent_code) {
		this.agent_code = agent_code;
	}
	public String getChannel_code() {
		return channel_code;
	}
	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}
	public String getKplan_contact_name() {
		return kplan_contact_name;
	}
	public void setKplan_contact_name(String kplan_contact_name) {
		this.kplan_contact_name = kplan_contact_name;
	}
	public String getKplan_contact_code() {
		return kplan_contact_code;
	}
	public void setKplan_contact_code(String kplan_contact_code) {
		this.kplan_contact_code = kplan_contact_code;
	}
	public Integer getContact_level() {
		return contact_level;
	}
	public void setContact_level(Integer contact_level) {
		this.contact_level = contact_level;
	}
	public String getOrder_source() {
		return order_source;
	}
	public void setOrder_source(String order_source) {
		this.order_source = order_source;
	}
	public String getKplan_contact_key() {
		return kplan_contact_key;
	}
	public void setKplan_contact_key(String kplan_contact_key) {
		this.kplan_contact_key = kplan_contact_key;
	}
	public Integer getOpen_status() {
		return open_status;
	}
	public void setOpen_status(Integer open_status) {
		this.open_status = open_status;
	}
	public Integer getDelivery_order() {
		return delivery_order;
	}
	public void setDelivery_order(Integer delivery_order) {
		this.delivery_order = delivery_order;
	}
	public Integer getDelivery_num() {
		return delivery_num;
	}
	public void setDelivery_num(Integer delivery_num) {
		this.delivery_num = delivery_num;
	}
	public String getProvince_code_pool() {
		return province_code_pool;
	}
	public void setProvince_code_pool(String province_code_pool) {
		this.province_code_pool = province_code_pool;
	}
	public String getProcduct_code_pool() {
		return procduct_code_pool;
	}
	public void setProcduct_code_pool(String procduct_code_pool) {
		this.procduct_code_pool = procduct_code_pool;
	}
	public Integer getPriority_level() {
		return priority_level;
	}
	public void setPriority_level(Integer priority_level) {
		this.priority_level = priority_level;
	}
	public String getPriority_type() {
		return priority_type;
	}
	public void setPriority_type(String priority_type) {
		this.priority_type = priority_type;
	}
	public Integer getDefault_tag() {
		return default_tag;
	}
	public void setDefault_tag(Integer default_tag) {
		this.default_tag = default_tag;
	}
	@Override
	public String toString() {
		return "kplan_channel_number [id=" + id + ", agent_name=" + agent_name + ", agent_code=" + agent_code
				+ ", channel_code=" + channel_code + ", kplan_contact_name=" + kplan_contact_name
				+ ", kplan_contact_code=" + kplan_contact_code + ", contact_level=" + contact_level + ", order_source="
				+ order_source + ", kplan_contact_key=" + kplan_contact_key + ", open_status=" + open_status
				+ ", delivery_order=" + delivery_order + ", delivery_num=" + delivery_num + ", province_code_pool="
				+ province_code_pool + ", procduct_code_pool=" + procduct_code_pool + ", priority_level="
				+ priority_level + ", priority_type=" + priority_type + ", default_tag=" + default_tag + "]";
	}
	
	
	
	

}
