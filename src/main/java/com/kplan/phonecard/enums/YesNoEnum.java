package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.kplan.phonecard.exceptions.BusinessException;

public enum YesNoEnum {
	YES(1, "是"), NO(0, "否"),;

	private Integer code;
	private String desc;

	private YesNoEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	// 获取枚举实例
	public static YesNoEnum fromValue(Integer value) {
		for (YesNoEnum statusEnum : YesNoEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum;
			}
		}
		throw new BusinessException("枚举类型错误");
	}

//	@Converter
	public static class EnumConvert implements AttributeConverter<YesNoEnum, Integer> {
		@Override
		public Integer convertToDatabaseColumn(YesNoEnum attribute) {
			return attribute.getCode();
		}

		@Override
		public YesNoEnum convertToEntityAttribute(Integer dbData) {
			return YesNoEnum.fromValue(dbData);
		}
	}
}
