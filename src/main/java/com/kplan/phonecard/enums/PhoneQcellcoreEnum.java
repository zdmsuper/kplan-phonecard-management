package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;

public enum PhoneQcellcoreEnum {
	wl("18581959364","文路"),
	ZTWL("13281168639","中台文路"),
	SY("18608009591","邵燕"),
	XYH("15528275575","薛颜虎"),
	CWP("13086613750","陈文萍"),
	SX("13281876760","苏秀"),;
	private String code;
	private String desc;
	private PhoneQcellcoreEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	// 获取枚举实例
	public static PhoneQcellcoreEnum fromValue(String value) {
		for (PhoneQcellcoreEnum statusEnum : PhoneQcellcoreEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum;
			}
		}
		return PhoneQcellcoreEnum.ZTWL;
	}

//	@Converter
	public static class EnumConvert implements AttributeConverter<PhoneQcellcoreEnum, String> {
		@Override
		public String convertToDatabaseColumn(PhoneQcellcoreEnum attribute) {
			return attribute.getCode();
		}

		@Override
		public PhoneQcellcoreEnum convertToEntityAttribute(String dbData) {
			return PhoneQcellcoreEnum.fromValue(dbData);
		}
	}
}
