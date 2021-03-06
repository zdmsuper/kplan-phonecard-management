package com.kplan.phonecard.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.kplan.phonecard.domain.CoreOrdersMarketk;
import com.kplan.phonecard.domain.CoreordersTrackLog;
import com.kplan.phonecard.domain.KplanPhoneNumber;
import com.kplan.phonecard.domain.KplanSecondaryOrders;
import com.kplan.phonecard.domain.Kplanprocducts;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.domain.OrderRowModel;
import com.kplan.phonecard.domain.UnicomPostCityCode;
import com.kplan.phonecard.domain.msgRes;
import com.kplan.phonecard.domain.entity.excelData;
import com.kplan.phonecard.domain.entity.excelMaliciTag;
import com.kplan.phonecard.domain.entity.excelMaliciousOrders;
import com.kplan.phonecard.domain.entity.excelOrder;
import com.kplan.phonecard.manager.CoreordersMarketkManager;
import com.kplan.phonecard.manager.KplanPhonenumBerManager;
import com.kplan.phonecard.manager.KplanSecondaryOrdersManager;
import com.kplan.phonecard.manager.UnicomPostcityCodeManager;
import com.kplan.phonecard.query.CoreOrdersMarketkQuery;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;
import com.kplan.phonecard.query.ManagerInfoQuery;
import com.kplan.phonecard.query.kplanscordersQuery;
import com.kplan.phonecard.service.CoreordersTrackLogService;
import com.kplan.phonecard.utils.DateUtils;
import com.kplan.phonecard.utils.PhoneRuleUtils;

@Controller
@RequestMapping("/malicious")
public class MaliciousOrderController extends AbstractBaseController {
	private static final Logger logger = LoggerFactory.getLogger(MaliciousOrderController.class);
	@Autowired
	KplanSecondaryOrdersManager kplanSecondaryOrdersManager;
	@Autowired
	UnicomPostcityCodeManager unicompostcityManager;
	@Autowired
	CoreordersMarketkManager coreOrdersManager;
	@Autowired
	KplanPhonenumBerManager kplanPhoneManager;
	@Autowired
	CoreordersTrackLogService logService;

	/**
	 * 成都恶意订单
	 * 
	 * @param map
	 * @param query
	 * @return
	 */
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
	 * @param remarks
	 * @param procDuctName
	 * @param phone_Num
	 * @param smsstatus
	 * @param ordersource
	 * @return
	 */
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
	 * @param remarks
	 * @param procDuctName
	 * @param phone_Num
	 * @param smsstatus
	 * @param ordersource
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Map<String, Object> map, KplanSecondaryOrdersQuery query) {
		String orderSouer="CD";
		if("5".equals(query.getDomain().getLogistics_info())) {
			orderSouer="CDOFFDITE";
		}
		Page<KplanSecondaryOrders> page = this.kplanSecondaryOrdersManager.malicicousList(query, this.getPageRequest(),
				orderSouer);
		map.put("page", page);
		map.put("query", query);
		return "malicious/list";
	}
	/**外围人员订单回访
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("/cdList")
	public String cdList(Map<String, Object> map, KplanSecondaryOrdersQuery query) {
		String orderSouer="CD";
		ManagerInfo managerInfo = super.getCurrentUserDetails().orElse(null);
		if("5".equals(query.getDomain().getLogistics_info())) {
			orderSouer="CDOFFDITE";
		}
		Page<KplanSecondaryOrders> page = this.kplanSecondaryOrdersManager.cdmalicicousList(query, this.getPageRequest(),
				orderSouer,managerInfo);
		map.put("page", page);
		map.put("query", query);
		return "malicious/cdlist";
	}
	/**外围客服人员领单跳转
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("/paper")
	public String paper(Map<String, Object> map, KplanSecondaryOrdersQuery query) {
		String res=this.kplanSecondaryOrdersManager.vitalOrderNum();
		map.put("res", res);
		return "malicious/paper";
	}
	@RequestMapping(value = "/paperNum", method = RequestMethod.POST)
	@ResponseBody
	public Object paperNum(Integer paperNum) {
		ManagerInfo managerInfo = super.getCurrentUserDetails().orElse(null);
		if(managerInfo!=null) {
			return this.kplanSecondaryOrdersManager.paperNum(paperNum, managerInfo);
		}else {
			msgRes msg = new msgRes();
			msg.setCode("200");
			msg.setStatus("200");
			msg.setMsg("登录失效，请重新登录 ");
			return JSON.toJSON(msg);
			
		}
		
	}

	@RequestMapping("/gzlist")
	public String gzlist(Map<String, Object> map, KplanSecondaryOrdersQuery query) {
		String orderSouer="GZ";
		if("5".equals(query.getDomain().getLogistics_info())) {
			orderSouer="GZOFFDITE";
		}
		Page<KplanSecondaryOrders> page = this.kplanSecondaryOrdersManager.malicicousList(query, this.getPageRequest(),
				orderSouer);
		map.put("page", page);
		map.put("query", query);
		return "malicious/gzlist";
	}

	@RequestMapping("/qryMaliciOrder")
	public String qryMaliciOrder(Map<String, Object> map, KplanSecondaryOrdersQuery query) {
		String procDuctCode = null;
		KplanSecondaryOrders order = this.kplanSecondaryOrdersManager.findById(query.getDomain().getId());
		UnicomPostCityCode address = this.unicompostcityManager.findByPrivoin(order.getPost_city(),
				order.getPost_district());
		List<UnicomPostCityCode> province = this.unicompostcityManager.findByPrivoin();
		List<Kplanprocducts> product = this.coreOrdersManager.qryProcDucts();
		if (product != null) {
			for (Kplanprocducts d : product) {
				if (d.getProcduct_name().equals(order.getProcduct_name())) {
					procDuctCode = d.getProcduct_code();
				}
			}
		}
		List<KplanPhoneNumber> phoneRuleList = this.kplanPhoneManager.findPhoneRuleList(procDuctCode,"成都");
		List<KplanPhoneNumber> phoneList = this.kplanPhoneManager.findPhoneList("", procDuctCode,
				address.getProvince_code());
		List<UnicomPostCityCode> city = null;
		List<UnicomPostCityCode> disr = null;
		if (address != null) {
			city = this.unicompostcityManager.findBycity(address.getProvince_code());
			disr = this.unicompostcityManager.qryDistrict(address.getCity_code());
		}
		if(order.getLock_status()==null||order.getLock_status()!=1) {
			map.put("proStatus", 2);
			this.kplanSecondaryOrdersManager.lockOrders(order.getId(), super.getCurrentUserDetails().orElse(null).getBasicUserInfo().getUserRealName());
			
		}else {
			if(StringUtils.trimToNull(order.getLock_user())!=null&&order.getLock_user().equals(super.getCurrentUserDetails().orElse(null).getBasicUserInfo().getUserRealName())) {
				map.put("proStatus", 2);
			}else {
			map.put("proStatus", 1);
			map.put("proMsg", "该订单已被:"+order.getLock_user()+" 锁定 不可处理");
			}
			
		}
		map.put("product", product);
		map.put("phoneRuleList", phoneRuleList);
		map.put("phoneList", phoneList);
		map.put("order", order);
		map.put("query", query);
		map.put("province", province);
		map.put("city", city);
		map.put("disr", disr);
		return "malicious/edit";

	}
	
	
	/**外围人员处理订单跳转
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("/qrycdMaliciOrder")
	public String qrycdMaliciOrder(Map<String, Object> map, KplanSecondaryOrdersQuery query) {
		String procDuctCode = null;
		KplanSecondaryOrders order = this.kplanSecondaryOrdersManager.findById(query.getDomain().getId());
		UnicomPostCityCode address = this.unicompostcityManager.findByPrivoin(order.getPost_city(),
				order.getPost_district());
		List<UnicomPostCityCode> province = this.unicompostcityManager.findByPrivoin();
		List<Kplanprocducts> product = this.coreOrdersManager.qryProcDucts();
		if (product != null) {
			for (Kplanprocducts d : product) {
				if (d.getProcduct_name().equals(order.getProcduct_name())) {
					procDuctCode = d.getProcduct_code();
				}
			}
		}
		List<KplanPhoneNumber> phoneRuleList = this.kplanPhoneManager.findPhoneRuleList(procDuctCode,"成都");
		List<KplanPhoneNumber> phoneList = this.kplanPhoneManager.findPhoneList("", procDuctCode,
				address.getProvince_code());
		List<UnicomPostCityCode> city = null;
		List<UnicomPostCityCode> disr = null;
		if (address != null) {
			city = this.unicompostcityManager.findBycity(address.getProvince_code());
			disr = this.unicompostcityManager.qryDistrict(address.getCity_code());
		}
		if(order.getLock_status()==null||order.getLock_status()!=1) {
			map.put("proStatus", 2);
			this.kplanSecondaryOrdersManager.lockOrders(order.getId(), super.getCurrentUserDetails().orElse(null).getBasicUserInfo().getUserRealName());
			
		}else {
			if(StringUtils.trimToNull(order.getLock_user())!=null&&order.getLock_user().equals(super.getCurrentUserDetails().orElse(null).getBasicUserInfo().getUserRealName())) {
				map.put("proStatus", 2);
			}else {
			map.put("proStatus", 1);
			map.put("proMsg", "该订单已被:"+order.getLock_user()+" 锁定  不可处理");
			}
			
		}
		map.put("product", product);
		map.put("phoneRuleList", phoneRuleList);
		map.put("phoneList", phoneList);
		map.put("order", order);
		map.put("query", query);
		map.put("province", province);
		map.put("city", city);
		map.put("disr", disr);
		return "malicious/cdedit";

	}
	

	@RequestMapping(value = "/procOrder", method = RequestMethod.POST)
	@ResponseBody
	public Object procOrder(String orderNo, String userName, String userid, String address, String re_phone,
			String proctype, String province, String provinceCode, String city, String cityCode, String district,
			String districtCode, String remarks, String procDuctName, String phone_Num, String smsstatus,
			String ordersource,String paddress) {
		ManagerInfo managerInfo = super.getCurrentUserDetails().orElse(null);
		logger.info(
				"orderNo:{},userName:{},userId:{},address:{},re_phone:{},proctype:{},province:{},provinceCode:{},city:{},cityCode:{},district:{},districtCode:{},remarks:{},procDuctName:{},phone_Num:{},smsstatus:{},ordersource:{},paddress{}",
				orderNo, userName,userid,address, re_phone,proctype,province,provinceCode,city,cityCode,district
				,districtCode,remarks,procDuctName,phone_Num,smsstatus,ordersource,paddress);
		return this.kplanSecondaryOrdersManager.procOrder(orderNo, userName, userid, address, re_phone, proctype,
				province, provinceCode, city, cityCode, district, districtCode, managerInfo, procDuctName, phone_Num,
				smsstatus, ordersource,remarks,paddress);
	}

	/**成都贵州恶意订单、物流订单导出
	 * @param response
	 * @param query
	 * @param ordersource
	 */
	@RequestMapping("/exMaliciOus")
	public void exMaliciOus(HttpServletResponse response, KplanSecondaryOrdersQuery query, String ordersource) {
		List<KplanSecondaryOrders> l = this.kplanSecondaryOrdersManager.exMaliciOus(query, ordersource);
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String fileName = date + "成都恶意订单数据报表";
		if ("GZ".equals(ordersource)) {
			fileName = date + "贵州恶意订单数据报表";
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel");
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
			List<excelMaliciousOrders> ex = new ArrayList<excelMaliciousOrders>();
			if (l != null && l.size() > 0) {
				excelMaliciousOrders e;
				for (KplanSecondaryOrders d : l) {
					e = new excelMaliciousOrders();
					e.setCity(d.getPost_city());
					e.setCreaDate(d.getPlace_order_time());
					e.setDir(d.getPost_district());
					e.setMaLiciousTag(d.getMalicious_order());
					e.setOperatorUser(d.getRemove_ident());
					e.setPhone(d.getPhone_num());
					e.setProDate(d.getProdate());
					e.setProDuctName(d.getProcduct_name());
					e.setProvince(d.getPost_province());
					e.setRemarks(d.getRemarks());
					e.setUserId(d.getUser_id());
					e.setUserName(d.getUser_name());
					e.setOrderStatus(d.getTrack_status().getDesc());
					e.setOperatorRemarks(d.getMalicious_info());
					e.setOrderType(d.getLogistics_info());
					ex.add(e);
				}
				if(ex.size()==0) {
					ex.add(new  excelMaliciousOrders());
				}
				OutputStream outputStream = response.getOutputStream();
				EasyExcel.write(outputStream, excelMaliciousOrders.class).excelType(ExcelTypeEnum.XLSX).sheet("成都恶意订单")
						.doWrite(ex);
				outputStream.flush();
				outputStream.close();
			}else {
				OutputStream outputStream = response.getOutputStream();
				EasyExcel.write(outputStream, excelMaliciousOrders.class).excelType(ExcelTypeEnum.XLSX).sheet("成都恶意订单")
						.doWrite(ex);
				outputStream.flush();
				outputStream.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**成都贵州查询被打标的回捞订单查询
	 * @return
	 */
	@RequestMapping("/qryMaliciTag")
	public String qryMaliciTag(Map<String, Object> map, CoreOrdersMarketkQuery query) {
		Page<CoreOrdersMarketk> page =this.coreOrdersManager.qryMaliciTag(query, this.getPageRequest());
		map.put("page", page);
		map.put("query", query);
		return "malicious/maliciTagList";
	}
	
	

	/**激活数据查询
	 * @return
	 */
	@RequestMapping("/qryDataTag")
	public String qryDataTag(Map<String, Object> map, KplanSecondaryOrdersQuery query) {
		Page<KplanSecondaryOrders> page =this.kplanSecondaryOrdersManager.qryDataTag(query, this.getPageRequest(),"CDDETAIL");
		map.put("page", page);
		map.put("query", query);
		return "malicious/qryDataTagList";
	}
	
	
	
	/**激活数据上传跳转页面
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("/uploadDataEdit")
	public String uploadDataEdit(Map<String, Object> map, ManagerInfoQuery query) {
		return "malicious/uploadDataEdit";
	}
	
	
	@RequestMapping(value = "/uploadDataFile", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadDataFile(@RequestParam("file") MultipartFile file,kplanscordersQuery query) throws IOException {
		InputStream inputStream = new BufferedInputStream(file.getInputStream());
		List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 1));
		return this.kplanSecondaryOrdersManager.uploadDataFile(data,query,super.getCurrentUserDetails().orElse(null));
	}
	
	/**数据激活数据下载
	 * @param response
	 * @param map
	 * @param query
	 */
	@RequestMapping("/exceData")
	public void exceData(HttpServletResponse response, Map<String, Object> map, KplanSecondaryOrdersQuery query) {
		List<KplanSecondaryOrders> l=this.kplanSecondaryOrdersManager.exceData(query);
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String fileName = date + "激活数据表";
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel");
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
			List<excelData> ex = new ArrayList<excelData>();
			CoreordersTrackLog log;
			excelData e;
			if (l != null && l.size() > 0) {
				for (KplanSecondaryOrders d : l) {
					e = new excelData();
					e.setOrderNo(d.getOrder_no());
					e.setUserName(d.getUser_name());
					e.setUserId(d.getUser_id());
					e.setLogisticsUserName(d.getLogistics_info());
					ex.add(e);
					
				}
				OutputStream outputStream = response.getOutputStream();
				EasyExcel.write(outputStream, excelData.class).excelType(ExcelTypeEnum.XLSX).sheet("激活数据订单")
						.doWrite(ex);
				outputStream.flush();
				outputStream.close();
			}else {
				OutputStream outputStream = response.getOutputStream();
				EasyExcel.write(outputStream, excelMaliciTag.class).excelType(ExcelTypeEnum.XLSX).sheet("恶意标签订单数据")
						.doWrite(ex);
				outputStream.flush();
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	
	
	/**恶意标签订单导出
	 * @param map
	 * @param query
	 */
	@RequestMapping("/exceMaliciTag")
	public void exceMaliciTag(HttpServletResponse response, Map<String, Object> map, CoreOrdersMarketkQuery query) {
		List<CoreOrdersMarketk> l=this.coreOrdersManager.exceMaliciTag(query);
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String fileName = date + "恶意标签订单数据报表";
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel");
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
			List<excelMaliciTag> ex = new ArrayList<excelMaliciTag>();
			CoreordersTrackLog log;
			excelMaliciTag e;
			if (l != null && l.size() > 0) {
				for (CoreOrdersMarketk d : l) {
					e = new excelMaliciTag();
					e.setMaliciTag(d.getMalicious_tag());
					e.setUnicomNo(d.getMall_order_no());
					if(d.getOrder_source().equals("线下上门渠道-四川") || d.getOrder_source().equals("线下上门渠道")) {
						e.setPrivicn("成都");
					}
					if(d.getOrder_source().equals("线下上门渠道-贵州") ) {
						e.setPrivicn("贵州");
					}
					if(!d.getOrder_source().contains("线下上门渠道")&&!d.getOrder_source().contains("交付上门")) {
						e.setPrivicn("成都K计划");
					}
					ex.add(e);
					this.coreOrdersManager.updateExportStatus(d.getId());
					log=new CoreordersTrackLog();
					log.setDelivery_order_no(d.getId());
					log.setOperator(super.getCurrentUserDetails().orElse(null).getBasicUserInfo().getUserRealName());
					log.setLog_info("338");
					log.setCreate_time(new Date());
					this.logService.insert(log);
					
				}
				if(ex.size()==0) {
					ex.add(new  excelMaliciTag());
				}
				OutputStream outputStream = response.getOutputStream();
				EasyExcel.write(outputStream, excelMaliciTag.class).excelType(ExcelTypeEnum.XLSX).sheet("恶意标签订单数据")
						.doWrite(ex);
				outputStream.flush();
				outputStream.close();
			}else {
				OutputStream outputStream = response.getOutputStream();
				EasyExcel.write(outputStream, excelMaliciTag.class).excelType(ExcelTypeEnum.XLSX).sheet("恶意标签订单数据")
						.doWrite(ex);
				outputStream.flush();
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	
	/**
	 * 解锁订单 1分钟执行一次，解锁锁定超过5分钟的订单
	 * @throws ParseException 
	 */
	@Scheduled(cron = "0 0/1 * * * ? ")
	public void unLockOrder() throws ParseException {
		this.kplanSecondaryOrdersManager.unLockOrder();
	}
}
