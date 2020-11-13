package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;

/**
 * @author Administrator
 *
 */
public enum OrderStatusEnum {
	/**
	 * 初始化，待处理
	 */
	
	InitOrderStatus(0,"初始化，待处理"),
	/**
	 * 待选号
	 */
	WAITPHONE(4,"待选号"),
	/**
	 * 待回导
	 */
	ReOrderStatus(3,"待回导"),
	
	/**
	 * 成功
	 */
	SUCCESSOrderStatus(11,"成功"),
	
	/**
	 * 失败
	 */
	FAILEDOrderStatus(21,"失败"),
	
	/**
	 * 不办理
	 */
	NotHandLeOrderStatus(22,"不办理"),
	
	/**
	 * 手工失败
	 */
	HandmadeFailedOrderStatus(2,"手工失败"),
	
	/**
	 * 系统处理中
	 */
	SystemProIngOrderStatus(10,"系统处理中"),
	/** 
	* 未知
	 */
	UNKNOW(null,"未知"),;
	private OrderStatusEnum(Integer code, String desc) {
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
	public static OrderStatusEnum fromValue(Integer value) {
		for (OrderStatusEnum statusEnum : OrderStatusEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum;
			}
		}
		return OrderStatusEnum.UNKNOW;
	}

//	@Converter
	public static class EnumConvert implements AttributeConverter<OrderStatusEnum, Integer> {
		@Override
		public Integer convertToDatabaseColumn(OrderStatusEnum attribute) {
			return attribute.getCode();
		}

		@Override
		public OrderStatusEnum convertToEntityAttribute(Integer dbData) {
			return OrderStatusEnum.fromValue(dbData);
		}
	}
}
