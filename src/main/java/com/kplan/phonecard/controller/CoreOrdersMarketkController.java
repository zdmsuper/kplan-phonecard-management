package com.kplan.phonecard.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.kplan.phonecard.domain.BasicUserInfo;
import com.kplan.phonecard.domain.CoreOrdersMarketk;
import com.kplan.phonecard.domain.KplanChannelNumberDetail;
import com.kplan.phonecard.domain.KplanPhoneNumber;
import com.kplan.phonecard.domain.KplanSecondaryOrders;
import com.kplan.phonecard.domain.Kplanprocducts;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.domain.OrderRowModel;
import com.kplan.phonecard.domain.UnicomPostCityCode;
import com.kplan.phonecard.domain.kplanscorders;
import com.kplan.phonecard.domain.entity.BackTitle;
import com.kplan.phonecard.domain.entity.excelBackOrder;
import com.kplan.phonecard.domain.entity.excelOrder;
import com.kplan.phonecard.manager.ManagerInfoManager;
import com.kplan.phonecard.manager.CoreordersMarketkManager;
import com.kplan.phonecard.manager.KplanChannelNumberDetailManager;
import com.kplan.phonecard.manager.KplanPhonenumBerManager;
import com.kplan.phonecard.manager.KplanSecondaryOrdersManager;
import com.kplan.phonecard.manager.UnicomPostcityCodeManager;
import com.kplan.phonecard.manager.kplanscordersManager;
import com.kplan.phonecard.query.ManagerInfoQuery;
import com.kplan.phonecard.query.UnicomPostCityCodeQuery;
import com.kplan.phonecard.query.kplanscordersQuery;
import com.kplan.phonecard.query.CoreOrdersMarketkQuery;
import com.kplan.phonecard.query.KplanChannelNumberDetailQuery;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;
import com.kplan.phonecard.service.CoreordersMarketkService;
import com.kplan.phonecard.utils.DateUtils;
import com.kplan.phonecard.utils.ExcelUtil;
import com.kplan.phonecard.utils.PhoneRuleUtils;

@Controller
@RequestMapping("/coreorder")
public class CoreOrdersMarketkController extends AbstractBaseController {
	private static final Logger logger = LoggerFactory.getLogger(CoreOrdersMarketkController.class);
	@Autowired
	CoreordersMarketkManager coreOrdersManager;
	@Autowired
	CoreordersMarketkService coreOrderSerbice;
	@Autowired
	UnicomPostcityCodeManager unicompostcityManager;
	@Autowired
	KplanPhonenumBerManager kplanPhoneManager;
	@Autowired
	ManagerInfoManager managerInfoManager;
	@Autowired
	KplanSecondaryOrdersManager kplanSecondaryOrdersManager;
	@Autowired
	KplanChannelNumberDetailManager kplanChannelNumberDetailManager;
	@Autowired
	kplanscordersManager kplanscordersManager;

	@RequestMapping("/list")
	public String findOrders(Map<String, Object> map, CoreOrdersMarketkQuery query) throws ParseException {
		if (query.getCreatedDateEnd() == null) {
			Date d = DateUtils.getDayNumT(0);
			query.setCreatedDateEnd(d);
			query.setCreatedDateStart(d);
		}
		Page<CoreOrdersMarketk> page = this.coreOrdersManager.findOrder(query, this.getPageRequest());
		map.put("query", query);
		map.put("page", page);
		return "coreorders/list";
	}

	@RequestMapping("/edit")
	public String edit(Map<String, Object> map, ManagerInfoQuery query) {
		List<UnicomPostCityCode> l = this.unicompostcityManager.findByPrivoin();
		List<Kplanprocducts> product = this.coreOrdersManager.qryProcDucts();
		List<KplanPhoneNumber> phoneList = this.kplanPhoneManager.findPhoneList("", product.get(0).getProcduct_code());
		List<KplanPhoneNumber> phoneRuleList = this.kplanPhoneManager.findPhoneRuleList(product.get(0).getProcduct_code());
		map.put("privoin", l);
		map.put("phoneList", phoneList);
		map.put("phoneRuleList", phoneRuleList);
		map.put("product", product);
		map.put("managerinfo", super.getCurrentUserDetails().orElse(null));
		return "coreorders/edit";
	}

	@RequestMapping("/editorder")
	public String editorder(Map<String, Object> map, CoreOrdersMarketkQuery query, String id) {
		List<UnicomPostCityCode> l = this.unicompostcityManager.findByPrivoin();
		List<Kplanprocducts> product = this.coreOrdersManager.qryProcDucts();
		CoreOrdersMarketk order = this.coreOrdersManager.findById(query.getDomain().getId());
		List<KplanPhoneNumber> phoneList = this.kplanPhoneManager.findPhoneList("", order.getProduct_code());
		List<KplanPhoneNumber> phoneRuleList = this.kplanPhoneManager.findPhoneRuleList(order.getProduct_code());
		map.put("privoin", l);
		map.put("phoneList", phoneList);
		map.put("phoneRuleList", phoneRuleList);
		map.put("product", product);
		map.put("order", order);
		return "coreorders/editorder";
	}

	@RequestMapping("/editfialed")
	public String editfialed(Map<String, Object> map, CoreOrdersMarketkQuery query, String id) {
		CoreOrdersMarketk order = this.coreOrdersManager.findById(query.getDomain().getId());
		map.put("order", order);
		return "coreorders/editfialed";
	}

	@RequestMapping("/upedit")
	public String upedit(Map<String, Object> map, ManagerInfoQuery query) {
		return "coreorders/upedit";
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(@RequestParam("file") MultipartFile file, kplanscordersQuery query) throws IOException {
		List<Object> data = EasyExcelFactory.read(file.getInputStream(), new Sheet(1, 0));
		ManagerInfo info = super.getCurrentUserDetails().orElse(null);
		return kplanSecondaryOrdersManager.upLoadorDers(data, query, info);
	}

	@RequestMapping("/uporderedit")
	public String uporderedit(Map<String, Object> map, ManagerInfoQuery query) {
		return "coreorders/uporderedit";
	}

	@RequestMapping(value = "/uploadOrderFile", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadOrderFile(@RequestParam("file") MultipartFile file, String keyword) throws IOException {
		InputStream inputStream = new BufferedInputStream(file.getInputStream());
		List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 0));
		List<OrderRowModel> l = PhoneRuleUtils.orderToList(data);
		return this.coreOrdersManager.addOrders(l, keyword);
	}

	@RequestMapping(method = RequestMethod.GET, value = "qryPhones")
	@ResponseBody
	public Object qryPhones(String phoneRule, String procductCode) {
		Object phoneList = this.kplanPhoneManager.findPhoneList(phoneRule, procductCode);
		return phoneList;
	}
	@RequestMapping(method = RequestMethod.GET, value = "qryRuleList")
	@ResponseBody
	public Object qryRuleList(String procDuctCode) {
		Object ruleList=this.kplanPhoneManager.findPhoneRuleList(procDuctCode);
		return ruleList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "qryCity")
	@ResponseBody
	public Object qryCity(String province_code) {
		Object l = this.unicompostcityManager.findBycity(province_code);
		return l;
	}

	@RequestMapping(method = RequestMethod.GET, value = "qryDistrict")
	@ResponseBody
	public Object qryDistrict(String city_code) {
		Object l = this.unicompostcityManager.qryDistrict(city_code);
		return l;
	}

	@RequestMapping(method = RequestMethod.GET, value = "qryPhonesNum")
	@ResponseBody
	public Object qryPhonesNum(String phoneNum, String procductCode, String procductName) {
		return this.unicompostcityManager.qryPhonesNum(phoneNum, procductCode, procductName);
	}

	/**
	 * 手工单录入
	 * 
	 * @param userName
	 * @param userid
	 * @param address
	 * @param ordersource
	 * @param province_code
	 * @param province_name
	 * @param re_phone
	 * @param city
	 * @param cityName
	 * @param district
	 * @param districtName
	 * @param phone_Num
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "savaOrders")
	@ResponseBody
	public Object savaOrders(String userName, String userid, String address, String ordersource, String province_code,
			String province_name, String re_phone, String city, String cityName, String district, String districtName,
			String phone_Num, String smsstatus, String Productcode, String Productname) {

		logger.info("userName:" + userName + " userid:" + userid + " address:" + address + " ordersource:" + ordersource
				+ " province_code:" + province_code + " province_name:" + province_name + " re_phone：" + re_phone
				+ " phone_Num:" + phone_Num + " city:" + city + " cityName:" + cityName + " district:" + district
				+ " districtName:" + districtName + " phone_Num:" + phone_Num + "Productcode:" + Productcode
				+ "Productname:" + Productname + " UserName:"
				+ super.getCurrentUserDetails().orElse(null).getBasicUserInfo().getUserRealName());
		return this.coreOrdersManager.savaOrders(userName, userid, address, ordersource, province_code, province_name,
				re_phone, city, cityName, district, districtName, phone_Num, smsstatus, Productcode, Productname);
	}

	/**
	 * 手工单导入选号
	 * 
	 * @param userName
	 * @param userid
	 * @param address
	 * @param ordersource
	 * @param province_code
	 * @param province_name
	 * @param re_phone
	 * @param city
	 * @param cityName
	 * @param district
	 * @param districtName
	 * @param phone_Num
	 * @param smsstatus
	 * @param Productcode
	 * @param Productname
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "savaNextOrders")
	@ResponseBody
	public Object savaNextOrders(String orderNo, String userName, String userid, String address, String ordersource,
			String province_code, String province_name, String re_phone, String city, String cityName, String district,
			String districtName, String phone_Num, String smsstatus, String Productcode, String Productname) {

		logger.info("userName:" + userName + " userid:" + userid + " address:" + address + " ordersource:" + ordersource
				+ " province_code:" + province_code + " province_name:" + province_name + " re_phone：" + re_phone
				+ " phone_Num:" + phone_Num + " city:" + city + " cityName:" + cityName + " district:" + district
				+ " districtName:" + districtName + " phone_Num:" + phone_Num + "Productcode:" + Productcode
				+ "Productname:" + Productname+" UserName:"
						+ super.getCurrentUserDetails().orElse(null).getBasicUserInfo().getUserRealName());
		return this.coreOrdersManager.savaNextOrders(orderNo, userName, userid, address, ordersource, province_code,
				province_name, re_phone, city, cityName, district, districtName, phone_Num, smsstatus, Productcode,
				Productname);
	}

	/**
	 * 回捞订单列表
	 * 
	 * @param map
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/secondarylist")
	public String qrySeconDaryorDer(Map<String, Object> map, KplanSecondaryOrdersQuery query) throws ParseException {
		if (query.getCreatedDateEnd() == null) {
			query.setCreatedDateEnd(DateUtils.getDayNum(0));
			query.setCreatedDateStart(DateUtils.getDayNum(3));
		}
		List<BackTitle> lti=this.kplanSecondaryOrdersManager.qryTitle(query.getCreatedDateStart(), query.getCreatedDateEnd());
		Page<KplanSecondaryOrders> orDers = this.kplanSecondaryOrdersManager.qrySeconadryorDer(query,
				this.getPageRequest());
		map.put("page", orDers);
		map.put("query", query);
		map.put("lti", lti);
		return "coreorders/secondarylist";

	}

	/**
	 * 移网审单列表
	 * 
	 * @param map
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/scorderlist")
	public String qryScorDers(Map<String, Object> map, kplanscordersQuery query) throws ParseException {
		if (query.getCreatedDateEnd() == null) {
			query.setCreatedDateEnd(DateUtils.getDayNum(0));
			query.setCreatedDateStart(DateUtils.getDayNum(3));
		}
		Page<kplanscorders> scoDers = this.kplanscordersManager.qryList(query, this.getPageRequest());
		if(query.getCreatedDateStart()==null||query.getCreatedDateEnd()==null) {
			Date d = DateUtils.getDayNumT(0);
			query.setCreatedDateEnd(d);
			query.setCreatedDateStart(d);
		}
		map.put("page", scoDers);
		map.put("query", query);
		return "coreorders/scorderlist";
	}

	@RequestMapping("/reportForm")
	public String reportForm(Map<String, Object> map, KplanChannelNumberDetailQuery query) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		query.setCreatedDateStart(sdf.parse(DateUtils.getoDay()));
		query.setCreatedDateEnd(sdf.parse(DateUtils.getyesterDay()));
		Page<KplanChannelNumberDetail> page = this.kplanChannelNumberDetailManager.findChannelInfos(query,
				this.getPageRequest());
		map.put("page", page);
		map.put("query", query);
		return "test/a";
	}

	@RequestMapping("/reportFormList")
	@ResponseBody
	public Object reportFormList(Map<String, Object> map, KplanChannelNumberDetailQuery query) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(DateUtils.getSevenDay(3));
		query.setCreatedDateStart(sdf.parse(DateUtils.getSevenDay(7)));
		query.setCreatedDateEnd(sdf.parse(DateUtils.getoDay()));

		return this.kplanChannelNumberDetailManager.findList(query);
	}

	@RequestMapping(method = RequestMethod.GET, value = "reSet")
	@ResponseBody
	public Object reSet(String orderNo, String phone) {
		return this.coreOrdersManager.reSet(orderNo, phone);
	}

	/**
	 * 重庆恶意订单
	 * 
	 * @param map
	 * @param quer
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/maliciousList")
	public String maliciousList(Map<String, Object> map, CoreOrdersMarketkQuery query) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(query.getCreatedDateStart()==null ||query.getCreatedDateEnd()==null) {
		query.setCreatedDateStart(sdf.parse(DateUtils.getSevenDay(1)));
		query.setCreatedDateEnd(sdf.parse(DateUtils.getoDay()));
		}
		Page<CoreOrdersMarketk> page = this.coreOrdersManager.maliciousList(query, this.getPageRequest());
		map.put("page", page);
		map.put("query", query);
		return "coreorders/maliciousList";
	}

	@RequestMapping("/procedit")
	public String procedit(Map<String, Object> map, CoreOrdersMarketkQuery query, String id) {
		CoreOrdersMarketk order = this.coreOrdersManager.findById(query.getDomain().getId());
		List<UnicomPostCityCode> province = this.unicompostcityManager.findByPrivoin();
		List<UnicomPostCityCode> city =this.unicompostcityManager.findBycity(order.getProvince_code());
		List<UnicomPostCityCode> disr =this.unicompostcityManager.qryDistrict(order.getCity_code());
		map.put("order", order);
		map.put("province", province);
		map.put("city", city);
		map.put("disr", disr);
		return "coreorders/procedit";
	}

	@RequestMapping("/operate")
	public String operate(Map<String, Object> map, CoreOrdersMarketkQuery query, String id) {
		CoreOrdersMarketk order = this.coreOrdersManager.findById(query.getDomain().getId());
		map.put("order", order);
		return "coreorders/operate";
	}

	@RequestMapping(value = "/procOrder" , method = RequestMethod.POST)
	@ResponseBody
	public Object procOrder(String orderNo, String userName, String userid, String address, String re_phone,
			String proctype,String province,String provinceCode,String city,String cityCode,String district,String districtCode,String remarks) {
		ManagerInfo managerInfo=super.getCurrentUserDetails().orElse(null);
		return this.coreOrdersManager.procOrder(orderNo, userName, userid, address, re_phone, proctype,province, provinceCode, city, cityCode, district, districtCode,managerInfo);
	}
	@RequestMapping("/changeOrders")
	public String changeOrders() {
		return "coreorders/changeOrders";
	}
	

	@RequestMapping(value = "/uploadChangeOrders", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadChangeOrders(@RequestParam("file") MultipartFile file, kplanscordersQuery query) throws IOException {
		List<Object> data = EasyExcelFactory.read(file.getInputStream(), new Sheet(1, 2));
		return coreOrdersManager.uploadChangeOrders(data, query);
	}
	@RequestMapping("/city")
	public String city(Map<String, Object> map,UnicomPostCityCodeQuery query) {
		List<UnicomPostCityCode> province = this.unicompostcityManager.findByPrivoin();
		List<UnicomPostCityCode> listCode=this.unicompostcityManager.city();
		map.put("province", province);
		map.put("listCode", listCode);
		map.put("query", query);
		return "coreorders/city";
	}
	
	@RequestMapping("/cityDetail")
	public String cityDetail(Map<String, Object> map,UnicomPostCityCodeQuery query) {
		List<UnicomPostCityCode> province = this.unicompostcityManager.findByPrivoin();
		List<UnicomPostCityCode> listCode=this.unicompostcityManager.cityDetail(query.getDomain().getProvince_code(), query.getDomain().getCity_code());
		if(query.getDomain().getProvince_code()!=null) {
			List<UnicomPostCityCode> city =this.unicompostcityManager.findBycity(query.getDomain().getProvince_code());
			map.put("city", city);
		}
		if(query.getDomain().getCity_code()!=null) {
			List<UnicomPostCityCode> disr =this.unicompostcityManager.qryDistrict(query.getDomain().getCity_code());
			map.put("disr", disr);
		}
		map.put("province", province);
		map.put("listCode", listCode);
		map.put("query", query);
		return "coreorders/city";
	}
	@RequestMapping("/exExcel")
	public void exExcel( String projectName,
            HttpServletResponse response,CoreOrdersMarketkQuery query) throws IOException {
		 String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		    String fileName = date + "，" + "数据报表";
		    try {
		        response.setCharacterEncoding("UTF-8");
		        response.setContentType("application/vnd.ms-excel");
		        fileName = new String(fileName.getBytes("UTF-8"), "UTF-8");
		        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
		        List<CoreOrdersMarketk> l=this.coreOrdersManager.qryExorDer(query);
		        List<excelOrder> ex = new ArrayList<excelOrder>();
		        if(l!=null) {
		        	excelOrder e;
		        	for(CoreOrdersMarketk k:l) {
		        		 e = new excelOrder();
		        		 if(9001==k.getTrack_status()) {
		        			 String orderNo="CQBACK"+k.getOrder_no();
		        				String sql="from CoreOrdersMarketk where id='"+orderNo+"'";
		        				List<CoreOrdersMarketk> li=this.coreOrderSerbice.getResultList(sql);
		        				 CoreOrdersMarketk o = null;
		        				if(li!=null&&li.size()>0) {
		        					o=li.get(0);
		        				}
		        			if(o!=null) {
		        				e.setAddress(o.getReceiver_address());
				        		 e.setCityName(o.getCity_name());
				        		 e.setDirName(o.getDistrict_name());
				        		 e.setMobile(o.getReceiver_phone());
				        		 e.setOperator(o.getOperator());
				        		 e.setOrderNo(o.getOrder_no());
				        		 e.setOrderSurce(o.getOrder_source());
				        		 e.setPhone(o.getOrder_number());
				        		 e.setProChannel(o.getExternal_company());
				        		 e.setTracTime(o.getTracktime());
				        		 e.setProvicnName(o.getProvince_name());
				        		 e.setUserId(o.getAccess_id_number());
				        		 e.setUserName(o.getAccess_name());
				        		 e.setOrderStatus(o.getOrder_status().getDesc());
		        			}else {
		        			 e.setAddress(k.getReceiver_address());
			        		 e.setCityName(k.getCity_name());
			        		 e.setDirName(k.getDistrict_name());
			        		 e.setMobile(k.getReceiver_phone());
			        		 e.setOperator(k.getOperator());
			        		 e.setOrderNo(k.getOrder_no());
			        		 e.setOrderSurce(k.getOrder_source());
			        		 e.setPhone(k.getOrder_number());
			        		 e.setProChannel(k.getExternal_company());
			        		 e.setTracTime(k.getTracktime());
			        		 e.setProvicnName(k.getProvince_name());
			        		 e.setUserId(k.getAccess_id_number());
			        		 e.setUserName(k.getAccess_name());
			        		 e.setOrderStatus(k.getOrder_status().getDesc());
		        			}
		        		 }
		        		 if(9001!=k.getTrack_status()) {
		        			 e.setAddress(k.getReceiver_address());
			        		 e.setCityName(k.getCity_name());
			        		 e.setDirName(k.getDistrict_name());
			        		 e.setMobile(k.getReceiver_phone());
			        		 e.setOperator(k.getOperator());
			        		 e.setOrderNo(k.getOrder_no());
			        		 e.setOrderSurce(k.getOrder_source());
			        		 e.setPhone(k.getOrder_number());
			        		 e.setProChannel(k.getExternal_company());
			        		 e.setTracTime(k.getTracktime());
			        		 e.setProvicnName(k.getProvince_name());
			        		 e.setUserId(k.getAccess_id_number());
			        		 e.setUserName(k.getAccess_name());
			        		 e.setOrderStatus(k.getOrder_status().getDesc());
		        		 }
		        		 if(330==k.getTrack_status()) {
		        		 e.setOperatorType("客服处理");
		        		 	}
		        		 if(9001==k.getTrack_status()) {
			        		 e.setOperatorType("订单重新办理");
			        		 }
		        		 if(9002==k.getTrack_status()) {
			        		 e.setOperatorType("订单不办理");
			        		 }
		        		 if(9003==k.getTrack_status()) {
			        		 e.setOperatorType("订单转运营");
			        		 }
		        		 if(9004==k.getTrack_status()) {
			        		 e.setOperatorType("订单二次回访");
			        		 }
		        		 if(9005==k.getTrack_status()) {
			        		 e.setOperatorType("订单关闭");
			        		}
		        		 if(9006==k.getTrack_status()) {
			        		 e.setOperatorType("订单三次回访");
			        		}
		        		 e.setCreaTime(k.getCreatetime());
		        		 e.setProcductName(k.getProduct_name());
		        		 e.setRemarks(k.getRemarks());
		        		 e.setRespon(k.getFail_reasons());
		        		 e.setMaliciousTag(k.getMalicious_tag());
		        		 ex.add(e);
		        	}
		        }
		        
		        OutputStream outputStream = response.getOutputStream();
		        EasyExcel.write(outputStream, excelOrder.class) 
		                .excelType(ExcelTypeEnum.XLSX)
		                .sheet("恶意订单")
		                .doWrite(ex);
		        outputStream.flush();
		        outputStream.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	}
	
	/**回捞订单导出
	 * @param projectName
	 * @param response
	 * @param query
	 * @throws IOException
	 */
	@RequestMapping("/exBackExcel")
	public void exBackExcel( String projectName,
            HttpServletResponse response,CoreOrdersMarketkQuery query) throws IOException {
		 String date = new SimpleDateFormat("yyyy-MM-dd HHmm").format(new Date());
		    String fileName = date + "数据报表";
		    try {
		        response.setCharacterEncoding("UTF-8");
		        response.setContentType("application/vnd.ms-excel");
		        fileName = new String(fileName.getBytes("UTF-8"), "UTF-8");
		        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
		        List<KplanSecondaryOrders> l=this.kplanSecondaryOrdersManager.exExcel(query.getDomain().getOperator());
		        List<excelBackOrder> ex = new ArrayList<excelBackOrder>();
		        if(l!=null) {
		        	excelBackOrder e;
		        	for(KplanSecondaryOrders k:l) {
		        		 e = new excelBackOrder();
		        		 e.setBack_info(k.getBack_info());
		        		 e.setDistribution_addres(k.getDistribution_addres());
		        		 e.setMalicious_order(k.getMalicious_order());
		        		 e.setORDER_NO(k.getOrder_no());
		        		 e.setORDER_STATUS(k.getOrder_status());
		        		 e.setPHONE_NUM(k.getPhone_num());
		        		 e.setPost_city(k.getPost_city());
		        		 e.setPost_district(k.getPost_district());
		        		 e.setPost_province(k.getPost_province());
		        		 e.setPRO_DATE(k.getPro_date());
		        		 e.setProcduct_name(k.getProcduct_name());
		        		 e.setReject_info(k.getReject_info());
		        		 e.setREMARKS(k.getRemarks());
		        		 e.setUSER_ID(k.getUser_id());
		        		 e.setUSER_NAME(k.getUser_name());
		        		 ex.add(e);
		        	}
		        }
		        
		        OutputStream outputStream = response.getOutputStream();
		        EasyExcel.write(outputStream, excelBackOrder.class) 
		                .excelType(ExcelTypeEnum.XLSX)
		                .sheet("回捞订单")
		                .doWrite(ex);
		        outputStream.flush();
		        outputStream.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	}

	private List<List<String>> createTestListStringHead() {
		// TODO Auto-generated method stub
		return null;
	}
}
