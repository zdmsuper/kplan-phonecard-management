package com.kplan.phonecard.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kplan.phonecard.domain.CoreOrdersMarketk;
import com.kplan.phonecard.domain.KplanPhoneNumber;
import com.kplan.phonecard.domain.msgRes;
import com.kplan.phonecard.query.CoreOrdersMarketkQuery;
import com.kplan.phonecard.service.CoreordersMarketkService;
import com.kplan.phonecard.utils.SqeUtils;

@Component
@Transactional
public class CoreordersMarketkManager extends BaseManager{
	private static final Logger logger = LoggerFactory.getLogger(CoreordersMarketkManager.class);
	@Autowired
	CoreordersMarketkService coreOrderSerbice;
	public Page<CoreOrdersMarketk> findOrder(@NotNull CoreOrdersMarketkQuery query, Pageable pageable){
			Specification<CoreOrdersMarketk> spec=new Specification<CoreOrdersMarketk>() {
			@Override
			public Predicate toPredicate(Root<CoreOrdersMarketk> r, CriteriaQuery<?> qr,
					CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
//				cb.equal(r.get("id"), 23L) "线下上门渠道"
				if(query.getCreatedDateStart()!=null&&query.getCreatedDateEnd()!=null) {
					list.add(cb.between(r.get("create_time"), query.getCreatedDateStart(), query.getCreatedDateEnd()));
				}
				list.add(cb.equal(r.get("order_source"),"线下上门渠道"));
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
//		String sql="select * from core_orders_market_k limit 10";
//		List<Object[] > l=this.coreOrderSerbice.getNativeResultList(sql);
		return this.coreOrderSerbice.findAll(spec, pageable);
	}
	
	public Object savaOrders(String userName, String userid, String address, String ordersource, String province_code,
			String province_name, String re_phone, String city, String cityName, String district, String districtName,
			String phone_Num, String smsstatus,String Productcode,String Productname) {
		try {
			KplanPhoneNumber phone = (KplanPhoneNumber) coreOrderSerbice.getById( phone_Num, KplanPhoneNumber.class);
			if (phone != null) {
				if (phone.getUse_not() != 0) {
					msgRes msg = new msgRes();
					msg.setCode("202");
					msg.setStatus("202");
					msg.setMsg("选择的订购号码已被使用，请重新选号");
					return JSON.toJSON(msg);
				} else {
					phone.setPhone_num(re_phone);
					phone.setUse_not(1);
					this.coreOrderSerbice.modify(phone);
					CoreOrdersMarketk k = new CoreOrdersMarketk();
					k.setReceiver_name(userName);
					k.setAccess_name(userName);
					k.setAccess_id_number(userid);
					k.setReceiver_address(address);
					k.setOrder_source("线下上门渠道");
					k.setProvince_code(province_code);
					k.setProvince_name(province_name);
					k.setReceiver_phone(re_phone);
					k.setCity_code(city);
					k.setCity_name(cityName);
					k.setDistrict_code(district);
					k.setDistrict_name(districtName);
					k.setOrder_number(phone_Num);
					k.setInitial_status(20);
					k.setOrder_status(0);
					k.setExport_status(1);
					k.setVisit_code(0);
					k.setCreatetime(new Date());
//					k.setProduct_code("981610241535");
//					k.setProduct_name("大王卡");
					k.setProduct_code(Productcode);
					k.setProduct_name(Productname);
					k.setBusiness_type("K计划");
					k.setDifferent_nets(-1);
					k.setId(SqeUtils.getBILIBILISqeNo());
					this.coreOrderSerbice.add(k);
					msgRes msg = new msgRes();
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("操作成功");
//					if ("1".equals(smsstatus)) {
//						String content = "您申领的" + phone_Num
//								+ "号卡，工作人员将尽快为您上门派送，请保持电话畅通！温馨提示：开卡后充值100元/200元后可获得等额加油优惠金。回复JY了解活动详情";
//						if (SendSmsBUtils.qryMobile(re_phone)) {
//							SendSmsBUtils.sendSms(re_phone, "sccu", content, "sccu999");
//						} else {
//							String msgContent = re_phone+" 您申领的" + phone_Num
//									+ "号卡，工作人员将尽快为您上门派送，请保持电话畅通！温馨提示：首次充值100元/200元后使用新号卡发短信YHJ至10655999可获得等额加油优惠金，适用于全国8万家合作油站，可享受最高7折优惠。";
//							SendSmsBUtils.sendSms("18583609824", "sccu", msgContent, "sccu999");
//						}
//					}
					return JSON.toJSON(msg);
				}
			} else {
				msgRes msg = new msgRes();
				msg.setCode("201");
				msg.setStatus("201");
				msg.setMsg("选择的订购号码不存在");
				return JSON.toJSON(msg);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			msgRes msg = new msgRes();
			msg.setCode("222");
			msg.setStatus("222");
			msg.setMsg("系统异常请联系管理员");
			return JSON.toJSON(msg);
		}
	}
	
}
