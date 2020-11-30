package com.kplan.phonecard.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kplan.phonecard.base.BaseDomain;
import com.kplan.phonecard.enums.KplanSeconDarytracStatusEnum;
import com.kplan.phonecard.enums.OrderStatusEnum;
import com.kplan.phonecard.enums.ProStatusEnum;
@Entity
@Table(name = "kplan_secondary_orders")
public class KplanSecondaryOrders extends BaseDomain{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="kplan_secondary_orders_id_seq")
	@SequenceGenerator(name="kplan_secondary_orders_id_seq",sequenceName="kplan_secondary_orders_id_seq",allocationSize=1)
	private Integer id;//	int8	64	0	True	BM?
	@Column(name = "order_no", unique = true, length = 64)
	private String order_no	;///;varchar;//	32	0	False		订单号
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "place_order_time", length = 7)
	private Date place_order_time;//	timestamp;//	6	0	False		下单时间
	@Column(name = "procduct_name", unique = true, length = 64)
	private String procduct_name;//	varchar	18	0	False		产品名称
	@Column(name = "phone", unique = true, length = 64)
	private String phone;///	varchar	16	0	False		订购号码
	@Column(name = "order_status", unique = true, length = 64)
	private String order_status	;//varchar	16	0	False		订单状态
	@Column(name = "malicious_order", unique = true, length = 64)
	private String malicious_order;//	varchar	32	0	False		恶意订单状态
	@Column(name = "failed_reason", unique = true, length = 64)
	private String failed_reason;//	varchar	32	0	False		失败原因
	@Column(name = "distribution_type", unique = true, length = 64)
	private String distribution_type;//	varchar	64	0	False		配送方式
	@Column(name = "user_name", unique = true, length = 24)
	private String user_name;//	varchar	8	0	False		用户姓名
	@Column(name = "phone_num", unique = true, length = 16)
	private String phone_num;//	varchar	16	0	False		联系电话
	@Column(name = "user_id", unique = true, length = 64)
	private String user_id	;//varchar	24	0	False		身份证
	@Convert(converter = ProStatusEnum.EnumConvert.class)
	@Column(name = "pro_status", precision = 22, scale = 0)
	private ProStatusEnum pro_status;//	int2	16	0	False		处理状态1已处理，2未处理
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "pro_date", length = 7)
	private Date pro_date;//	timestamp	6	0	False		处理时间
	@Column(name = "order_source", unique = true, length = 64)
	private String order_source;
	@Column(name = "distribution_addres", unique = true, length = 64)
	private String distribution_addres;
	@Column(name = "order_id", unique = true, length = 32)
	private String order_id;//	varchar	32	0	False		订单ID
	@Column(name = "phone_province", unique = true, length = 16)
	private String phone_province;//	varchar	16	0	False		号码省份
	@Column(name = "phone_city", unique = true, length = 16)
	private String phone_city;//	varchar	16	0	False		号码地市
	@Column(name = "post_province", unique = true, length = 16)
	private String post_province;//	varchar	16	0	False		交付省份
	@Column(name = "post_city", unique = true, length = 16)
	private String post_city;//	varchar	16	0	False		交付地市
	@Column(name = "post_district", unique = true, length = 32)
	private String post_district;//	varchar	32	0	False		交付区县
	@Column(name = "age", unique = true, length = 8)
	private String age;//	varchar	8	0	False		年龄
//	@Column(name = "product_name", unique = true, length = 32)
//	private String product_name;//	varchar	32	0	False		商品名称
	@Column(name = "contact_code", unique = true, length = 16)
	private String contact_code;//	varchar	16	0	False		触点编码
	@Column(name = "place_agent_code", unique = true, length = 32)
	private String place_agent_code;//	varchar	32	0	False		下单发展人编码
	@Column(name = "place_agent_name", unique = true, length = 32)
	private String place_agent_name;//	varchar	32	0	False		下单发展人名称
	@Column(name = "remove_ident", unique = true, length = 32)
	private String remove_ident;//	varchar	32	0	False		剔除标识
	@Column(name = "back_info", unique = true, length = 32)
	private String back_info;//	varchar	32	0	False		时间回溯+订单回溯标识
	@Column(name = "malicious_info", unique = true, length = 64)
	private String malicious_info;//	varchar	64	0	False		5类黑名单类型+重复订单7day+审核打标
	@Column(name = "reject_info", unique = true, length = 32)
	private String reject_info;//	varchar	32	0	False		驳回标识
	@Column(name = "logistics_info", unique = true, length = 255)
	private String logistics_info;//	varchar	255	0	False		物流信息 物流公司+物流单号+物流状态
	@Column(name = "remarks", unique = true, length = 255)
	private String remarks;//	varchar	255	0	False		备注
	@Convert(converter = KplanSeconDarytracStatusEnum.EnumConvert.class)
	@Column(name = "track_status", precision = 32, scale = 0)
	private KplanSeconDarytracStatusEnum track_status;//	int4	32	0	False		跟单状态
	@Column(name = "operator", unique = true, length = 16)
	private String operator;//	varchar	16	0	False		操作人

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
			public Date getPlace_order_time() {
				return place_order_time;
			}
			public void setPlace_order_time(Date place_order_time) {
				this.place_order_time = place_order_time;
			}
			public String getProcduct_name() {
				return procduct_name;
			}
			public void setProcduct_name(String procduct_name) {
				this.procduct_name = procduct_name;
			}
			public String getPhone() {
				return phone;
			}
			public void setPhone(String phone) {
				this.phone = phone;
			}
			public String getOrder_status() {
				return order_status;
			}
			public void setOrder_status(String order_status) {
				this.order_status = order_status;
			}
			public String getMalicious_order() {
				return malicious_order;
			}
			public void setMalicious_order(String malicious_order) {
				this.malicious_order = malicious_order;
			}
			public String getFailed_reason() {
				return failed_reason;
			}
			public void setFailed_reason(String failed_reason) {
				this.failed_reason = failed_reason;
			}
			public String getDistribution_type() {
				return distribution_type;
			}
			public void setDistribution_type(String distribution_type) {
				this.distribution_type = distribution_type;
			}
			public String getUser_name() {
				return user_name;
			}
			public void setUser_name(String user_name) {
				this.user_name = user_name;
			}
			public String getPhone_num() {
				return phone_num;
			}
			public void setPhone_num(String phone_num) {
				this.phone_num = phone_num;
			}
			public String getUser_id() {
				return user_id;
			}
			public void setUser_id(String user_id) {
				this.user_id = user_id;
			}
			
			public ProStatusEnum getPro_status() {
				return pro_status;
			}
			public void setPro_status(ProStatusEnum pro_status) {
				this.pro_status = pro_status;
			}
			public Date getPro_date() {
				return pro_date;
			}
			public void setPro_date(Date pro_date) {
				this.pro_date = pro_date;
			}
			public String getOrder_source() {
				return order_source;
			}
			public void setOrder_source(String order_source) {
				this.order_source = order_source;
			}
			public String getDistribution_addres() {
				return distribution_addres;
			}
			public void setDistribution_addres(String distribution_addres) {
				this.distribution_addres = distribution_addres;
			}
			public String getOrder_id() {
				return order_id;
			}
			public void setOrder_id(String order_id) {
				this.order_id = order_id;
			}
			public String getPhone_province() {
				return phone_province;
			}
			public void setPhone_province(String phone_province) {
				this.phone_province = phone_province;
			}
			public String getPhone_city() {
				return phone_city;
			}
			public void setPhone_city(String phone_city) {
				this.phone_city = phone_city;
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
			public String getAge() {
				return age;
			}
			public void setAge(String age) {
				this.age = age;
			}
			public String getContact_code() {
				return contact_code;
			}
			public void setContact_code(String contact_code) {
				this.contact_code = contact_code;
			}
			public String getPlace_agent_code() {
				return place_agent_code;
			}
			public void setPlace_agent_code(String place_agent_code) {
				this.place_agent_code = place_agent_code;
			}
			public String getPlace_agent_name() {
				return place_agent_name;
			}
			public void setPlace_agent_name(String place_agent_name) {
				this.place_agent_name = place_agent_name;
			}
			public String getRemove_ident() {
				return remove_ident;
			}
			public void setRemove_ident(String remove_ident) {
				this.remove_ident = remove_ident;
			}
			public String getBack_info() {
				return back_info;
			}
			public void setBack_info(String back_info) {
				this.back_info = back_info;
			}
			public String getMalicious_info() {
				return malicious_info;
			}
			public void setMalicious_info(String malicious_info) {
				this.malicious_info = malicious_info;
			}
			public String getReject_info() {
				return reject_info;
			}
			public void setReject_info(String reject_info) {
				this.reject_info = reject_info;
			}
			public String getLogistics_info() {
				return logistics_info;
			}
			public void setLogistics_info(String logistics_info) {
				this.logistics_info = logistics_info;
			}
			public String getRemarks() {
				return remarks;
			}
			public void setRemarks(String remarks) {
				this.remarks = remarks;
			}
			
			public KplanSeconDarytracStatusEnum getTrack_status() {
				return track_status;
			}
			public void setTrack_status(KplanSeconDarytracStatusEnum track_status) {
				this.track_status = track_status;
			}
			public String getOperator() {
				return operator;
			}
			public void setOperator(String operator) {
				this.operator = operator;
			}
			@Override
			public String toString() {
				return "kplan_secondary_orders [id=" + id + ", order_no=" + order_no + ", place_order_time="
						+ place_order_time + ", procduct_name=" + procduct_name + ", phone=" + phone + ", order_status="
						+ order_status + ", malicious_order=" + malicious_order + ", failed_reason=" + failed_reason
						+ ", distribution_type=" + distribution_type + ", user_name=" + user_name + ", phone_num="
						+ phone_num + ", user_id=" + user_id + ", pro_status=" + pro_status + ", pro_date=" + pro_date
						+ ", order_source=" + order_source + ", distribution_addres=" + distribution_addres
						+ ", order_id=" + order_id + ", phone_province=" + phone_province + ", phone_city=" + phone_city
						+ ", post_province=" + post_province + ", post_city=" + post_city + ", post_district="
						+ post_district + ", age=" + age + ", contact_code="
						+ contact_code + ", place_agent_code=" + place_agent_code + ", place_agent_name="
						+ place_agent_name + ", remove_ident=" + remove_ident + ", back_info=" + back_info
						+ ", malicious_info=" + malicious_info + ", reject_info=" + reject_info + ", logistics_info="
						+ logistics_info + ", remarks=" + remarks + ", track_status=" + track_status + ", operator="
						+ operator + "]";
			}
			
			
			
			
			
			

}
