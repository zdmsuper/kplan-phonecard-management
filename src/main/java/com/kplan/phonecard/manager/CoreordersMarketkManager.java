package com.kplan.phonecard.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
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
import com.kplan.phonecard.domain.Kplanprocducts;
import com.kplan.phonecard.domain.OrderRowModel;
import com.kplan.phonecard.domain.UnicomPostCityCode;
import com.kplan.phonecard.domain.msgRes;
import com.kplan.phonecard.enums.ExportStatusEnum;
import com.kplan.phonecard.enums.OrderStatusEnum;
import com.kplan.phonecard.query.CoreOrdersMarketkQuery;
import com.kplan.phonecard.service.CoreordersMarketkService;
import com.kplan.phonecard.utils.SqeUtils;

@Component
@Transactional
public class CoreordersMarketkManager extends BaseManager{
	private static final Logger logger = LoggerFactory.getLogger(CoreordersMarketkManager.class);
	@Autowired
	CoreordersMarketkService coreOrderSerbice;
	@Autowired
	UnicomPostcityCodeManager unicomPostcityCodeManager;
	public Page<CoreOrdersMarketk> findOrder(@NotNull CoreOrdersMarketkQuery query, Pageable pageable){
			Specification<CoreOrdersMarketk> spec=new Specification<CoreOrdersMarketk>() {
			@Override
			public Predicate toPredicate(Root<CoreOrdersMarketk> r, CriteriaQuery<?> qr,
					CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
//				cb.equal(r.get("id"), 23L) "线下上门渠道"
				if(query.getCreatedDateStart()!=null&&query.getCreatedDateEnd()!=null) {
					list.add(cb.between(r.get("createtime"), query.getCreatedDateStart(), query.getCreatedDateEnd()));
				}
				if(StringUtils.trimToNull(query.getKeyword())!=null) {
					list.add(cb.or(cb.equal(r.get("receiver_phone"), query.getKeyword()),cb.equal(r.get("order_number"), query.getKeyword())));
				}
				if(query.getDomain().getOrder_status()!=null) {
					list.add(cb.equal(r.get("order_status"), query.getDomain().getOrder_status()));
				}
				if(query.getDomain().getOrder_source()!=null) {
				list.add(cb.equal(r.get("order_source"),query.getDomain().getOrder_source()));
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
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
					String phonesql="update kplan_phone_number set use_not=2 where phone_num='"+re_phone+"' and use_not=1";
					this.coreOrderSerbice.exeNative(phonesql);
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
					k.setOrder_status(OrderStatusEnum.InitOrderStatus);
					k.setExport_status(ExportStatusEnum.EXPORTSTATUS1);
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
	
	
	
	public Object savaNextOrders(String orderNo,String userName, String userid, String address, String ordersource, String province_code,
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
					String phonesql="update kplan_phone_number set use_not=2 where phone_num='"+re_phone+"' and use_not=1";
					this.coreOrderSerbice.exeNative(phonesql);
					phone.setPhone_num(re_phone);
					phone.setUse_not(1);
					this.coreOrderSerbice.modify(phone);
					CoreOrdersMarketk k ;
					String sql="from  CoreOrdersMarketk where id='"+orderNo+"'";
					List<CoreOrdersMarketk> l=this.coreOrderSerbice.getResultList(sql);
					if(l!=null&&l.size()>0) {
						k=l.get(0);
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
						k.setOrder_status(OrderStatusEnum.InitOrderStatus);
//						k.setOrder_status(OrderStatusEnum.SUCCESSOrderStatus);
						k.setExport_status(ExportStatusEnum.EXPORTSTATUS1);
//						k.setExport_status(ExportStatusEnum.EXPORTSTATUS2);
						k.setVisit_code(0);
						k.setBusiness_type("K计划");
						k.setDifferent_nets(-1);
						this.coreOrderSerbice.modify(k);
						msgRes msg = new msgRes();
						msg.setCode("200");
						msg.setStatus("200");
						msg.setMsg("操作成功");
						return JSON.toJSON(msg);
					}
					else {
						msgRes msg = new msgRes();
						msg.setCode("222");
						msg.setStatus("222");
						msg.setMsg("查找不到订单");
						return JSON.toJSON(msg);
					}
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
	
	public Object reSet(String orderNo,String phone) {
		String sql="update core_orders_market_k set export_status=1,initial_status=20,order_status=0,visit_code=1,order_number='' where order_no='"+orderNo+"' and order_status!=11";
		String phonesql="update kplan_phone_number set use_not=2 where phone_num='"+phone+"'";
		
		msgRes msg = new msgRes();
		try {
			 this.coreOrderSerbice.exeNative(sql);
			 this.coreOrderSerbice.exeNative(phonesql);
				msg.setCode("200");
				msg.setStatus("200");
				msg.setMsg("订单重置成功");
		} catch (Exception e) {
			msg.setCode("222");
			msg.setStatus("222");
			msg.setMsg("系统异常请联系管理员");
		}
			return JSON.toJSON(msg);
			
	}
	
	public List<Kplanprocducts> qryProcDucts(){
		String sql="from Kplanprocducts";
		List<Kplanprocducts> l=this.coreOrderSerbice.getResultList(sql);
		return l;
	}
	
	public Object addOrders(List<OrderRowModel> l,String proTag) {
		msgRes msg = new msgRes();
		try {
			
			if(l!=null&&l.size()>0) {
				for(OrderRowModel o:l) {
					UnicomPostCityCode c=this.unicomPostcityCodeManager.findById(o.getDistrictCode());
					if(c!=null) {
						CoreOrdersMarketk k = new CoreOrdersMarketk();
						k.setReceiver_name(o.getUserName().trim());
						k.setAccess_name(o.getUserName().trim());
						k.setAccess_id_number(o.getUserId().trim());
						k.setReceiver_address(o.getAddress().trim());
						k.setOrder_source("线下上门渠道");
						k.setProvince_code(c.getProvince_code());
						k.setProvince_name(c.getProvince_name());
						k.setReceiver_phone(o.getPhone().trim());
						k.setCity_code(c.getCity_code());
						k.setCity_name(c.getCity_name());
						k.setDistrict_code(c.getDistrict_code());
						k.setDistrict_name(c.getDistrict_name());
						k.setInitial_status(20);
						if("1".equals(proTag)) {
						k.setOrder_status(OrderStatusEnum.WAITPHONE);
						k.setExport_status(ExportStatusEnum.EXPORTSTATUS4);
						}else {
							k.setOrder_status(OrderStatusEnum.InitOrderStatus);
							k.setExport_status(ExportStatusEnum.EXPORTSTATUS1);
						}
					
						k.setVisit_code(0);
						k.setCreatetime(new Date());
						k.setProduct_code(o.getProcductCode().trim());
						k.setProduct_name(o.getProcductName().trim());
						k.setBusiness_type("K计划");
						k.setDifferent_nets(-1);
						k.setId(SqeUtils.getBILIBILISqeNo());
						this.coreOrderSerbice.add(k);
						
					}
					else {
						msg.setCode("200");
						msg.setStatus("200");
						msg.setMsg("获取地市信息失败");
					}
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单导入成功");
				}
				
			}
		} catch (Exception e) {
			msg.setCode("222");
			msg.setStatus("222");
			msg.setMsg("系统异常请联系管理员");
			logger.error(e.getMessage(),e);
		}
		
		return JSON.toJSON(msg);
	} 
	
	public CoreOrdersMarketk findById(String id) {
		String sql="from  CoreOrdersMarketk where id='"+id+"'";
		List<CoreOrdersMarketk> l=this.coreOrderSerbice.getResultList(sql);
		if(l!=null&&l.size()>0) {
			return l.get(0);
		}
	return 	null;
	}
}
