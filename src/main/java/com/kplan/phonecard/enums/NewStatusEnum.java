package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;

public  enum NewStatusEnum {
		
	/**
	 * 接口调用失败
	 */
	
	interfaceFialed(214,"接口调用失败"),
	/**
	 * 未满16岁
	 */
	AgeFaile(215,"未满16岁"),
	/**
	 * 已满16岁
	 */
	AgeSUCCESS(216,"已满16岁"),
	/**
	 * 疑似风险地址
	 */
	SuspectedRiskAddress(217,"疑似风险地址"),
	/**
	 * 无风险地址
	 */
	RiskAddress(218,"无风险地址"),
	/**
	 * 订单重复
	 */
	RepeatAddress(219,"订单重复"),
	/**
	 * 订单不重复
	 */
	NotRepeatAddress(220,"订单不重复"),
	/**
	 * 订单锁定
	 */
	OrderOnLock(221,"订单锁定"),
	/**
	 * 订单解锁
	 */
	OrderNotLock(222,"订单解锁"),
	/**
	 * 通过单同步
	 */
	OrderSyn(316,"通过单同步"),
	/**
	 * 不通过单同步
	 */
	OrderNotSyn(317,"不通过单同步"),
	/**
	 * 重复单通过
	 */
	RepeatOrderSyn(318,"重复单通过"),
	/**
	 * 重复单不通过
	 */
	RepeatOrderNotSyn(319,"重复单不通过"),
	
	/**
	 * 地址无风险
	 */
	RiskNotAddress(320,"地址无风险"),
	/**
	 * 地址有风险
	 */
	RiskAndAddress(321,"地址有风险"),
	
	/**
	 * 物流通过
	 */
	LogisticsSyn(322,"物流通过"),
	
	/**
	 *物流不通过
	 */
	LogisticsNotSyn(323,"物流不通过"),
	
	/**
	 * 未接/无法接通
	 */
	PhoneNot(324,"未接/无法接通"),
	
	/**
	 * 挂断
	 */
	PhoneHangUP(325,"挂断"),
	
	/**
	 * 晚点联系
	 */
	PhoneLastContact(326,"晚点联系"),
	
	/**
	 * 关机
	 */
	PhoneShutDown(328,"关机"),
	
	/**
	 * 其他
	 */
	OTHER(329,"其他"),
	/**
	 * 回访成功
	 */
	ReturnVisit(330,"回访成功"),
	
	/**
	 * 不办理
	 */
	NotHandle(331,"不办理"),
	
	/**
	 * 多次联系不上
	 */
	MultipleContactsNot(332,"多次联系不上"),
	
	/**
	 * 语音不通
	 */
	VoiceNot(333,"语音不通"),
	
	/**
	 * 不清楚地址
	 */
	UnclearAddress(334,"不清楚地址"),
	
	/**
	 * 地址复核通过
	 */
	AddressRepeatSyn(335,"地址复核通过"),
	
	/**
	 * 地址复核不通过
	 */
	AddressRepeatNotSyn(336,"地址复核不通过"),
	
	/**
	 * 物流单通过
	 */
	LogisticsorDerSyn(337,"物流单通过"),
	
	/**
	 * 物流单不通过
	 */
	LogisticsorDerNotSyn(338,"物流单不通过"),
	
	/**
	 * 反诈无风险
	 */
	FraudNotRisk(210,"反诈无风险"),
	
	/**
	 * 反诈风险
	 */
	FraudRisk(211,"反诈风险"),
		
	/**
	 * 自然人校验通过
	 */
	NaturalPersonSyn(212,"自然人校验通过"),	
		
	/**
	 * 自然人校验不通过
	 */
	NaturalPersonNotSyn(213,"自然人校验不通过"),/**
	 * 未知
	 */
	UNKNOW(null,"未知")
	,;		
		
	private NewStatusEnum(Integer code, String desc) {
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
	public static NewStatusEnum fromValue(Integer value) {
		for (NewStatusEnum statusEnum : NewStatusEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum;
			}
		}
		return NewStatusEnum.UNKNOW;
	}

//	@Converter
	public static class EnumConvert implements AttributeConverter<NewStatusEnum, Integer> {
		@Override
		public Integer convertToDatabaseColumn(NewStatusEnum attribute) {
			return attribute.getCode();
		}

		@Override
		public NewStatusEnum convertToEntityAttribute(Integer dbData) {
			return NewStatusEnum.fromValue(dbData);
		}
	}
		
		
		

}
