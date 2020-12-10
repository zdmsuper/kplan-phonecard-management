package com.kplan.phonecard.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kplan.phonecard.domain.KplanMolicioustag;
import com.kplan.phonecard.manager.KplanMolicioustagManager;
import com.kplan.phonecard.query.KplanMolicioustagQuery;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;

@Controller
@RequestMapping("/system")
public class SystemController extends AbstractBaseController{
		@Autowired
		private KplanMolicioustagManager kplanMolicioustagManager;
		/**获取恶意标签列表
		 * @return
		 */
		@RequestMapping("/list")
		public String list(Map<String, Object> map, KplanMolicioustagQuery query) {
			Page<KplanMolicioustag> page=this.kplanMolicioustagManager.qryMaliciTag(query, this.getPageRequest());
			map.put("page", page);
			return "system/list";
		}
		
		/**恶意标签删除
		 * @param maliciousId
		 * @return
		 */
		@RequestMapping(value = "/maliciOusDel", method = RequestMethod.GET)
		@ResponseBody
		private Object maliciOusDel(Integer maliciousId) {
			return this.kplanMolicioustagManager.maliciOusDel(maliciousId);
		}
		/**添加恶意标签跳转
		 * @param map
		 * @param query
		 * @return
		 */
		@RequestMapping("/edit")
		public String edit(Map<String, Object> map, KplanSecondaryOrdersQuery query) {
			return "system/edit";
		}  
		
		/**恶意标签添加
		 * @param maliciousTag
		 * @param maliciousTagCode
		 * @param businesstype
		 * @return
		 */
		@RequestMapping("/savaMalicious")
		@ResponseBody
		public Object savaMalicious(String maliciousTag,String maliciousTagCode,String businesstype) {
			return this.kplanMolicioustagManager.savaMalicious(maliciousTag, maliciousTagCode, businesstype);
		}
}
