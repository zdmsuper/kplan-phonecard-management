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
@Table(name = "kplan_procducts")
public class Kplanprocducts extends BaseDomain{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="kplan_procducts_sequence")
	@SequenceGenerator(name="kplan_procducts_sequence",sequenceName="kplan_procducts_sequence",allocationSize=1)
	private Integer id;//	int4	32	0	True	BM?
	@Column(name = "procduct_name", unique = true, length = 32)
	private String procduct_name;//	varchar	32	0	False		产品名称
	@Column(name = "procduct_code", unique = true, length = 32)
	private String procduct_code;//	varchar	32	0	False		产品编码
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProcduct_name() {
		return procduct_name;
	}
	public void setProcduct_name(String procduct_name) {
		this.procduct_name = procduct_name;
	}
	public String getProcduct_code() {
		return procduct_code;
	}
	public void setProcduct_code(String procduct_code) {
		this.procduct_code = procduct_code;
	}
	
	

}
