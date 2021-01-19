package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;

public enum MakePhoneStatusEnum {
	/**
	 * 未使用
	 */
	make(0,"未使用"),
	/**
	 * 已使用
	 */
	makeNot(1,"已使用"),
	/**
	 * 未知
	 */
	UNKNOW(null,"未知")
	,;
	private Integer code;
	private String desc;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	private MakePhoneStatusEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	// 获取枚举实例
		public static MakePhoneStatusEnum fromValue(Integer value) {
			for (MakePhoneStatusEnum statusEnum : MakePhoneStatusEnum.values()) {
				if (Objects.equals(value, statusEnum.getCode())) {
					return statusEnum;
				}
			}
			return MakePhoneStatusEnum.UNKNOW;
		}

//		@Converter
		public static class EnumConvert implements AttributeConverter<MakePhoneStatusEnum, Integer> {
			@Override
			public Integer convertToDatabaseColumn(MakePhoneStatusEnum attribute) {
				return attribute.getCode();
			}

			@Override
			public MakePhoneStatusEnum convertToEntityAttribute(Integer dbData) {
				return MakePhoneStatusEnum.fromValue(dbData);
			}
		}
}
