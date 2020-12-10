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
@Table(name = "kplan_molicioustag")
public class KplanMolicioustag extends BaseDomain{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="kplan_molicioustag_sequence")
	@SequenceGenerator(name="kplan_molicioustag_sequence",sequenceName="kplan_molicioustag_sequence",allocationSize=1)
	private Integer id;//	int4	32	0	True	BM?
	@Column(name = "malicious_tag", unique = true, length = 64)
	private String malicious_tag;//	varchar	64	0	False		恶意标签
	@Column(name = "addgroup", unique = true, length = 64)
	private String addgroup;//	varchar;//	32	0	False		所属组
	@Column(name = "malicious_code", unique = true, length = 64)
	private String malicious_code;	//varchar	64	0	False		恶意标签编码
	@Column(name = "business_type", unique = true, length = 64)
	private String business_type;//	varchar	64	0	False		业务类型
	@Column(name = "business_code", unique = true, length = 64)
	private String business_code;//	varchar	32	0	False		业务代码
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMalicious_tag() {
		return malicious_tag;
	}
	public void setMalicious_tag(String malicious_tag) {
		this.malicious_tag = malicious_tag;
	}
	public String getAddgroup() {
		return addgroup;
	}
	public void setAddgroup(String addgroup) {
		this.addgroup = addgroup;
	}
	public String getMalicious_code() {
		return malicious_code;
	}
	public void setMalicious_code(String malicious_code) {
		this.malicious_code = malicious_code;
	}
	public String getBusiness_type() {
		return business_type;
	}
	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}
	public String getBusiness_code() {
		return business_code;
	}
	public void setBusiness_code(String business_code) {
		this.business_code = business_code;
	}

}
