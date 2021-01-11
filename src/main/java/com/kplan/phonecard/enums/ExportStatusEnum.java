package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;

public enum ExportStatusEnum {
	/**
	 * 不符合导出状态
	 */
	EXPORTSTATUS0(0,"不符合导出状态"),
	/**
	 * 待系统自动导出
	 */
	EXPORTSTATUS1(1,"待系统自动导出"),
	/**
	 * 待手工导出
	 */
	EXPORTSTATUS7(7,"待手工导出7"),
	/**
	 * 待手工导出
	 */
	EXPORTSTATUS8(8,"待手工导出8"),
	
	/**
	 * 待手工导出
	 */
	EXPORTSTATUS9(9,"待手工导出9"),
	/**
	 * ZOP平台下单处理中
	 */
	EXPORTSTATUS10(10,"ZOP平台下单处理中"),
	/**
	 * 驳回单已导出
	 */
	EXPORTSTATUS15(15,"驳回单已导出"),
	
	/**
	 * 已导出订单
	 */
	EXPORTSTATUS2(2,"已导出订单"),
	
	/**
	 * 待选号
	 */
	EXPORTSTATUS4(4,"待选号"),
	
	/**
	 * 未知
	 */
	UNKNOW(null,"未知")
	,;		
	
	private ExportStatusEnum(Integer code, String desc) {
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
	public static ExportStatusEnum fromValue(Integer value) {
		for (ExportStatusEnum statusEnum : ExportStatusEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum;
			}
		}
		return ExportStatusEnum.UNKNOW;
	}

//	@Converter
	public static class EnumConvert implements AttributeConverter<ExportStatusEnum, Integer> {
		@Override
		public Integer convertToDatabaseColumn(ExportStatusEnum attribute) {
			return attribute.getCode();
		}

		@Override
		public ExportStatusEnum convertToEntityAttribute(Integer dbData) {
			return ExportStatusEnum.fromValue(dbData);
		}
	}
}
