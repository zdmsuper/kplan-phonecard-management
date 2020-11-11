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
@Table(name = "core_wopay_channel")
public class CoreWopayChannel extends BaseDomain{
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE,generator="core_wopay_channel_id_seq1")
    @SequenceGenerator(sequenceName="core_wopay_channel_id_seq1", name="core_wopay_channel_id_seq1",allocationSize = 1)
	private Long id;//	int4	32	0	True	BM?
	@Column(name = "channel_code", unique = true, length = 32)
	private String channel_code;//	varchar	50	0	True		渠道编码
	@Column(name = "operator", unique = true, length = 32)
	private String operator;//	varchar	20	0	False		操作员
	@Column(name = "channel_nature", unique = true, length = 32)
	private String channel_nature;//	varchar	50	0	False		渠道属性
	@Column(name = "branch", unique = true, length = 32)
	private String branch;//	varchar	100	0	False		分公司
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 7)
	private Date create_time;//	timestamp	6	0	True		创建时间
	@Column(name = "job_num", unique = true, length = 32)
	private String job_num;//	varchar	20	0	False		工号
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChannel_code() {
		return channel_code;
	}
	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getChannel_nature() {
		return channel_nature;
	}
	public void setChannel_nature(String channel_nature) {
		this.channel_nature = channel_nature;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getJob_num() {
		return job_num;
	}
	public void setJob_num(String job_num) {
		this.job_num = job_num;
	}
	
	
	

}
