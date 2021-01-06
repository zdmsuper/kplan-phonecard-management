package com.kplan.phonecard.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.kplan.phonecard.domain.CoreordersTrackLog;
import com.kplan.phonecard.domain.CustomerServiceLog;
import com.kplan.phonecard.domain.KplanPhoneNumber;
import com.kplan.phonecard.domain.Kplanprocducts;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.domain.OrderRowModel;
import com.kplan.phonecard.domain.UnicomPostCityCode;
import com.kplan.phonecard.domain.msgRes;
import com.kplan.phonecard.enums.ExportStatusEnum;
import com.kplan.phonecard.enums.OrderStatusEnum;
import com.kplan.phonecard.query.CoreOrdersMarketkQuery;
import com.kplan.phonecard.query.kplanscordersQuery;
import com.kplan.phonecard.service.CoreordersMarketkService;
import com.kplan.phonecard.service.CoreordersTrackLogService;
import com.kplan.phonecard.service.CustomerServiceLogService;
import com.kplan.phonecard.utils.DateUtils;
import com.kplan.phonecard.utils.SqeUtils;

@Component
@Transactional
public class CoreordersMarketkManager extends BaseManager {
	private static final Logger logger = LoggerFactory.getLogger(CoreordersMarketkManager.class);
	@Autowired
	CoreordersMarketkService coreOrderSerbice;
	@Autowired
	UnicomPostcityCodeManager unicomPostcityCodeManager;
	@Autowired
	CoreordersTrackLogService logService;
	@Autowired
	CustomerServiceLogService customerServiceLogService;


	public Page<CoreOrdersMarketk> findOrder(@NotNull CoreOrdersMarketkQuery query, Pageable pageable) {
		Specification<CoreOrdersMarketk> spec = new Specification<CoreOrdersMarketk>() {
			@Override
			public Predicate toPredicate(Root<CoreOrdersMarketk> r, CriteriaQuery<?> qr, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
//				cb.equal(r.get("id"), 23L) "线下上门渠道"
				if (query.getCreatedDateStart() != null && query.getCreatedDateEnd() != null) {
					list.add(cb.between(r.get("createtime"), query.getCreatedDateStart(), query.getCreatedDateEnd()));
				}
				if (StringUtils.trimToNull(query.getKeyword()) != null) {
					list.add(cb.or(cb.equal(r.get("receiver_phone"), query.getKeyword()),
							cb.equal(r.get("order_number"), query.getKeyword())));
				}
				if (query.getDomain().getOrder_status() != null) {
					list.add(cb.equal(r.get("order_status"), query.getDomain().getOrder_status()));
				}
				if (query.getDomain().getOrder_source() != null) {
					if("线下上门渠道".equals(query.getDomain().getOrder_source())) {
						list.add(cb.like(r.get("order_source"), "%"+query.getDomain().getOrder_source()+"%"));
					}else {
					list.add(cb.equal(r.get("order_source"), query.getDomain().getOrder_source()));
					}
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return this.coreOrderSerbice.findAll(spec, pageable);
	}

	public Page<CoreOrdersMarketk> maliciousList(@NotNull CoreOrdersMarketkQuery query, Pageable pageable) {
		Specification<CoreOrdersMarketk> spec = new Specification<CoreOrdersMarketk>() {
			public Predicate toPredicate(Root<CoreOrdersMarketk> r, CriteriaQuery<?> qr, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				try {
					list.add(cb.between(r.get("createtime"), DateUtils.getDayNumT(100000), DateUtils.getDayNumT(48)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				list.add(cb.equal(r.get("order_status"), OrderStatusEnum.SUCCESSOrderStatus));
				if (query.getKeyword() == null) {
					list.add(cb.or(cb.equal(r.get("track_status"), 330), cb.equal(r.get("track_status"), 9003),
							cb.equal(r.get("track_status"), 9004), cb.equal(r.get("track_status"), 9006)));
				}
				if (query.getKeyword() != null && query.getKeyword().equals("1")) {
					list.add(cb.equal(r.get("track_status"), 330));
				}
				if (query.getKeyword() != null && query.getKeyword().equals("2")) {
					list.add(cb.equal(r.get("track_status"), 9003));
				}
				if (query.getKeyword() != null && query.getKeyword().equals("3")) {
					list.add(cb.equal(r.get("track_status"), 9004));
				}
				if (query.getKeyword() != null && query.getKeyword().equals("4")) {
					list.add(cb.equal(r.get("track_status"), 9006));
				}
				if (query.getDomain().getMalicious_tag() != null) {
					list.add(cb.or(cb.equal(r.get("receiver_phone"), query.getDomain().getMalicious_tag()),
							cb.equal(r.get("order_number"), query.getDomain().getMalicious_tag()),
							cb.equal(r.get("access_id_number"), query.getDomain().getMalicious_tag())));
				}
				list.add(cb.or(cb.like(r.get("malicious_tag"), "公安证件号码与证件姓名不匹配"),
						cb.like(r.get("malicious_tag"), "zop接入本地库校验失败")));
				list.add(cb.notEqual(r.get("order_source"), "标记订单"));

				Predicate pred = cb.and(list.toArray(new Predicate[0]));
				list.clear();
				try {
					list.add(cb.between(r.get("createtime"), DateUtils.getDayNumT(100000), DateUtils.getDayNumT(48)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				list.add(cb.equal(r.get("order_status"), OrderStatusEnum.SUCCESSOrderStatus));
				// list.add(cb.or(cb.equal(r.get("track_status"),
				// 330),cb.equal(r.get("track_status"), 9003),cb.equal(r.get("track_status"),
				// 9004)));

				if (query.getKeyword() == null) {
					list.add(cb.or(cb.equal(r.get("track_status"), 330), cb.equal(r.get("track_status"), 9003),
							cb.equal(r.get("track_status"), 9004), cb.equal(r.get("track_status"), 9006)));
				}
				if (query.getKeyword() != null && query.getKeyword().equals("1")) {
					list.add(cb.equal(r.get("track_status"), 330));
				}
				if (query.getKeyword() != null && query.getKeyword().equals("2")) {
					list.add(cb.equal(r.get("track_status"), 9003));
				}
				if (query.getKeyword() != null && query.getKeyword().equals("3")) {
					list.add(cb.equal(r.get("track_status"), 9004));
				}
				if (query.getKeyword() != null && query.getKeyword().equals("4")) {
					list.add(cb.equal(r.get("track_status"), 9006));
				}
				if (query.getDomain().getMalicious_tag() != null) {
					list.add(cb.or(cb.equal(r.get("receiver_phone"), query.getDomain().getMalicious_tag()),
							cb.equal(r.get("order_number"), query.getDomain().getMalicious_tag()),
							cb.equal(r.get("access_id_number"), query.getDomain().getMalicious_tag())));
				}
				list.add(cb.or(cb.like(r.get("malicious_tag"), "待确认地址"), cb.like(r.get("malicious_tag"), "恶意地址"),
						cb.like(r.get("malicious_tag"), "配送地址冲突"), cb.like(r.get("malicious_tag"), "联系地址全是数字")));
				try {
					cb.between(r.get("createtime"), DateUtils.getDayNum(100000), DateUtils.getDayNum(72));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				list.add(cb.notEqual(r.get("order_source"), "标记订单"));
				Predicate pred2 = cb.and(list.toArray(new Predicate[0]));
				return cb.or(pred, pred2);
			}
		};
		return this.coreOrderSerbice.findAll(spec, pageable);
	}

	public Object savaOrders(String userName, String userid, String address, String ordersource, String province_code,
			String province_name, String re_phone, String city, String cityName, String district, String districtName,
			String phone_Num, String smsstatus, String Productcode, String Productname,String user) {
			msgRes msg = new msgRes();
		try {
			if(coreOrderSerbice.chekOrder(re_phone)) {
				msg.setCode("203");
				msg.setStatus("203");
				msg.setMsg("该订单已办理，请勿重复办理");
				return JSON.toJSON(msg);
			}
			
			KplanPhoneNumber phoneNew = (KplanPhoneNumber) coreOrderSerbice.getById(phone_Num, KplanPhoneNumber.class);
			if(phoneNew==null) {
				KplanPhoneNumber p=new KplanPhoneNumber();
				p.setCity_code("810");
				p.setCread_date(new Date());
				p.setProvince_code("81");
				p.setProvince_name("四川");
				p.setCity_name("成都市");
				p.setBeautiful_num("官方号码");
				p.setRule_name("官方靓号");
				p.setRender_num(0);
				p.setRender_starindex(0);
				p.setLast_date(new Date());
				p.setUse_not(0);
				p.setId(phone_Num);
				coreOrderSerbice.add(p);
			}
			
			KplanPhoneNumber phone = (KplanPhoneNumber) coreOrderSerbice.getById(phone_Num, KplanPhoneNumber.class);
			
			
			if (phone != null) {
				if (phone.getUse_not() != 0) {
					msg.setCode("202");
					msg.setStatus("202");
					msg.setMsg("选择的订购号码已被使用，请重新选号");
					return JSON.toJSON(msg);
				} else {
					String phonesql = "update kplan_phone_number set use_not=2 where phone_num='" + re_phone
							+ "' and use_not=1";
					this.coreOrderSerbice.exeNative(phonesql);
					phone.setPhone_num(re_phone);
					phone.setUse_not(1);
					this.coreOrderSerbice.modify(phone);
					CoreOrdersMarketk k = new CoreOrdersMarketk();
					k.setReceiver_name(userName);
					k.setAccess_name(userName);
					k.setAccess_id_number(userid);
					k.setReceiver_address(address);
					if("贵州".equals(ordersource)) {
						k.setOrder_source("线下上门渠道-贵州");
					}
					if("成都".equals(ordersource)) {
						
							k.setOrder_source("线下上门渠道-四川");
					
					}
					if(StringUtils.trimToNull(user)!=null) {
						k.setOrder_source("交付上门渠道");
						k.setRecommend_name(user);
					}
				
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
				msg.setCode("201");
				msg.setStatus("201");
				msg.setMsg("选择的订购号码不存在");
				return JSON.toJSON(msg);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg.setCode("222");
			msg.setStatus("222");
			msg.setMsg("系统异常请联系管理员");
			return JSON.toJSON(msg);
		}
	}

	public Object savaNextOrders(String orderNo, String userName, String userid, String address, String ordersource,
			String province_code, String province_name, String re_phone, String city, String cityName, String district,
			String districtName, String phone_Num, String smsstatus, String Productcode, String Productname) {
		try {
			KplanPhoneNumber phone = (KplanPhoneNumber) coreOrderSerbice.getById(phone_Num, KplanPhoneNumber.class);
			if (phone != null) {
				if (phone.getUse_not() != 0) {
					msgRes msg = new msgRes();
					msg.setCode("202");
					msg.setStatus("202");
					msg.setMsg("选择的订购号码已被使用，请重新选号");
					return JSON.toJSON(msg);
				} else {
					String phonesql = "update kplan_phone_number set use_not=2 where phone_num='" + re_phone
							+ "' and use_not=1";
					this.coreOrderSerbice.exeNative(phonesql);
					phone.setPhone_num(re_phone);
					phone.setUse_not(1);
					this.coreOrderSerbice.modify(phone);
					CoreOrdersMarketk k;
					String sql = "from  CoreOrdersMarketk where id='" + orderNo + "'";
					List<CoreOrdersMarketk> l = this.coreOrderSerbice.getResultList(sql);
					if (l != null && l.size() > 0) {
						k = l.get(0);
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
					} else {
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

	public Object reSet(String orderNo, String phone) {
		String sql = "update core_orders_market_k set export_status=1,initial_status=20,order_status=0,visit_code=1,order_number='' where order_no='"
				+ orderNo + "' and order_status!=11";
		String phonesql = "update kplan_phone_number set use_not=2 where phone_num='" + phone + "'";

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

	public List<Kplanprocducts> qryProcDucts() {
		String sql = "from Kplanprocducts";
		List<Kplanprocducts> l = this.coreOrderSerbice.getResultList(sql);
		return l;
	}

	public Object addOrders(List<OrderRowModel> l, String proTag,String provicn) {
		msgRes msg = new msgRes();
		try {

			if (l != null && l.size() > 0) {
				for (OrderRowModel o : l) {
					UnicomPostCityCode c = this.unicomPostcityCodeManager.findById(o.getDistrictCode());
					if (c != null) {
						CoreOrdersMarketk k = new CoreOrdersMarketk();
						k.setReceiver_name(o.getUserName().trim());
						k.setAccess_name(o.getUserName().trim());
						k.setAccess_id_number(o.getUserId().trim());
						k.setReceiver_address(o.getAddress().trim());
						k.setOrder_source("线下上门渠道-"+provicn);
						k.setProvince_code(c.getProvince_code());
						k.setProvince_name(c.getProvince_name());
						k.setReceiver_phone(o.getPhone().trim());
						k.setCity_code(c.getCity_code());
						k.setCity_name(c.getCity_name());
						k.setDistrict_code(c.getDistrict_code());
						k.setDistrict_name(c.getDistrict_name());
						k.setInitial_status(20);
						if ("1".equals(proTag)) {
							k.setOrder_status(OrderStatusEnum.WAITPHONE);
							k.setExport_status(ExportStatusEnum.EXPORTSTATUS4);
						} else {
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

					} else {
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
			logger.error(e.getMessage(), e);
		}

		return JSON.toJSON(msg);
	}

	public CoreOrdersMarketk findById(String id) {
		String sql = "from  CoreOrdersMarketk where id='" + id + "'";
		List<CoreOrdersMarketk> l = this.coreOrderSerbice.getResultList(sql);
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	public Object procOrder(String orderNo, String userName, String userid, String address, String re_phone,
			String proctype, String province, String provinceCode, String city, String cityCode, String district,
			String districtCode, ManagerInfo managerInfo) {
		msgRes msg = new msgRes();
		CustomerServiceLog serviceLog=new CustomerServiceLog();
		CoreOrdersMarketk order;
		UnicomPostCityCode dir = null;
		try {
			if (StringUtils.trimToNull(districtCode) != null) {
				dir = this.unicomPostcityCodeManager.findById(districtCode);
				if (dir == null) {
					msg.setCode("223");
					msg.setStatus("223");
					msg.setMsg("获取区县地市编码出错！");
					return JSON.toJSON(msg);
				}
			}
			String sql = "from  CoreOrdersMarketk where id='" + orderNo + "'";
			List<CoreOrdersMarketk> l = this.coreOrderSerbice.getResultList(sql);
			CoreordersTrackLog log;
			if (l != null && l.size() > 0) {
				order = l.get(0);
				if ("1".equals(proctype)) {
					order.setTrack_status(9001);
					order.setTracktime(new Date());
					if (StringUtils.trimToNull(order.getFail_reasons()) != null) {
						order.setFail_reasons(order.getFail_reasons() + " "
								+ managerInfo.getBasicUserInfo().getUserRealName() + " 订单办理");
					} else {
						order.setFail_reasons(managerInfo.getBasicUserInfo().getUserRealName() + " 订单办理");
					}
					order.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.coreOrderSerbice.modify(order);
					CoreOrdersMarketk k = new CoreOrdersMarketk();
					k.setReceiver_name(userName);
					k.setAccess_name(userName);
					k.setAccess_id_number(userid);
					k.setReceiver_address(address);
					k.setOrder_source("标记订单");
					k.setProvince_code(dir.getProvince_code());
					k.setProvince_name(dir.getProvince_name());
					k.setReceiver_phone(re_phone);
					k.setCity_code(dir.getCity_code());
					k.setCity_name(dir.getCity_name());
					k.setDistrict_code(dir.getDistrict_code());
					k.setDistrict_name(dir.getDistrict_name());
					k.setInitial_status(20);
					k.setOrder_status(OrderStatusEnum.InitOrderStatus);
					k.setExport_status(ExportStatusEnum.EXPORTSTATUS1);
					k.setVisit_code(0);
					k.setCreatetime(new Date());
					k.setProduct_code(order.getProduct_code());
					k.setProduct_name(order.getProduct_name());
					k.setBusiness_type("K计划");
					k.setDifferent_nets(-1);
					k.setId("CQBACK" + order.getOrder_no());
					k.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.coreOrderSerbice.add(k);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9001");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					
					serviceLog.setBusiness_type("K计划");
					serviceLog.setCity_name(order.getCity_name());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getDistrict_name());
					serviceLog.setOperation_result("订单办理");
					serviceLog.setOperation_type("重庆恶意订单回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getReceiver_phone());
					serviceLog.setProduct_name(order.getProduct_name());
					serviceLog.setProvince_name(order.getProvince_name());
					serviceLog.setRemarks("CQ");
					this.customerServiceLogService.add(serviceLog);
					
					this.logService.add(log);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
				if ("2".equals(proctype)) {
					order.setTrack_status(9002);
					if (StringUtils.trimToNull(order.getFail_reasons()) != null) {
						order.setFail_reasons(order.getFail_reasons() + " "
								+ managerInfo.getBasicUserInfo().getUserRealName() + " 订单不办理");
					} else {
						order.setFail_reasons(managerInfo.getBasicUserInfo().getUserRealName() + " 订单不办理");
					}
					order.setTracktime(new Date());
					order.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.coreOrderSerbice.modify(order);
					msg.setCode("200");
					msg.setStatus("200");
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9002");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					serviceLog.setBusiness_type("K计划");
					serviceLog.setCity_name(order.getCity_name());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getDistrict_name());
					serviceLog.setOperation_result("订单不办理");
					serviceLog.setOperation_type("重庆恶意订单回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getReceiver_phone());
					serviceLog.setProduct_name(order.getProduct_name());
					serviceLog.setProvince_name(order.getProvince_name());
					serviceLog.setRemarks("CQ");
					this.customerServiceLogService.add(serviceLog);
					msg.setMsg("订单处理成功");
				}
				if ("3".equals(proctype)) {
					order.setTrack_status(9003);
					if (StringUtils.trimToNull(order.getFail_reasons()) != null) {
						order.setFail_reasons(order.getFail_reasons() + " "
								+ managerInfo.getBasicUserInfo().getUserRealName() + " 订单转运营");
					} else {
						order.setFail_reasons(managerInfo.getBasicUserInfo().getUserRealName() + " 订单转运营");
					}
					order.setTracktime(new Date());
					order.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.coreOrderSerbice.modify(order);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9003");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					serviceLog.setBusiness_type("K计划");
					serviceLog.setCity_name(order.getCity_name());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getDistrict_name());
					serviceLog.setOperation_result("订单转运营");
					serviceLog.setOperation_type("重庆恶意订单回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getReceiver_phone());
					serviceLog.setProduct_name(order.getProduct_name());
					serviceLog.setProvince_name(order.getProvince_name());
					serviceLog.setRemarks("CQ");
					this.customerServiceLogService.add(serviceLog);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
				if ("4".equals(proctype)) {
					order.setTrack_status(9004);
					order.setTracktime(new Date());
					order.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					if (StringUtils.trimToNull(order.getFail_reasons()) != null) {
						order.setFail_reasons(order.getFail_reasons() + " "
								+ managerInfo.getBasicUserInfo().getUserRealName() + " 订单转二次回访");
					} else {
						order.setFail_reasons(managerInfo.getBasicUserInfo().getUserRealName() + " 订单转二次回访");
					}
					this.coreOrderSerbice.modify(order);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9004");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					serviceLog.setBusiness_type("K计划");
					serviceLog.setCity_name(order.getCity_name());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getDistrict_name());
					serviceLog.setOperation_result("订单转二次回访");
					serviceLog.setOperation_type("重庆恶意订单回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getReceiver_phone());
					serviceLog.setProduct_name(order.getProduct_name());
					serviceLog.setProvince_name(order.getProvince_name());
					serviceLog.setRemarks("CQ");
					this.customerServiceLogService.add(serviceLog);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
				if ("5".equals(proctype)) {
					order.setTrack_status(9005);
					order.setTracktime(new Date());
					order.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					if (StringUtils.trimToNull(order.getFail_reasons()) != null) {
						order.setFail_reasons(order.getFail_reasons() + " "
								+ managerInfo.getBasicUserInfo().getUserRealName() + " 订单关闭");
					} else {
						order.setFail_reasons(managerInfo.getBasicUserInfo().getUserRealName() + " 订单关闭");
					}
					this.coreOrderSerbice.modify(order);

					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9005");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					serviceLog.setBusiness_type("K计划");
					serviceLog.setCity_name(order.getCity_name());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getDistrict_name());
					serviceLog.setOperation_result("订单关闭");
					serviceLog.setOperation_type("重庆恶意订单回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getReceiver_phone());
					serviceLog.setProduct_name(order.getProduct_name());
					serviceLog.setProvince_name(order.getProvince_name());
					serviceLog.setRemarks("CQ");
					this.customerServiceLogService.add(serviceLog);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
				if ("6".equals(proctype)) {
					order.setTracktime(new Date());
					order.setTrack_status(9006);
					order.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					if (StringUtils.trimToNull(order.getFail_reasons()) != null) {
						order.setFail_reasons(order.getFail_reasons() + " "
								+ managerInfo.getBasicUserInfo().getUserRealName() + " 订单转三次回访");
					} else {
						order.setFail_reasons(managerInfo.getBasicUserInfo().getUserRealName() + " 订单转三次回访");
					}
					this.coreOrderSerbice.modify(order);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9006");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					serviceLog.setBusiness_type("K计划");
					serviceLog.setCity_name(order.getCity_name());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getDistrict_name());
					serviceLog.setOperation_result("订单转三次回访");
					serviceLog.setOperation_type("重庆恶意订单回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getReceiver_phone());
					serviceLog.setProduct_name(order.getProduct_name());
					serviceLog.setProvince_name(order.getProvince_name());
					serviceLog.setRemarks("CQ");
					this.customerServiceLogService.add(serviceLog);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
			} else {
				msg.setCode("200");
				msg.setStatus("200");
				msg.setMsg("查询不到该订单");

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			msg.setCode("201");
			msg.setStatus("201");
			msg.setMsg("系统异常请联系管理员");
			return JSON.toJSON(msg);
		}
		return JSON.toJSON(msg);
	}

	public Object uploadChangeOrders(List<Object> data, kplanscordersQuery query) {
		msgRes msg = new msgRes();
		int proCount = 0;
		String sql = "";
		try {
			for (Object l : data) {
				List<String> list = (List<String>) l;
				if ("08-2278-2948-a0bg".equals(list.get(20))) {
					if ("成功关闭".equals(list.get(7))) {
						sql = "update core_orders_market_k set export_status=6 where mall_order_no='" + list.get(1)
								+ "'";
						this.coreOrderSerbice.exeNative(sql);
						proCount++;
					}
					if ("新订单中心退单".equals(list.get(7))) {
						sql = "update core_orders_market_k set export_status=11 where mall_order_no='" + list.get(1)
								+ "'";
						this.coreOrderSerbice.exeNative(sql);
						proCount++;
					}
				}
			}
			msg.setCode("200");
			msg.setStatus("200");
			msg.setMsg("文件上传成功,成功处理" + proCount + "笔订单");
			logger.info("文件上传成功,成功处理" + proCount + "笔订单");
		} catch (Exception e) {
			msg.setCode("999");
			msg.setStatus("999");
			msg.setMsg("系统异常，请稍后重试");
			return JSON.toJSON(msg);
		}
		return JSON.toJSON(msg);
	}

	/**
	 * @param query
	 * @return
	 */
	public List<CoreOrdersMarketk> qryExorDer(CoreOrdersMarketkQuery query) {
		if (query.getCreatedDateStart() != null && query.getCreatedDateEnd() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starDate = sdf.format(query.getCreatedDateStart());
			String endDate = sdf.format(query.getCreatedDateEnd());
//			String sql = "from CoreOrdersMarketk where "
//					+ "( ((malicious_tag LIKE'%公安证件号码与证件姓名不匹配%'  OR malicious_tag LIKE'%zop接入本地库校验失败%'   AND  "
//					+ "createtime< '"+DateUtils.getSevenDayT(query.getCreatedDateEnd(), 2)+"') AND tracktime >= '"+starDate+"'  "
//					+ "AND tracktime <= '"+endDate+"'  "
//					+ "AND to_char(createtime + '24 Hours', 'YYYY-MM-DD') < to_char(tracktime,  'YYYY-MM-DD')  )  "
//					+ "OR (( malicious_tag LIKE'%待确认地址%'  "
//					+ "OR malicious_tag LIKE'%恶意地址%'  "
//					+ "OR malicious_tag LIKE'%配送地址冲突%'  "
//					+ "OR malicious_tag LIKE'%联系地址全是数字%'   "
//					+ "AND createtime < '"+DateUtils.getSevenDayT(query.getCreatedDateEnd(), 3)+"' ) "
//					+ "AND tracktime >= '"+starDate+"'  "
//					+ "AND  tracktime <= '"+endDate+"'  "
//					+ "AND to_char(createtime + '24 Hours', 'YYYY-MM-DD') < to_char(tracktime, 'YYYY-MM-DD') )   )  "
//					+ "AND order_source <> '标记订单'";
			String sql = "from CoreOrdersMarketk where  malicious_tag is not null and order_source <> '标记订单'    and track_status!=330 AND tracktime >= '"+starDate+"'  " + 
					"AND  tracktime <= '"+endDate+"' AND to_char(createtime + '24 Hours', 'YYYY-MM-DD') < to_char(tracktime,  'YYYY-MM-DD')  ";
			logger.info(sql);
			return this.coreOrderSerbice.getResultList(sql);
		} else {
			String sql = "from CoreOrdersMarketk where  and malicious_tag is not null and ( track_status=9001 or track_status=9002 track_status=9003 or track_status=9004 or track_status=9005 or track_status=9006)";
			return this.coreOrderSerbice.getResultList(sql);
		}

	}

	public List<CoreOrdersMarketk> qryExorDers(@NotNull CoreOrdersMarketkQuery query) {
		Specification<CoreOrdersMarketk> spec = new Specification<CoreOrdersMarketk>() {
			public Predicate toPredicate(Root<CoreOrdersMarketk> r, CriteriaQuery<?> qr, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				try {
					list.add(cb.between(r.get("createtime"), DateUtils.getDayNumT(100000),
							DateUtils.getDayNumT(query.getCreatedDateEnd(), 48)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				list.add(cb.or(cb.like(r.get("malicious_tag"), "公安证件号码与证件姓名不匹配"),
						cb.like(r.get("malicious_tag"), "zop接入本地库校验失败")));

				Predicate pred = cb.and(list.toArray(new Predicate[0]));
				list.clear();
				try {
					list.add(cb.between(r.get("createtime"), DateUtils.getDayNumT(100000),
							DateUtils.getDayNumT(query.getCreatedDateEnd(), 72)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				list.add(cb.or(cb.like(r.get("malicious_tag"), "待确认地址"), cb.like(r.get("malicious_tag"), "恶意地址"),
						cb.like(r.get("malicious_tag"), "配送地址冲突"), cb.like(r.get("malicious_tag"), "联系地址全是数字")));
				try {
					cb.between(r.get("createtime"), DateUtils.getDayNum(100000), DateUtils.getDayNum(48));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Predicate pred2 = cb.and(list.toArray(new Predicate[0]));
				return cb.or(pred, pred2);
			}
		};
		return this.coreOrderSerbice.findAllList(spec);
	}
}
