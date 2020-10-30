package com.kplan.phonecard.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;

import com.kplan.phonecard.exceptions.BusinessException;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public enum ScorDerStatusEnum {
	/**
	 * 订单创建
	 */
	InitialStatus(0,"订单创建"),
	
	/**
	 * 订单信息获取中
	 */
	InfoIng(3,"订单信息获取中"),
	
	/**
	 * 订单信息获取完成
	 */
	InfoStatus(1,"订单信息获取完成"),
	
	/**
	 * 反诈信息校验中
	 */
	FraudIng(4,"反诈信息校验中"),
	
	/**
	 * 反诈校验完成
	 */
	FraudStatus(2,"反诈校验完成"),
	
	/**
	 * 一证五号校验
	 */
	FiveCheck(12,"一证五号校验"),
	
	/**
	 * 风险地址校验
	 */
	RiskAddress(14,"风险地址校验"),
	
	/**
	 * 疑似风险地址
	 */
	SuspectedRiskAddress(15,"疑似风险地址"),
	
	/**
	 * 待人工审核
	 */
	ExamineVerify(16,"待人工审核"),
	
	/**
	 * 重复校验
	 */
	RepeatCheck(19,"重复校验"),
	
	/**
	 * 重复单审核
	 */
	ExamineCheck(18,"重复单审核"),
	
	/**
	 * 物流审核
	 */
	ExamineLogistics(17,"物流审核"),
	
	/**
	 * 审核通过
	 */
	ExamineAdopt(20,"审核通过"),
	
	/**
	 * 地址回访
	 */
	AddressReturn(30,"地址回访"),
	
	/**
	 * 回访挂起
	 */
	ReturnUp(31,"回访挂起"),
	
	/**
	 * 物流单挂起
	 */
	othen(32,"物流单挂起"),
	/**
	 * 未知
	 */
	UNKNOW(null,"未知")
	,;
	private ScorDerStatusEnum(Integer code, String desc) {
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
	public static ScorDerStatusEnum fromValue(Integer value) {
		for (ScorDerStatusEnum statusEnum : ScorDerStatusEnum.values()) {
			if (Objects.equals(value, statusEnum.getCode())) {
				return statusEnum;
			}
		}
		return ScorDerStatusEnum.UNKNOW;
	}

//	@Converter
	public static class EnumConvert implements AttributeConverter<ScorDerStatusEnum, Integer> {
		@Override
		public Integer convertToDatabaseColumn(ScorDerStatusEnum attribute) {
			return attribute.getCode();
		}

		@Override
		public ScorDerStatusEnum convertToEntityAttribute(Integer dbData) {
			return ScorDerStatusEnum.fromValue(dbData);
		}
	}
}
