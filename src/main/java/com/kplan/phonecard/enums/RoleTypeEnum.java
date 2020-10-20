package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.kplan.phonecard.exceptions.BusinessException;

public enum RoleTypeEnum {
	/**
	 * 总经理
	 */
	GENERAL(10, "总经理"),
	/**
	 * 经理
	 */
	ASSISTANT(20, "经理"),
	/**
	 * 会计
	 */
	ACCOUNTING(30, "会计"),
	/**
	 * 收银员
	 */
	CASHIER(40, "收银员"),;
	private RoleTypeEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private Integer code;
	private String desc;

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	// 获取枚举实例
	public static RoleTypeEnum fromValue(Integer value) {
		for (RoleTypeEnum statusEnum : RoleTypeEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum;
			}
		}
		throw new BusinessException("枚举类型错误");
	}

//	@Converter
	public static class EnumConvert implements AttributeConverter<RoleTypeEnum, Integer> {
		@Override
		public Integer convertToDatabaseColumn(RoleTypeEnum attribute) {
			return attribute.getCode();
		}

		@Override
		public RoleTypeEnum convertToEntityAttribute(Integer dbData) {
			return RoleTypeEnum.fromValue(dbData);
		}
	}
}
