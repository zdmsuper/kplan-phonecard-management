package com.kplan.phonecard.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kplan.phonecard.domain.KplanPhoneNumber;
import com.kplan.phonecard.domain.KplanSecondaryOrders;
import com.kplan.phonecard.domain.Kplanprocducts;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.domain.UnicomPostCityCode;
import com.kplan.phonecard.manager.CoreordersMarketkManager;
import com.kplan.phonecard.manager.KplanPhonenumBerManager;
import com.kplan.phonecard.manager.KplanSecondaryOrdersManager;
import com.kplan.phonecard.manager.UnicomPostcityCodeManager;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;

@Controller
@RequestMapping("/malicious")
public class MaliciousOrderController extends AbstractBaseController{
	private static final Logger logger = LoggerFactory.getLogger(MaliciousOrderController.class);
	@Autowired
	KplanSecondaryOrdersManager kplanSecondaryOrdersManager;
	@Autowired
	UnicomPostcityCodeManager unicompostcityManager;
	@Autowired
	CoreordersMarketkManager coreOrdersManager;
	@Autowired
	KplanPhonenumBerManager kplanPhoneManager;
	/**成都恶意订单
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Map<String, Object> map,KplanSecondaryOrdersQuery query) {
		Page<KplanSecondaryOrders> page=this.kplanSecondaryOrdersManager.malicicousList(query, this.getPageRequest());
		map.put("page", page);
		map.put("query", query);
		return "malicious/list";
	}
	@RequestMapping("/qryMaliciOrder")
	public String qryMaliciOrder(Map<String, Object> map,KplanSecondaryOrdersQuery query) {
		String procDuctCode=null;
		KplanSecondaryOrders order=this.kplanSecondaryOrdersManager.findById(query.getDomain().getId());
		UnicomPostCityCode address=this.unicompostcityManager.findByPrivoin(order.getPost_city(), order.getPost_district());
		List<UnicomPostCityCode> province = this.unicompostcityManager.findByPrivoin();
		List<Kplanprocducts> product = this.coreOrdersManager.qryProcDucts();
		if(product!=null) {
			for(Kplanprocducts d:product) {
				if(d.getProcduct_name().equals(order.getProcduct_name())) {
					procDuctCode=d.getProcduct_code();
				}
			}
		}
		List<KplanPhoneNumber> phoneRuleList = this.kplanPhoneManager.findPhoneRuleList(procDuctCode);
		List<KplanPhoneNumber> phoneList = this.kplanPhoneManager.findPhoneList("", procDuctCode);
		List<UnicomPostCityCode> city = null;
		List<UnicomPostCityCode> disr = null ;
		if(address!=null) {
		 city =this.unicompostcityManager.findBycity(address.getProvince_code());
		 disr =this.unicompostcityManager.qryDistrict(address.getCity_code());
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
	
	
	@RequestMapping(value = "/procOrder" , method = RequestMethod.POST)
	@ResponseBody
	public Object procOrder(String orderNo, String userName, String userid, String address, String re_phone,
			String proctype,String province,String provinceCode,String city,String cityCode,String district,String districtCode,String remarks,String procDuctName,String phone_Num,String smsstatus) {
		ManagerInfo managerInfo=super.getCurrentUserDetails().orElse(null);
		return this.kplanSecondaryOrdersManager.procOrder(orderNo, userName, userid, address, re_phone, proctype,province, provinceCode, city, cityCode, district, districtCode,managerInfo,procDuctName,phone_Num,smsstatus);
	}
}
