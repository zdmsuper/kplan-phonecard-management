package com.kplan.phonecard.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.domain.core_orders_market_k;
import com.kplan.phonecard.domain.kplan_phone_number;
import com.kplan.phonecard.domain.unicom_post_city_code;
import com.kplan.phonecard.enums.GenderEnum;
import com.kplan.phonecard.manager.ManagerInfoManager;
import com.kplan.phonecard.manager.CoreordersMarketkManager;
import com.kplan.phonecard.manager.KplanpHonenumBerManager;
import com.kplan.phonecard.manager.UnicomPostcityCodeManager;
import com.kplan.phonecard.query.ManagerInfoQuery;
import com.kplan.phonecard.query.core_orders_market_kQuery;
import com.kplan.phonecard.service.core_orders_market_kService;

@Controller
@RequestMapping("/coreorder")
public class CoreOrdersMarketkController extends AbstractBaseController{
	private static final Logger logger = LoggerFactory.getLogger(CoreOrdersMarketkController.class);
	@Autowired
	CoreordersMarketkManager coreOrdersManager;
	@Autowired
	core_orders_market_kService coreOrderSerbice;
	@Autowired
	UnicomPostcityCodeManager unicompostcityManager;
	@Autowired
	KplanpHonenumBerManager kplanPhoneManager;
	@Autowired
	ManagerInfoManager managerInfoManager;
	@RequestMapping("/list")
	public String findOrders(Map<String, Object> map, core_orders_market_kQuery query){
		Page<core_orders_market_k> page = this.coreOrdersManager.findOrder(query, this.getPageRequest());
		map.put("query", query);
		map.put("page", page);
		return "coreorders/list";
	}
	
	@RequestMapping("/edit")
	public String edit(Map<String, Object> map, ManagerInfoQuery query) {
		List<unicom_post_city_code> l=this.unicompostcityManager.findByPrivoin();
		List<kplan_phone_number> phoneList=this.kplanPhoneManager.findPhoneList("");
		List<kplan_phone_number> phoneRuleList=this.kplanPhoneManager.findPhoneRuleList();
		map.put("privoin", l);
		map.put("phoneList", phoneList);
		map.put("phoneRuleList", phoneRuleList);
		return "coreorders/edit";
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
}
