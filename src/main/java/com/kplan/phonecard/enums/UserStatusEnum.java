package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;

import com.kplan.phonecard.exceptions.BusinessException;

public enum UserStatusEnum {
	/**
	 * 正常
	 */
	VALID(10, "正常"),

	/**
	 * 锁定
	 */
	LOCKED(20, "停用");
	private UserStatusEnum(Integer code, String desc) {
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
	public static UserStatusEnum fromValue(Integer value) {
		for (UserStatusEnum statusEnum : UserStatusEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum;
			}
		}
		throw new BusinessException("枚举类型错误");
	}

	public static class EnumConvert implements AttributeConverter<UserStatusEnum, Integer> {
		@Override
		public Integer convertToDatabaseColumn(UserStatusEnum attribute) {
			return attribute.getCode();
		}

		@Override
		public UserStatusEnum convertToEntityAttribute(Integer dbData) {
			return UserStatusEnum.fromValue(dbData);
		}
	}
}
