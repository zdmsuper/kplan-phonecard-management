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
@Table(name = "core_orders_track_log")
public class CoreordersTrackLog extends BaseDomain{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="core_orders_track_log_id_seq")
	@SequenceGenerator(name="core_orders_track_log_id_seq",sequenceName="core_orders_track_log_id_seq",allocationSize=1)
	private Integer id;//	int4	32
	@Column(name = "delivery_order_no", unique = true, length = 32)
	private String delivery_order_no;//	varchar	200
	@Column(name = "operator", unique = true, length = 32)
	private String operator;//	varchar	50
	@Column(name = "log_info", unique = true, length = 32)
	private String log_info;//	varchar	0
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 7)
	private Date create_time;//	timestamp	6
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDelivery_order_no() {
		return delivery_order_no;
	}
	public void setDelivery_order_no(String delivery_order_no) {
		this.delivery_order_no = delivery_order_no;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getLog_info() {
		return log_info;
	}
	public void setLog_info(String log_info) {
		this.log_info = log_info;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	
	


}
