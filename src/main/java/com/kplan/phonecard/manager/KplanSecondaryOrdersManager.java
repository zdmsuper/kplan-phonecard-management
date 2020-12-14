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
import com.kplan.phonecard.domain.CoreordersTrackLog;
import com.kplan.phonecard.domain.CustomerServiceLog;
import com.kplan.phonecard.domain.KplanPhoneNumber;
import com.kplan.phonecard.domain.KplanSecondaryOrders;
import com.kplan.phonecard.domain.Kplanprocducts;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.domain.UnicomPostCityCode;
import com.kplan.phonecard.domain.msgRes;
import com.kplan.phonecard.domain.entity.BackTitle;
import com.kplan.phonecard.enums.ExportStatusEnum;
import com.kplan.phonecard.enums.KplanSeconDarytracStatusEnum;
import com.kplan.phonecard.enums.OrderStatusEnum;
import com.kplan.phonecard.enums.ProStatusEnum;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;
import com.kplan.phonecard.query.ManagerInfoQuery;
import com.kplan.phonecard.query.kplanscordersQuery;
import com.kplan.phonecard.service.CoreordersMarketkService;
import com.kplan.phonecard.service.CoreordersTrackLogService;
import com.kplan.phonecard.service.CustomerServiceLogService;
import com.kplan.phonecard.service.KplanSecondaryOrdersService;
import com.kplan.phonecard.service.KplanprocductService;
import com.kplan.phonecard.utils.DateUtils;
import com.kplan.phonecard.utils.SendSmsUtils;

import one.util.streamex.StreamEx;

@Component
@Transactional
public class KplanSecondaryOrdersManager extends BaseManager {
	private static final Logger logger = LoggerFactory.getLogger(KplanSecondaryOrdersManager.class);
	@Autowired
	CoreordersMarketkService coreOrderSerbice;
	@Autowired
	KplanSecondaryOrdersService kplanSecondaryOrdersService;
	@Autowired
	UnicomPostcityCodeManager unicomPostcityCodeManager;
	@Autowired
	KplanprocductsManager kplanprocductsManager;
	@Autowired
	KplanprocductService kplanprocductService;
	@Autowired
	CoreordersTrackLogService logService;
	@Autowired
	CustomerServiceLogService customerServiceLogService;

	public String upLoadorDers(List<Object> data, kplanscordersQuery query, ManagerInfo info,String logisticsinfo) {
		msgRes msg = new msgRes();
		String upLoadSqe=DateUtils.getTodayDate();
		KplanSecondaryOrders o;
		try {
			if (data != null && data.size() > 0) {
				for (Object l : data) {
					List<String> list = (List<String>) l;
					o = new KplanSecondaryOrders();
					o.setPlace_order_time(new Date());
					o.setOrder_no(list.get(0));
					o.setPro_status(ProStatusEnum.CREADORDER);
					o.setOrder_source(query.getKeyword());
					o.setTrack_status(KplanSeconDarytracStatusEnum.INISTATUS);
					o.setLogistics_info(logisticsinfo);
					if (info != null) {
						o.setOperator(info.getBasicUserInfo().getUserRealName() +upLoadSqe );
					}
					this.kplanSecondaryOrdersService.add(o);
				}
				msg.setCode("200");
				msg.setStatus("200");
				msg.setMsg("文件上传成功");
				return JSON.toJSONString(msg);
			} else {
				msg.setCode("201");
				msg.setStatus("201");
				msg.setMsg("文件上传时间文件数据为空");
				return JSON.toJSONString(msg);
			}
		} catch (Exception e) {
			msg.setCode("999");
			msg.setStatus("999");
			msg.setMsg("系统异常，请稍后重试");
			return JSON.toJSONString(msg);
		}

	}

	public Page<KplanSecondaryOrders> qrySeconadryorDer(@NotNull KplanSecondaryOrdersQuery query, Pageable pageable) {
		Specification<KplanSecondaryOrders> spec = new Specification<KplanSecondaryOrders>() {
			@Override
			public Predicate toPredicate(Root<KplanSecondaryOrders> r, CriteriaQuery<?> qr, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (query.getCreatedDateStart() != null && query.getCreatedDateEnd() != null) {
					list.add(cb.between(r.get("place_order_time"), query.getCreatedDateStart(),
							query.getCreatedDateEnd()));
				}
				if (query.getKeyword() != null) {
					list.add(cb.equal(r.get("phone_num"), query.getKeyword()));
				}
				if (query.getDomain().getOperator() != null) {
					list.add(cb.equal(r.get("operator"), query.getDomain().getOperator()));
				}
				if (query.getDomain().getOrder_source() != null) {
					list.add(cb.equal(r.get("order_source"), query.getDomain().getOrder_source()));
				}
				if (query.getDomain().getPro_status() != null) {
					list.add(cb.equal(r.get("pro_status"), query.getDomain().getPro_status()));
				}
//				list.add(cb.or(cb.equal(r.get("pro_status"), 1),cb.equal(r.get("pro_status"), 99)));
//				list.add(cb.or(cb.equal(r.get("pro_status"), 99)));
//				list.add(cb.and());
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return this.kplanSecondaryOrdersService.findAll(spec, pageable);
	}

	public Page<KplanSecondaryOrders> malicicousList(@NotNull KplanSecondaryOrdersQuery query, Pageable pageable,String orderSource) {
		Specification<KplanSecondaryOrders> spec = new Specification<KplanSecondaryOrders>() {
			@Override
			public Predicate toPredicate(Root<KplanSecondaryOrders> r, CriteriaQuery<?> qr, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (query.getCreatedDateStart() != null && query.getCreatedDateEnd() != null) {
					list.add(cb.between(r.get("place_order_time"), query.getCreatedDateStart(),
							query.getCreatedDateEnd()));
				}
				if(query.getDomain().getPhone_num()!=null) {
					list.add(cb.or(cb.equal(r.get("phone_num"), query.getDomain().getPhone_num()),cb.equal(r.get("phone"), query.getDomain().getPhone_num())));
				}else {
					if(query.getKeyword()!=null) {
						if(query.getKeyword().equals("1")) {
							list.add(cb.equal(r.get("track_status"), KplanSeconDarytracStatusEnum.WAITSTATUS));
						}
						if(query.getKeyword().equals("2")) {
							list.add(cb.equal(r.get("track_status"), KplanSeconDarytracStatusEnum.SECONDVISITSTATUS));
						}
						if(query.getKeyword().equals("3")) {
							list.add(cb.equal(r.get("track_status"), KplanSeconDarytracStatusEnum.THREEVISITSTATUS));
						}
					}
					if(query.getDomain().getLogistics_info()!=null) {
						if(query.getDomain().getLogistics_info().equals("1")) {
//							list.add(cb.equal(r.get("track_status"), KplanSeconDarytracStatusEnum.WAITSTATUS));
							list.add(cb.or(cb.isNull(r.get("logistics_info")),cb.notEqual(r.get("logistics_info"), "物流订单")));
						}
						if(query.getDomain().getLogistics_info().equals("4")) {
							list.add(cb.equal(r.get("logistics_info"), "物流订单"));
						}
					}
						
						list.add(cb.or(cb.equal(r.get("track_status"), KplanSeconDarytracStatusEnum.WAITSTATUS),
								cb.equal(r.get("track_status"), KplanSeconDarytracStatusEnum.SECONDVISITSTATUS),
								cb.equal(r.get("track_status"), KplanSeconDarytracStatusEnum.THREEVISITSTATUS),
								cb.equal(r.get("track_status"), KplanSeconDarytracStatusEnum.TRANSFERTOOPERATION)));
				}
				list.add(cb.equal(r.get("order_source"), orderSource));
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return this.kplanSecondaryOrdersService.findAll(spec, pageable);
	}

	public List<BackTitle> qryTitle(Date date, Date date2) {
		String sql = "select operator from kplan_secondary_orders where pro_date>='" + date + "' and  pro_date<='"
				+ date2 + "' group by operator ";
		List<Object> l = this.kplanSecondaryOrdersService.getNativeResultList(sql);
		List<BackTitle> resultList = StreamEx.of(l).map(r -> {
			BackTitle b = new BackTitle();
			b.setExportFileTitle(r.toString());
			return b;
		}).toList();
		return resultList;

	}

	public List<KplanSecondaryOrders> exExcel(String operator) {
		String sql = "from KplanSecondaryOrders where operator='" + operator + "'";
		return this.kplanSecondaryOrdersService.getResultList(sql);
	}

	public KplanSecondaryOrders findById(Integer id) {
		return (KplanSecondaryOrders) this.kplanSecondaryOrdersService.getById(id, KplanSecondaryOrders.class);
	}

	/**
	 * @param orderNo
	 * @param userName
	 * @param userid
	 * @param address
	 * @param re_phone
	 * @param proctype
	 * @param province
	 * @param provinceCode
	 * @param city
	 * @param cityCode
	 * @param district
	 * @param districtCode
	 * @param managerInfo
	 * @param pocDuctName
	 * @param phone_Num
	 * @param smsstatus
	 * @param ordersource
	 * @return
	 */
	public Object procOrder(String orderNo, String userName, String userid, String address, String re_phone,
			String proctype, String province, String provinceCode, String city, String cityCode, String district,
			String districtCode, ManagerInfo managerInfo, String pocDuctName,String phone_Num,String smsstatus,
			String ordersource,String remarks) {
		msgRes msg = new msgRes();
		CustomerServiceLog serviceLog=new CustomerServiceLog();
		KplanSecondaryOrders order;
		UnicomPostCityCode dir = null;
		Kplanprocducts pocDuct = null;
		if(coreOrderSerbice.chekOrder(re_phone)) {
			msg.setCode("203");
			msg.setStatus("203");
			msg.setMsg("该订单已办理，请勿重复办理");
			return JSON.toJSON(msg);
		}
		
		if(StringUtils.trimToNull(phone_Num)!=null) {
		KplanPhoneNumber phone = (KplanPhoneNumber) coreOrderSerbice.getById(phone_Num, KplanPhoneNumber.class);
		if(phone!=null) {
			if (phone.getUse_not() != 0) {
				msg.setCode("202");
				msg.setStatus("202");
				msg.setMsg("选择的订购号码已被使用，请重新选号");
				return JSON.toJSON(msg);
			}else {
				String phonesql = "update kplan_phone_number set use_not=1 ,phone_num='"+re_phone+"' where phone='" + phone_Num
						+ "' ";
				this.coreOrderSerbice.exeNative(phonesql);
			}
		}
		}
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
			pocDuct = this.kplanprocductsManager.qryProcduct(pocDuctName);
			if (pocDuct == null) {
				msg.setCode("224");
				msg.setStatus("224");
				msg.setMsg("查找不到产品编码");
				return JSON.toJSON(msg);
			}

			String sql = "from  KplanSecondaryOrders where id=" + Integer.parseInt(orderNo) + "";
			List<KplanSecondaryOrders> l = this.kplanSecondaryOrdersService.getResultList(sql);
			CoreordersTrackLog log;
			if (l != null && l.size() > 0) {
				order = l.get(0);
				if ("1".equals(proctype)) {
					order.setTrack_status(KplanSeconDarytracStatusEnum.HANDLESTATUS);
					order.setProdate(new Date());
					if (StringUtils.trimToNull(order.getRemarks()) != null) {
						order.setRemarks(
								order.getRemarks() + " " + managerInfo.getBasicUserInfo().getUserRealName() + " 订单办理");
					} else {
						order.setRemarks(managerInfo.getBasicUserInfo().getUserRealName() + " 订单办理");
					}
					if(StringUtils.trimToNull(order.getMalicious_info())!=null) {
						order.setMalicious_info(order.getMalicious_info()+","+remarks);
					}else {
						order.setMalicious_info(remarks);
					}
					order.setRemove_ident(managerInfo.getBasicUserInfo().getUserRealName());
					this.kplanSecondaryOrdersService.modify(order);
					CoreOrdersMarketk k = new CoreOrdersMarketk();
					if(phone_Num!=null) {
						k.setOrder_number(phone_Num);
					}
					k.setReceiver_name(userName);
					k.setAccess_name(userName);
					k.setAccess_id_number(userid);
					k.setReceiver_address(address);
					if("GZ".equals(ordersource)) {
						k.setOrder_source("线下上门渠道-贵州");
					}if("CD".equals(ordersource)) {
						k.setOrder_source("线下上门渠道-四川");
					}
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
					k.setProduct_code(pocDuct.getProcduct_code());
					k.setProduct_name(pocDuct.getProcduct_name());
					k.setBusiness_type("K计划");
					k.setDifferent_nets(-1);
					k.setId("CDBACK" + order.getOrder_no());
					k.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.coreOrderSerbice.add(k);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9001");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					
					
					//钉钉日志
					serviceLog.setBusiness_type("交付订单");
					serviceLog.setCity_name(order.getPost_city());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getPost_district());
					serviceLog.setOperation_result("订单办理");
					serviceLog.setOperation_type("回捞用户回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getPhone_num());
					serviceLog.setProduct_name(order.getProcduct_name());
					serviceLog.setProvince_name(order.getPost_province());
					serviceLog.setRemarks(order.getOrder_source());
					this.customerServiceLogService.add(serviceLog);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
				if ("2".equals(proctype)) {
					order.setTrack_status(KplanSeconDarytracStatusEnum.FAILEDSTATUS);
					if (StringUtils.trimToNull(order.getRemarks()) != null) {
						order.setRemarks(
								order.getRemarks() + " " + managerInfo.getBasicUserInfo().getUserRealName() + " 订单不办理");
					} else {
						order.setRemarks(managerInfo.getBasicUserInfo().getUserRealName() + " 订单不办理");
					}
					if(StringUtils.trimToNull(order.getMalicious_info())!=null) {
						order.setMalicious_info(order.getMalicious_info()+","+remarks);
					}else {
						order.setMalicious_info(remarks);
					}
					order.setProdate(new Date());
					order.setRemove_ident(managerInfo.getBasicUserInfo().getUserRealName());
					this.coreOrderSerbice.modify(order);
					msg.setCode("200");
					msg.setStatus("200");
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9002");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					
					serviceLog.setBusiness_type("交付订单");
					serviceLog.setCity_name(order.getPost_city());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getPost_district());
					serviceLog.setOperation_result(KplanSeconDarytracStatusEnum.FAILEDSTATUS.getDesc());
					serviceLog.setOperation_type("回捞用户回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getPhone_num());
					serviceLog.setProduct_name(order.getProcduct_name());
					serviceLog.setProvince_name(order.getPost_province());
					serviceLog.setRemarks(order.getOrder_source());
					this.customerServiceLogService.add(serviceLog);
					
					msg.setMsg("订单处理成功");
					
					
				}
				if ("3".equals(proctype)) {
					order.setTrack_status(KplanSeconDarytracStatusEnum.TRANSFERTOOPERATION);
					if (StringUtils.trimToNull(order.getRemarks()) != null) {
						order.setRemarks(
								order.getRemarks() + " " + managerInfo.getBasicUserInfo().getUserRealName() + " 订单转运营");
					} else {
						order.setRemarks(managerInfo.getBasicUserInfo().getUserRealName() + " 订单转运营");
					}
					if(StringUtils.trimToNull(order.getMalicious_info())!=null) {
						order.setMalicious_info(order.getMalicious_info()+","+remarks);
					}else {
						order.setMalicious_info(remarks);
					}
					order.setProdate(new Date());
					order.setRemove_ident(managerInfo.getBasicUserInfo().getUserRealName());
					this.coreOrderSerbice.modify(order);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9003");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					
					serviceLog.setBusiness_type("交付订单");
					serviceLog.setCity_name(order.getPost_city());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getPost_district());
					serviceLog.setOperation_result(KplanSeconDarytracStatusEnum.TRANSFERTOOPERATION.getDesc());
					serviceLog.setOperation_type("回捞用户回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getPhone_num());
					serviceLog.setProduct_name(order.getProcduct_name());
					serviceLog.setProvince_name(order.getPost_province());
					serviceLog.setRemarks(order.getOrder_source());
					this.customerServiceLogService.add(serviceLog);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
				if ("4".equals(proctype)) {
					order.setTrack_status(KplanSeconDarytracStatusEnum.SECONDVISITSTATUS);
					order.setProdate(new Date());
					order.setRemove_ident(managerInfo.getBasicUserInfo().getUserRealName());
					if (StringUtils.trimToNull(order.getRemarks()) != null) {
						order.setRemarks(order.getRemarks() + " " + managerInfo.getBasicUserInfo().getUserRealName()
								+ " 订单转二次回访");
					} else {
						order.setRemarks(managerInfo.getBasicUserInfo().getUserRealName() + " 订单转二次回访");
					}
					if(StringUtils.trimToNull(order.getMalicious_info())!=null) {
						order.setMalicious_info(order.getMalicious_info()+","+remarks);
					}else {
						order.setMalicious_info(remarks);
					}
					this.coreOrderSerbice.modify(order);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9004");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					
					
					serviceLog.setBusiness_type("交付订单");
					serviceLog.setCity_name(order.getPost_city());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getPost_district());
					serviceLog.setOperation_result(KplanSeconDarytracStatusEnum.SECONDVISITSTATUS.getDesc());
					serviceLog.setOperation_type("回捞用户回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getPhone_num());
					serviceLog.setProduct_name(order.getProcduct_name());
					serviceLog.setProvince_name(order.getPost_province());
					serviceLog.setRemarks(order.getOrder_source());
					this.customerServiceLogService.add(serviceLog);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
				if ("5".equals(proctype)) {
					order.setTrack_status(KplanSeconDarytracStatusEnum.CLOSESTATUS);
					order.setProdate(new Date());
					order.setRemove_ident(managerInfo.getBasicUserInfo().getUserRealName());
					if (StringUtils.trimToNull(order.getRemarks()) != null) {
						order.setRemarks(
								order.getRemarks() + " " + managerInfo.getBasicUserInfo().getUserRealName() + " 订单关闭");
					} else {
						order.setRemarks(managerInfo.getBasicUserInfo().getUserRealName() + " 订单关闭");
					}
					if(StringUtils.trimToNull(order.getMalicious_info())!=null) {
						order.setMalicious_info(order.getMalicious_info()+","+remarks);
					}else {
						order.setMalicious_info(remarks);
					}
					this.coreOrderSerbice.modify(order);

					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9005");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					
					serviceLog.setBusiness_type("交付订单");
					serviceLog.setCity_name(order.getPost_city());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getPost_district());
					serviceLog.setOperation_result(KplanSeconDarytracStatusEnum.CLOSESTATUS.getDesc());
					serviceLog.setOperation_type("回捞用户回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getPhone_num());
					serviceLog.setProduct_name(order.getProcduct_name());
					serviceLog.setProvince_name(order.getPost_province());
					serviceLog.setRemarks(order.getOrder_source());
					this.customerServiceLogService.add(serviceLog);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
				if ("6".equals(proctype)) {
					order.setProdate(new Date());
					order.setTrack_status(KplanSeconDarytracStatusEnum.THREEVISITSTATUS);
					order.setRemove_ident(managerInfo.getBasicUserInfo().getUserRealName());
					if (StringUtils.trimToNull(order.getRemarks()) != null) {
						order.setRemarks(order.getRemarks() + " " + managerInfo.getBasicUserInfo().getUserRealName()
								+ " 订单转三次联系");
					} else {
						order.setRemarks(managerInfo.getBasicUserInfo().getUserRealName() + " 订单转三次联系");
					}
					if(StringUtils.trimToNull(order.getMalicious_info())!=null) {
						order.setMalicious_info(order.getMalicious_info()+","+remarks);
					}else {
						order.setMalicious_info(remarks);
					}
					this.coreOrderSerbice.modify(order);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9006");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					
					serviceLog.setBusiness_type("交付订单");
					serviceLog.setCity_name(order.getPost_city());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getPost_district());
					serviceLog.setOperation_result(KplanSeconDarytracStatusEnum.THREEVISITSTATUS.getDesc());
					serviceLog.setOperation_type("回捞用户回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getPhone_num());
					serviceLog.setProduct_name(order.getProcduct_name());
					serviceLog.setProvince_name(order.getPost_province());
					serviceLog.setRemarks(order.getOrder_source());
					this.customerServiceLogService.add(serviceLog);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
				
				if ("7".equals(proctype)) {
					order.setProdate(new Date());
					order.setTrack_status(KplanSeconDarytracStatusEnum.NOTPHONEEND);
					order.setRemove_ident(managerInfo.getBasicUserInfo().getUserRealName());
					if (StringUtils.trimToNull(order.getRemarks()) != null) {
						order.setRemarks(order.getRemarks() + " " + managerInfo.getBasicUserInfo().getUserRealName()
								+ " 订单多次联系不上");
					} else {
						order.setRemarks(managerInfo.getBasicUserInfo().getUserRealName() + " 订单多次联系不上");
					}
					if(StringUtils.trimToNull(order.getMalicious_info())!=null) {
						order.setMalicious_info(order.getMalicious_info()+","+remarks);
					}else {
						order.setMalicious_info(remarks);
					}
					this.coreOrderSerbice.modify(order);
					SendSmsUtils.senMsg(re_phone, userid, userName);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9007");//多次联系不上
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					
					serviceLog.setBusiness_type("交付订单");
					serviceLog.setCity_name(order.getPost_city());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getPost_district());
					serviceLog.setOperation_result(KplanSeconDarytracStatusEnum.NOTPHONEEND.getDesc());
					serviceLog.setOperation_type("回捞用户回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getPhone_num());
					serviceLog.setProduct_name(order.getProcduct_name());
					serviceLog.setProvince_name(order.getPost_province());
					serviceLog.setRemarks(order.getOrder_source());
					this.customerServiceLogService.add(serviceLog);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
				if ("8".equals(proctype)) {
					order.setProdate(new Date());
					order.setTrack_status(KplanSeconDarytracStatusEnum.NOREIVITI);
					order.setRemove_ident(managerInfo.getBasicUserInfo().getUserRealName());
					if (StringUtils.trimToNull(order.getRemarks()) != null) {
						order.setRemarks(order.getRemarks() + " " + managerInfo.getBasicUserInfo().getUserRealName()
								+ " 订单不符合回访");
					} else {
						order.setRemarks(managerInfo.getBasicUserInfo().getUserRealName() + " 订单不符合回访");
					}
					if(StringUtils.trimToNull(order.getMalicious_info())!=null) {
						order.setMalicious_info(order.getMalicious_info()+","+remarks);
					}else {
						order.setMalicious_info(remarks);
					}
					this.coreOrderSerbice.modify(order);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9008");//不符合回访
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					
					serviceLog.setBusiness_type("交付订单");
					serviceLog.setCity_name(order.getPost_city());
					serviceLog.setCreate_time(new Date());
					serviceLog.setDistrict_name(order.getPost_district());
					serviceLog.setOperation_result(KplanSeconDarytracStatusEnum.NOREIVITI.getDesc());
					serviceLog.setOperation_type("回捞用户回访");
					serviceLog.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					serviceLog.setOrder_no(order.getOrder_no());
					serviceLog.setPhone(order.getPhone_num());
					serviceLog.setProduct_name(order.getProcduct_name());
					serviceLog.setProvince_name(order.getPost_province());
					serviceLog.setRemarks(order.getOrder_source());
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
	
	public List<KplanSecondaryOrders> exMaliciOus(KplanSecondaryOrdersQuery query,String ordersource){
		String sql="from KplanSecondaryOrders where place_order_time>='"+query.getCreatedDateStart()+"' and place_order_time<='"+query.getCreatedDateEnd()+"' and track_status!=2  and order_source='"+ordersource+"'";
		List<KplanSecondaryOrders> l=this.kplanSecondaryOrdersService.getResultList(sql);
		return l;
	}

}
