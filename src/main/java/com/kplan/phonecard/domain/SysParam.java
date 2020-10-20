package com.kplan.phonecard.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Digits;

import com.kplan.phonecard.base.BaseDomain;

@Entity
public class SysParam extends BaseDomain{
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE,generator="SysParam_sequence")
    @SequenceGenerator(sequenceName="SysParam_sequence", name="SysParam_sequence",allocationSize = 1)

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	// 1 平台佣金
	private Integer type;
	
	@Digits(integer = 17, fraction = 4)
	private BigDecimal x;
	
	@Digits(integer = 17, fraction = 4)
	private BigDecimal y;
	
	@Digits(integer = 17, fraction = 4)
	private BigDecimal z;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getX() {
		return x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public BigDecimal getY() {
		return y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}

	public BigDecimal getZ() {
		return z;
	}

	public void setZ(BigDecimal z) {
		this.z = z;
	}
}
