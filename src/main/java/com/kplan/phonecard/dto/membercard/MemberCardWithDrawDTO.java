package com.kplan.phonecard.dto.membercard;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class MemberCardWithDrawDTO {
	@NotNull
	private Long id;
	@Digits(integer = 8, fraction = 2, message = "提现金额格式不正确")
	private BigDecimal amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
