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
@Table(name = "kplan_examination")
public class KplanExamination extends BaseDomain{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="kplan_examination_sequence")
	@SequenceGenerator(name="kplan_examination_sequence",sequenceName="kplan_examination_sequence",allocationSize=1)
	private Integer id;
	@Column(name = "cont_code", unique = true, length = 32)
	private String cont_code;	//varchar	32	0	False		触点编码
	@Column(name = "examination_status", unique = true, length = 32)
	private String examination_status;//	int2	16	0	False		是否审单
	@Column(name = "transfer_job", unique = true, length = 32)
	private String transfer_job;	//varchar	32	0	False		转入工号
	@Column(name = "job_name", unique = true, length = 32)
	private String job_name;	//varchar	32	0	False		工号名称
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cread_date", length = 7)
	private Date cread_date;	//timestamp	6	0	False		创建时间
	@Column(name = "operator", unique = true, length = 32)
	private String operator;	//varchar	16	0	False		操作人
	@Column(name = "pro_type", unique = true, length = 32)
	private String pro_type;
	@Column(name = "program_type", unique = true, length = 32)
	private String program_type;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCont_code() {
		return cont_code;
	}
	public void setCont_code(String cont_code) {
		this.cont_code = cont_code;
	}
	public String getExamination_status() {
		return examination_status;
	}
	public void setExamination_status(String examination_status) {
		this.examination_status = examination_status;
	}
	public String getTransfer_job() {
		return transfer_job;
	}
	public void setTransfer_job(String transfer_job) {
		this.transfer_job = transfer_job;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	public Date getCread_date() {
		return cread_date;
	}
	public void setCread_date(Date cread_date) {
		this.cread_date = cread_date;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getPro_type() {
		return pro_type;
	}
	public void setPro_type(String pro_type) {
		this.pro_type = pro_type;
	}
	public String getProgram_type() {
		return program_type;
	}
	public void setProgram_type(String program_type) {
		this.program_type = program_type;
	}
	
	

}
