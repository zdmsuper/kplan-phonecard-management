package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;

import com.kplan.phonecard.exceptions.BusinessException;

public enum ExamineStatusEnum {
	/**
	 * 待审核
	 */
	Other(0,"待审核"),
	/**
	 * 审核成功
	 */
	SUCCESS(1,"审核成功"),
	/**
	 * 订单已转
	 */
	FAILED(2,"订单已转"),
	/**
	 * 审核失败
	 */
	OTHERFAILED(3,"已被三方转走"),
	/**
	 * 未知
	 */
	UNKNOW(null,"未知")
	,;
	private ExamineStatusEnum(Integer code, String desc) {
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
	public static ExamineStatusEnum fromValue(Integer value) {
		for (ExamineStatusEnum statusEnum : ExamineStatusEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum;
			}
		}
		return ExamineStatusEnum.UNKNOW;
	}

//	@Converter
	public static class EnumConvert implements AttributeConverter<ExamineStatusEnum, Integer> {
		@Override
		public Integer convertToDatabaseColumn(ExamineStatusEnum attribute) {
			return attribute.getCode();
		}

		@Override
		public ExamineStatusEnum convertToEntityAttribute(Integer dbData) {
			return ExamineStatusEnum.fromValue(dbData);
		}
	}
}
