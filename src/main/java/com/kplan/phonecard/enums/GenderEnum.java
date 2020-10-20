package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.kplan.phonecard.exceptions.BusinessException;

public enum GenderEnum {
	/**
	 * 男
	 */
	MALE(10, "男"),
	/**
	 * 女
	 */
	FEMALE(20, "女"),
	/**
	 * 保密
	 */
	SECRECY(30, "保密"),
	/**
	 * 其他
	 */
	Other(40, "其他"),;
	private GenderEnum(Integer code, String desc) {
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
	public static GenderEnum fromValue(Integer value) {
		for (GenderEnum statusEnum : GenderEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum;
			}
		}
		throw new BusinessException("枚举类型错误");
	}

//	@Converter
	public static class EnumConvert implements AttributeConverter<GenderEnum, Integer> {
		@Override
		public Integer convertToDatabaseColumn(GenderEnum attribute) {
			return attribute.getCode();
		}

		@Override
		public GenderEnum convertToEntityAttribute(Integer dbData) {
			return GenderEnum.fromValue(dbData);
		}
	}
}
