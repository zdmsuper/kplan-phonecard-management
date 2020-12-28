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
@Table(name = "kplan_door_personnel")
public class KplanDoorPersonnel extends BaseDomain{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="kplan_door_personnel_sequence")
	@SequenceGenerator(name="kplan_door_personnel_sequence",sequenceName="kplan_door_personnel_sequence",allocationSize=1)
	private Integer id;
	@Column(name = "accessName", unique = true, length = 32)
	private String username;//	varchar	16	0	False		姓名
	@Column(name = "phone", unique = true, length = 32)
	private String phone;//	varchar	16	0	False		联系电话
	@Column(name = "company", unique = true, length = 32)
	private String company;//	varchar	64	0	False		分公司
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	

}
