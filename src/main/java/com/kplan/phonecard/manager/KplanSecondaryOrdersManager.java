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
import com.kplan.phonecard.service.KplanSecondaryOrdersService;
import com.kplan.phonecard.service.KplanprocductService;
import com.kplan.phonecard.utils.DateUtils;

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

	public String upLoadorDers(List<Object> data, kplanscordersQuery query, ManagerInfo info) {
		msgRes msg = new msgRes();
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
					if (info != null) {
						o.setOperator(info.getBasicUserInfo().getUserRealName() + DateUtils.getTodayDate());
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

	public Page<KplanSecondaryOrders> malicicousList(@NotNull KplanSecondaryOrdersQuery query, Pageable pageable) {
		Specification<KplanSecondaryOrders> spec = new Specification<KplanSecondaryOrders>() {
			@Override
			public Predicate toPredicate(Root<KplanSecondaryOrders> r, CriteriaQuery<?> qr, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (query.getCreatedDateStart() != null && query.getCreatedDateEnd() != null) {
					list.add(cb.between(r.get("place_order_time"), query.getCreatedDateStart(),
							query.getCreatedDateEnd()));
				}
//				if(query.getKeyword()!=null) {
//					list.add(cb.equal(r.get("phone_num"), query.getKeyword()));
//				}
				list.add(cb.or(cb.equal(r.get("track_status"), KplanSeconDarytracStatusEnum.WAITSTATUS),
						cb.equal(r.get("track_status"), KplanSeconDarytracStatusEnum.SECONDVISITSTATUS),
						cb.equal(r.get("track_status"), KplanSeconDarytracStatusEnum.THREEVISITSTATUS),
						cb.equal(r.get("track_status"), KplanSeconDarytracStatusEnum.TRANSFERTOOPERATION)));
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

	public Object procOrder(String orderNo, String userName, String userid, String address, String re_phone,
			String proctype, String province, String provinceCode, String city, String cityCode, String district,
			String districtCode, ManagerInfo managerInfo, String pocDuctName) {
		msgRes msg = new msgRes();
		KplanSecondaryOrders order;
		UnicomPostCityCode dir = null;
		Kplanprocducts pocDuct = null;
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

			String sql = "from  CoreOrdersMarketk where id='" + orderNo + "'";
			List<KplanSecondaryOrders> l = this.kplanSecondaryOrdersService.getResultList(sql);
			CoreordersTrackLog log;
			if (l != null && l.size() > 0) {
				order = l.get(0);
				if ("1".equals(proctype)) {
					order.setTrack_status(KplanSeconDarytracStatusEnum.HANDLESTATUS);
					order.setPro_date(new Date());
					if (StringUtils.trimToNull(order.getRemarks()) != null) {
						order.setRemarks(
								order.getRemarks() + " " + managerInfo.getBasicUserInfo().getUserRealName() + " 订单办理");
					} else {
						order.setRemarks(managerInfo.getBasicUserInfo().getUserRealName() + " 订单办理");
					}
					order.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.kplanSecondaryOrdersService.modify(order);
					CoreOrdersMarketk k = new CoreOrdersMarketk();
					k.setReceiver_name(userName);
					k.setAccess_name(userName);
					k.setAccess_id_number(userid);
					k.setReceiver_address(address);
					k.setOrder_source("线下上门渠道");
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
					k.setId("CQBACK" + order.getOrder_no());
					k.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.coreOrderSerbice.add(k);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9001");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
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
					order.setPro_date(new Date());
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
					order.setPro_date(new Date());
					order.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.coreOrderSerbice.modify(order);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9003");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
				if ("4".equals(proctype)) {
					order.setTrack_status(KplanSeconDarytracStatusEnum.SECONDVISITSTATUS);
					order.setPro_date(new Date());
					order.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					if (StringUtils.trimToNull(order.getRemarks()) != null) {
						order.setRemarks(order.getRemarks() + " " + managerInfo.getBasicUserInfo().getUserRealName()
								+ " 订单转二次回访");
					} else {
						order.setRemarks(managerInfo.getBasicUserInfo().getUserRealName() + " 订单转二次回访");
					}
					this.coreOrderSerbice.modify(order);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9004");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
				if ("5".equals(proctype)) {
					order.setTrack_status(KplanSeconDarytracStatusEnum.CLOSESTATUS);
					order.setPro_date(new Date());
					order.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					if (StringUtils.trimToNull(order.getRemarks()) != null) {
						order.setRemarks(
								order.getRemarks() + " " + managerInfo.getBasicUserInfo().getUserRealName() + " 订单关闭");
					} else {
						order.setRemarks(managerInfo.getBasicUserInfo().getUserRealName() + " 订单关闭");
					}
					this.coreOrderSerbice.modify(order);

					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9005");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
					msg.setCode("200");
					msg.setStatus("200");
					msg.setMsg("订单处理成功");
				}
				if ("6".equals(proctype)) {
					order.setPro_date(new Date());
					order.setTrack_status(KplanSeconDarytracStatusEnum.THREEVISITSTATUS);
					order.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					if (StringUtils.trimToNull(order.getRemarks()) != null) {
						order.setRemarks(order.getRemarks() + " " + managerInfo.getBasicUserInfo().getUserRealName()
								+ " 订单转三次联系");
					} else {
						order.setRemarks(managerInfo.getBasicUserInfo().getUserRealName() + " 订单转三次联系");
					}
					this.coreOrderSerbice.modify(order);
					log = new CoreordersTrackLog();
					log.setDelivery_order_no(order.getOrder_no());
					log.setCreate_time(new Date());
					log.setLog_info("9006");
					log.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
					this.logService.add(log);
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

}
