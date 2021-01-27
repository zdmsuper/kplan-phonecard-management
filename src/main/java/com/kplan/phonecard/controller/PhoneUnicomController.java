package com.kplan.phonecard.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import com.alibaba.fastjson.JSON;
import com.kplan.phonecard.domain.KplanSecondaryOrders;
import com.kplan.phonecard.domain.KplanUnicomPhone;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.domain.msgRes;
import com.kplan.phonecard.manager.KplanUnicomPhoneManager;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;
import com.kplan.phonecard.query.KplanUnicomPhoneQuery;
import com.kplan.phonecard.query.kplanscordersQuery;

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
		map.put("basicUserInfo", super.getCurrentUserDetails().orElse(null).getBasicUserInfo());
		return "phone/list";
	}
	/**号码上传跳转
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("/upLoadedit")
	public String upLoadedit(Map<String, Object> map, KplanUnicomPhoneQuery query) {
		return "phone/upLoadedit";
	}
	/**号码上传
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		List<Object> data = EasyExcelFactory.read(file.getInputStream(), new Sheet(1, 1));
		ManagerInfo info = super.getCurrentUserDetails().orElse(null);
		return this.kplanUnicomPhoneManager.uploadFile(data);
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
	/**号码锁定
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "lockPhone")
	@ResponseBody
	public Object lockPhone(Long id) {
		return this.kplanUnicomPhoneManager.lockPhone(id);
	}
	
	/**号码解锁
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "UnlockPhone")
	@ResponseBody
	public Object UnlockPhone(Long id) {
		return this.kplanUnicomPhoneManager.UnlockPhone(id);
	}
}
