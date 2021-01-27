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
import com.kplan.phonecard.enums.MakePhoneStatusEnum;
import com.kplan.phonecard.enums.ProStatusEnum;
@Entity
@Table(name = "kplan_unicom_phone")
public class KplanUnicomPhone extends BaseDomain{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="kplan_unicom_phone_sequence")
	@SequenceGenerator(name="kplan_unicom_phone_sequence",sequenceName="kplan_unicom_phone_sequence",allocationSize=1)
			private Long id;//	int4	32	0	True	BM?
			@Column(name = "phone", unique = true, length = 16)	
			private String phone;//	varchar	16	0	False		电话号码
			@Column(name = "section_no", unique = true, length = 16)	
			private String section_no;	//varchar	16	0	False		号段
			@Column(name = "middle_secition_no", unique = true, length = 16)	
			private String middle_secition_no;//	varchar	8	0	False		中间号段
			@Column(name = "include_four", unique = true, length = 16)
			private String include_four;//	varchar	16	0	False		是否包含4
			@Column(name = "rule_name", unique = true, length = 16)
			private String rule_name;//	varchar	16	0	False		规则
			@Convert(converter = MakePhoneStatusEnum.EnumConvert.class)
			@Column(name = "make_status", precision = 22, scale = 0)
			private MakePhoneStatusEnum 	makestatus;//	int2	16	0	False		是否已使用 0 未使用 1 已被使用
			@Column(name = "phone_num", unique = true, length = 16)
			private String 	phone_num;//	varchar	16	0	False		使用的号码
			@Temporal(TemporalType.TIMESTAMP)
			@Column(name = "crea_date", length = 7)
			private Date crea_date;	//timestamp	6	0	False		创建时间
			@Temporal(TemporalType.TIMESTAMP)
			@Column(name = "up_date", length = 7)
			private Date up_date;//	timestamp	6	0	False		更新时间
			@Column(name = "remarks", unique = true, length = 255)
			private String remarks;	//varchar	255	0	False		备注
			public Long getId() {
				return id;
			}
			public void setId(Long id) {
				this.id = id;
			}
			public String getPhone() {
				return phone;
			}
			public void setPhone(String phone) {
				this.phone = phone;
			}
			public String getSection_no() {
				return section_no;
			}
			public void setSection_no(String section_no) {
				this.section_no = section_no;
			}
			public String getMiddle_secition_no() {
				return middle_secition_no;
			}
			public void setMiddle_secition_no(String middle_secition_no) {
				this.middle_secition_no = middle_secition_no;
			}
			public String getInclude_four() {
				return include_four;
			}
			public void setInclude_four(String include_four) {
				this.include_four = include_four;
			}
			public String getRule_name() {
				return rule_name;
			}
			public void setRule_name(String rule_name) {
				this.rule_name = rule_name;
			}
			public MakePhoneStatusEnum getMakestatus() {
				return makestatus;
			}
			public void setMakestatus(MakePhoneStatusEnum makestatus) {
				this.makestatus = makestatus;
			}
			public String getPhone_num() {
				return phone_num;
			}
			public void setPhone_num(String phone_num) {
				this.phone_num = phone_num;
			}
			public Date getCrea_date() {
				return crea_date;
			}
			public void setCrea_date(Date crea_date) {
				this.crea_date = crea_date;
			}
			public Date getUp_date() {
				return up_date;
			}
			public void setUp_date(Date up_date) {
				this.up_date = up_date;
			}
			public String getRemarks() {
				return remarks;
			}
			public void setRemarks(String remarks) {
				this.remarks = remarks;
			}
			
			
			

}
