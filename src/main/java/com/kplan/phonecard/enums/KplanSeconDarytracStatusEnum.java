package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;

/**
 * @author Administrator
 *
 */
public enum KplanSeconDarytracStatusEnum {
	/**
	 * 初始状态
	 */
	INITSTATUS(0, "初始状态"),
	/**待获取订单信息
	 * 
	 */
	INISTATUS(-1,"待获取订单信息"),
	/**
	 * 等待处理
	 */
	WAITSTATUS(1, "等待处理"),
	/**
	 * 不符合回捞标准
	 */
	OUTSTATUS(2, "不符合回捞标准"),
	/**
	 * 回捞数据验证中
	 */
	BACKINGSTATUS(3, "回捞数据验证中"),
	/**
	 * 同意办理
	 */
	HANDLESTATUS(11, "同意办理"),
	
	/**
	 * 不办理
	 */
	FAILEDSTATUS(12, "不办理"),
	/**
	 * 二次回访
	 */
	SECONDVISITSTATUS(13,"二次回访"),
	/**
	 * 三次回访
	 */
	THREEVISITSTATUS(13,"三次回访"),
	/**
	 * 转运营
	 */
	TRANSFERTOOPERATION(14,"转运营"),
	/**
	 * 订单关闭
	 */
	CLOSESTATUS(15,"订单关闭"),
	/**
	 * 多次联系不上
	 */
	NOTPHONEEND(16,"多次联系不上"),
	/** 未知
	 */
	UNKNOW(null,"未知")
	,;
	private KplanSeconDarytracStatusEnum(Integer code, String desc) {
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
	public static KplanSeconDarytracStatusEnum fromValue(Integer value) {
		for (KplanSeconDarytracStatusEnum statusEnum : KplanSeconDarytracStatusEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum;
			}
		}
		return KplanSeconDarytracStatusEnum.UNKNOW;
	}

//	@Converter
	public static class EnumConvert implements AttributeConverter<KplanSeconDarytracStatusEnum, Integer> {
		@Override
		public Integer convertToDatabaseColumn(KplanSeconDarytracStatusEnum attribute) {
			return attribute.getCode();
		}

		@Override
		public KplanSeconDarytracStatusEnum convertToEntityAttribute(Integer dbData) {
			return KplanSeconDarytracStatusEnum.fromValue(dbData);
		}
	}
}
