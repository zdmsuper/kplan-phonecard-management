package com.kplan.phonecard.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kplan.phonecard.domain.KplanSecondaryOrders;
import com.kplan.phonecard.domain.KplanUnicomPhone;
import com.kplan.phonecard.manager.KplanUnicomPhoneManager;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;
import com.kplan.phonecard.query.KplanUnicomPhoneQuery;

@Controller
@RequestMapping("/phone")
public class PhoneUnicomController extends AbstractBaseController{
	@Autowired
	KplanUnicomPhoneManager kplanUnicomPhoneManager;
	/**获取联通官方号码库
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Map<String, Object> map, KplanUnicomPhoneQuery query) {
		Page<KplanUnicomPhone> page=this.kplanUnicomPhoneManager.list(query, this.getPageRequest());
		List<KplanUnicomPhone> listSectionNo=this.kplanUnicomPhoneManager.qrySectionNo();
		List<KplanUnicomPhone> listRuleName = null;
		if(query.getDomain().getSection_no()!=null) {
			 listRuleName=this.kplanUnicomPhoneManager.qryRuleNameList(query.getDomain().getSection_no());
		}
		map.put("page", page);
		map.put("query", query);
		map.put("listRuleName", listRuleName);
		map.put("listSectionNo", listSectionNo);
		return "phone/list";
	}
	
	/**查询规则
	 * @param province_code
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "qryRuleName")
	@ResponseBody
	public Object qryRuleName(String section_no) {
		Object l = this.kplanUnicomPhoneManager.qryRuleName(section_no);
		return l;
	}
}
