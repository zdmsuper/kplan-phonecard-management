package com.kplan.phonecard.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.kplan.phonecard.base.BaseDomain;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the kplan_channel_number_detail database table.
 * 
 */
@Entity
@Table(name="kplan_channel_number_detail")
@NamedQuery(name="KplanChannelNumberDetail.findAll", query="SELECT k FROM KplanChannelNumberDetail k")
public class KplanChannelNumberDetail extends  BaseDomain {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE,generator="kplan_channel_number_detail_id_seq")
    @SequenceGenerator(sequenceName="kplan_channel_number_detail_id_seq", name="kplan_channel_number_detail_id_seq",allocationSize = 1)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cread_date", length = 7)
	private Date creadDate;

	@Column(name="delivery_num")
	private Integer deliveryNum;

	@Column(name="delivery_order")
	private Integer deliveryOrder;

	@Column(name="kplan_agent_name")
	private String kplanAgentName;

	@Column(name="kplan_contact_name")
	private String kplanContactName;

	public KplanChannelNumberDetail() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreadDate() {
		return creadDate;
	}

	public void setCreadDate(Date creadDate) {
		this.creadDate = creadDate;
	}

	public Integer getDeliveryNum() {
		return this.deliveryNum;
	}

	public void setDeliveryNum(Integer deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public Integer getDeliveryOrder() {
		return this.deliveryOrder;
	}

	public void setDeliveryOrder(Integer deliveryOrder) {
		this.deliveryOrder = deliveryOrder;
	}

	public String getKplanAgentName() {
		return this.kplanAgentName;
	}

	public void setKplanAgentName(String kplanAgentName) {
		this.kplanAgentName = kplanAgentName;
	}

	public String getKplanContactName() {
		return this.kplanContactName;
	}

	public void setKplanContactName(String kplanContactName) {
		this.kplanContactName = kplanContactName;
	}

}