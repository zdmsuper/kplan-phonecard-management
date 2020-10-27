package com.kplan.phonecard.controller;

import java.io.IOException;
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

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.kplan.phonecard.domain.CoreOrdersMarketk;
import com.kplan.phonecard.domain.KplanPhoneNumber;
import com.kplan.phonecard.domain.KplanSecondaryOrders;
import com.kplan.phonecard.domain.UnicomPostCityCode;
import com.kplan.phonecard.domain.kplanscorders;
import com.kplan.phonecard.manager.ManagerInfoManager;
import com.kplan.phonecard.manager.CoreordersMarketkManager;
import com.kplan.phonecard.manager.KplanPhonenumBerManager;
import com.kplan.phonecard.manager.KplanSecondaryOrdersManager;
import com.kplan.phonecard.manager.UnicomPostcityCodeManager;
import com.kplan.phonecard.manager.kplanscordersManager;
import com.kplan.phonecard.query.ManagerInfoQuery;
import com.kplan.phonecard.query.kplanscordersQuery;
import com.kplan.phonecard.query.CoreOrdersMarketkQuery;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;
import com.kplan.phonecard.service.CoreordersMarketkService;

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
		List<KplanPhoneNumber> phoneList=this.kplanPhoneManager.findPhoneList("");
		List<KplanPhoneNumber> phoneRuleList=this.kplanPhoneManager.findPhoneRuleList();
		map.put("privoin", l);
		map.put("phoneList", phoneList);
		map.put("phoneRuleList", phoneRuleList);
		return "coreorders/edit";
	}
	@RequestMapping("/upedit")
	public String upedit(Map<String, Object> map, ManagerInfoQuery query) {
		return "coreorders/upedit";
	}
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		List<Object> data = EasyExcelFactory.read(file.getInputStream(), new Sheet(1, 0));
		return  kplanSecondaryOrdersManager.upLoadorDers(data);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "qryPhones")
	@ResponseBody
	public Object qryPhones(String phoneRule) {
		Object phoneList = this.kplanPhoneManager.findPhoneList(phoneRule);
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
	public Object qryPhonesNum(String phoneNum) {
		return this.unicompostcityManager.qryPhonesNum(phoneNum);
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
			String phone_Num,String smsstatus) {

		logger.info("userName:" + userName + " userid:" + userid + " address:" + address + " ordersource:" + ordersource
				+ " province_code:" + province_code + " province_name:" + province_name + " re_phone：" + re_phone
				+ " phone_Num:" + phone_Num + " city:" + city + " cityName:" + cityName + " district:" + district
				+ " districtName:" + districtName+" phone_Num:"+phone_Num);
		return this.coreOrdersManager.savaOrders(userName, userid, address, ordersource, province_code, province_name,
				re_phone, city, cityName, district, districtName, phone_Num,smsstatus);
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
}
