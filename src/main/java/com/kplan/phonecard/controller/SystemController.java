package com.kplan.phonecard.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kplan.phonecard.domain.KplanExamination;
import com.kplan.phonecard.domain.KplanMolicioustag;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.manager.KplanExaminationManager;
import com.kplan.phonecard.manager.KplanMolicioustagManager;
import com.kplan.phonecard.query.KplanExaminationQury;
import com.kplan.phonecard.query.KplanMolicioustagQuery;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;

@Controller
@RequestMapping("/system")
public class SystemController extends AbstractBaseController{
		@Autowired
		private KplanMolicioustagManager kplanMolicioustagManager;
		@Autowired
		private KplanExaminationManager kplanExaminationManager;
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
		public Object savaMalicious(String maliciousTag,String maliciousTagCode,String businesstype,String businesscode) {
			return this.kplanMolicioustagManager.savaMalicious(maliciousTag, maliciousTagCode, businesstype,businesscode);
		}
		
		/**审单系统参数设置
		 * @param map
		 * @param query
		 * @return
		 */
		@RequestMapping("/externalList")
		public String externalList(Map<String, Object> map, KplanExaminationQury query) {
			Page<KplanExamination> page=this.kplanExaminationManager.qryList(query, this.getPageRequest());
			map.put("page", page);
			map.put("query", query);
			return "system/externalList";
		}
		/**审单参数设置跳转
		 * @param map
		 * @param query
		 * @return
		 */
		@RequestMapping("/externaEdit")
		public String externaEdit(Map<String, Object> map, KplanExaminationQury query) {
			return "system/externaEdit";
		}
		
		/**增加触点编码
		 * @param cont_code
		 * @param examination_status
		 * @param transfer_job
		 * @param job_name
		 * @return
		 */
		@RequestMapping("/savaExamination")
		@ResponseBody
		public Object savaExamination(String cont_code,String examination_status  ,String transfer_job, String job_name,String pro_type,String program_type) {
			ManagerInfo managerInfo=super.getCurrentUserDetails().orElse(null);
			return this.kplanExaminationManager.savaExamination(cont_code, examination_status, transfer_job, job_name, managerInfo,pro_type,program_type);
		}
		/**删除触点编码
		 * @return
		 */
		@RequestMapping(value = "/examinationDel", method = RequestMethod.GET)
		@ResponseBody
		public Object examinationDel(Integer id) {
			return this.kplanExaminationManager.examinationDel(id);
		}
}
