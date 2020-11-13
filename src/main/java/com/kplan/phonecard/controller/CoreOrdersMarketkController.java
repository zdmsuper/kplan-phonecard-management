package com.kplan.phonecard.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import com.kplan.phonecard.domain.CoreOrdersMarketk;
import com.kplan.phonecard.domain.KplanChannelNumberDetail;
import com.kplan.phonecard.domain.KplanPhoneNumber;
import com.kplan.phonecard.domain.KplanSecondaryOrders;
import com.kplan.phonecard.domain.Kplanprocducts;
import com.kplan.phonecard.domain.OrderRowModel;
import com.kplan.phonecard.domain.UnicomPostCityCode;
import com.kplan.phonecard.domain.kplanscorders;
import com.kplan.phonecard.manager.ManagerInfoManager;
import com.kplan.phonecard.manager.CoreordersMarketkManager;
import com.kplan.phonecard.manager.KplanChannelNumberDetailManager;
import com.kplan.phonecard.manager.KplanPhonenumBerManager;
import com.kplan.phonecard.manager.KplanSecondaryOrdersManager;
import com.kplan.phonecard.manager.UnicomPostcityCodeManager;
import com.kplan.phonecard.manager.kplanscordersManager;
import com.kplan.phonecard.query.ManagerInfoQuery;
import com.kplan.phonecard.query.kplanscordersQuery;
import com.kplan.phonecard.query.CoreOrdersMarketkQuery;
import com.kplan.phonecard.query.KplanChannelNumberDetailQuery;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;
import com.kplan.phonecard.service.CoreordersMarketkService;
import com.kplan.phonecard.utils.DateUtils;
import com.kplan.phonecard.utils.PhoneRuleUtils;

@Controller
@RequestMapping("/coreorder")
public class CoreOrdersMarketkController extends AbstractBaseController{
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
	KplanSecondaryOrdersManager  kplanSecondaryOrdersManager;
	@Autowired
	KplanChannelNumberDetailManager kplanChannelNumberDetailManager;
	@Autowired
	kplanscordersManager kplanscordersManager;
	@RequestMapping("/list")
	public String findOrders(Map<String, Object> map, CoreOrdersMarketkQuery query){
		Page<CoreOrdersMarketk> page = this.coreOrdersManager.findOrder(query, this.getPageRequest());
		map.put("query", query);
		map.put("page", page);
		return "coreorders/list";
	}
	
	@RequestMapping("/edit")
	public String edit(Map<String, Object> map, ManagerInfoQuery query) {
		List<UnicomPostCityCode> l=this.unicompostcityManager.findByPrivoin();
		List<Kplanprocducts> product=this.coreOrdersManager.qryProcDucts();
		List<KplanPhoneNumber> phoneList=this.kplanPhoneManager.findPhoneList("",product.get(0).getProcduct_code());
		List<KplanPhoneNumber> phoneRuleList=this.kplanPhoneManager.findPhoneRuleList();
		map.put("privoin", l);
		map.put("phoneList", phoneList);
		map.put("phoneRuleList", phoneRuleList);
		map.put("product", product);
		return "coreorders/edit";
	}
	
	@RequestMapping("/editorder")
	public String editorder(Map<String, Object> map, CoreOrdersMarketkQuery query,String id) {
		List<UnicomPostCityCode> l=this.unicompostcityManager.findByPrivoin();
		List<Kplanprocducts> product=this.coreOrdersManager.qryProcDucts();
		CoreOrdersMarketk order=this.coreOrdersManager.findById(query.getDomain().getId());
		List<KplanPhoneNumber> phoneList=this.kplanPhoneManager.findPhoneList("",order.getProduct_code());
		List<KplanPhoneNumber> phoneRuleList=this.kplanPhoneManager.findPhoneRuleList();
		map.put("privoin", l);
		map.put("phoneList", phoneList);
		map.put("phoneRuleList", phoneRuleList);
		map.put("product", product);
		map.put("order", order);
		return "coreorders/editorder";
	}
	@RequestMapping("/upedit")
	public String upedit(Map<String, Object> map, ManagerInfoQuery query) {
		return "coreorders/upedit";
	}
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(@RequestParam("file") MultipartFile file,kplanscordersQuery query) throws IOException {
		List<Object> data = EasyExcelFactory.read(file.getInputStream(), new Sheet(1, 0));
		return  kplanSecondaryOrdersManager.upLoadorDers(data,query);
	}
	@RequestMapping("/uporderedit")
	public String uporderedit(Map<String, Object> map, ManagerInfoQuery query) {
		return "coreorders/uporderedit";
	}
	
	@RequestMapping(value = "/uploadOrderFile", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadOrderFile(@RequestParam("file") MultipartFile file,String keyword) throws IOException {
		InputStream inputStream = new BufferedInputStream(file.getInputStream());
		List<Object> data =	 EasyExcelFactory.read(inputStream, new Sheet(1, 0));
		List<OrderRowModel> l=PhoneRuleUtils.orderToList(data);
		return this.coreOrdersManager.addOrders(l, keyword);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "qryPhones")
	@ResponseBody
	public Object qryPhones(String phoneRule,String procductCode) {
		Object phoneList = this.kplanPhoneManager.findPhoneList(phoneRule,procductCode);
		return phoneList;
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
	public Object qryPhonesNum(String phoneNum,String procductCode,String procductName) {
		return this.unicompostcityManager.qryPhonesNum(phoneNum,procductCode,procductName);
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
			String phone_Num,String smsstatus,String Productcode,String Productname) {

		logger.info("userName:" + userName + " userid:" + userid + " address:" + address + " ordersource:" + ordersource
				+ " province_code:" + province_code + " province_name:" + province_name + " re_phone：" + re_phone
				+ " phone_Num:" + phone_Num + " city:" + city + " cityName:" + cityName + " district:" + district
				+ " districtName:" + districtName+" phone_Num:"+phone_Num+"Productcode:"+Productcode+"Productname:"+Productname);
		return this.coreOrdersManager.savaOrders(userName, userid, address, ordersource, province_code, province_name,
				re_phone, city, cityName, district, districtName, phone_Num,smsstatus,Productcode,Productname);
	}
	
	/**手工单导入选号
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
	public Object savaNextOrders(String orderNo,String userName, String userid, String address, String ordersource, String province_code,
			String province_name, String re_phone, String city, String cityName, String district, String districtName,
			String phone_Num,String smsstatus,String Productcode,String Productname) {

		logger.info("userName:" + userName + " userid:" + userid + " address:" + address + " ordersource:" + ordersource
				+ " province_code:" + province_code + " province_name:" + province_name + " re_phone：" + re_phone
				+ " phone_Num:" + phone_Num + " city:" + city + " cityName:" + cityName + " district:" + district
				+ " districtName:" + districtName+" phone_Num:"+phone_Num+"Productcode:"+Productcode+"Productname:"+Productname);
		return this.coreOrdersManager.savaNextOrders(orderNo,userName, userid, address, ordersource, province_code, province_name,
				re_phone, city, cityName, district, districtName, phone_Num,smsstatus,Productcode,Productname);
	}
	/**回捞订单列表
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("/secondarylist")
	public String qrySeconDaryorDer(Map<String, Object> map, KplanSecondaryOrdersQuery query) {
		Page<KplanSecondaryOrders> orDers=this.kplanSecondaryOrdersManager.qrySeconadryorDer(query, this.getPageRequest());
		map.put("page", orDers);
		map.put("query", query);
		return "coreorders/secondarylist";
		
	}
	/**移网审单列表
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("/scorderlist")
	public String qryScorDers(Map<String, Object> map, kplanscordersQuery query) {
		Page<kplanscorders> scoDers=this.kplanscordersManager.qryList(query, this.getPageRequest());
		map.put("page", scoDers);
		map.put("query", query);
		return "coreorders/scorderlist";
	}
	@RequestMapping("/reportForm")
	public String reportForm(Map<String, Object> map, KplanChannelNumberDetailQuery query) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		query.setCreatedDateStart(sdf.parse(DateUtils.getoDay()));
		query.setCreatedDateEnd(sdf.parse(DateUtils.getyesterDay()));
		Page<KplanChannelNumberDetail> page=this.kplanChannelNumberDetailManager.findChannelInfos(query, this.getPageRequest());
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
	public Object reSet(String orderNo,String phone) {
		return this.coreOrdersManager.reSet(orderNo,phone);
	}
}
