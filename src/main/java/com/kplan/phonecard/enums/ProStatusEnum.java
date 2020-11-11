package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;

public enum ProStatusEnum {
	/**
	 * 订单信息获取完成
	 */
	PROING(1,"订单信息获取完成"),
	/**
	 * 订单创建
	 */
	CREADORDER(0,"订单创建"),
	/**
	 * 订单信息获取中
	 */
	ORDERINFOING(3,"订单信息获取中"),
	/**
	 * 反诈信息获取中
	 */
	RISKORDERING(4,"反诈信息获取中"),
	/**
	 * 订单处理完成
	 */
	ENDPROSTATUS(99,"订单处理完成"),/** 
	* 未知
	 */
	UNKNOW(null,"未知"),;
	
	private ProStatusEnum(Integer code, String desc) {
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
	public static ProStatusEnum fromValue(Integer value) {
		for (ProStatusEnum statusEnum : ProStatusEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum;
			}
		}
		return ProStatusEnum.UNKNOW;
	}

//	@Converter
	public static class EnumConvert implements AttributeConverter<ProStatusEnum, Integer> {
		@Override
		public Integer convertToDatabaseColumn(ProStatusEnum attribute) {
			return attribute.getCode();
		}

		@Override
		public ProStatusEnum convertToEntityAttribute(Integer dbData) {
			return ProStatusEnum.fromValue(dbData);
		}
	}
}
