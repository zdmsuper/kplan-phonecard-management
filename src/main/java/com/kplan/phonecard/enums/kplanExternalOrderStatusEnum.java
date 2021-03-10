package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;

public enum kplanExternalOrderStatusEnum {
	/**
	 * 订单创建
	 */
	InitStatus0(0,"订单创建"),
	/**
	 * 订单已导出
	 */
	EXPSTATUS(1,"订单已导出"),
	/**
	 * 回导成功
	 */
	EXPSTATUS2(2,"回导成功"),
	/**
	 * 回导失败
	 */
	EXPSTATUS3(3,"回导失败"),
	/**
	 * 异常订单
	 */
	EXPSTATUSERROR(-1,"异常订单"),
	/**
	 * 未知
	 */
	UNKNOW(null,"未知"), ;
	
	private kplanExternalOrderStatusEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
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
	
	// 获取枚举实例
	public static kplanExternalOrderStatusEnum fromValue(Integer value) {
		for (kplanExternalOrderStatusEnum statusEnum : kplanExternalOrderStatusEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum;
			}
		}
		return kplanExternalOrderStatusEnum.UNKNOW;
	}
	
	public static String fromValueDesc(Integer value) {
		for (kplanExternalOrderStatusEnum statusEnum : kplanExternalOrderStatusEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum.getDesc();
			}
		}
		return kplanExternalOrderStatusEnum.UNKNOW.desc;
	}

//	@Converter
	public static class EnumConvert implements AttributeConverter<kplanExternalOrderStatusEnum, Integer> {
		@Override
		public Integer convertToDatabaseColumn(kplanExternalOrderStatusEnum attribute) {
			return attribute.getCode();
		}

		@Override
		public kplanExternalOrderStatusEnum convertToEntityAttribute(Integer dbData) {
			return kplanExternalOrderStatusEnum.fromValue(dbData);
		}
	}
}
