package com.kplan.phonecard.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kplan.phonecard.domain.KplanExamination;
import com.kplan.phonecard.domain.KplanMolicioustag;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.domain.msgRes;
import com.kplan.phonecard.query.KplanExaminationQury;
import com.kplan.phonecard.service.KplanExaminationService;

@Component
@Transactional
public class KplanExaminationManager extends BaseManager{
	@Autowired
	KplanExaminationService kplanExaminationService;
	
	
	public Page<KplanExamination> qryList(@NotNull KplanExaminationQury query, Pageable pageable){
		Specification<KplanExamination> spec = new Specification<KplanExamination>() {
			public Predicate toPredicate(Root<KplanExamination> r, CriteriaQuery<?> qr, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return this.kplanExaminationService.findAll(spec, pageable);
	}
	
	/**审单标签添加
	 * @param cont_code
	 * @param examination_status
	 * @param transfer_job
	 * @param job_name
	 * @return
	 */
	public Object savaExamination(String cont_code,String examination_status  ,String transfer_job, String job_name,ManagerInfo managerInfo) {
		msgRes msg=new msgRes();
		try {
			KplanExamination k=new KplanExamination();
			k.setCont_code(cont_code);
			k.setExamination_status(examination_status);
			k.setTransfer_job(transfer_job);
			k.setJob_name(job_name);
			k.setCread_date(new Date());
			k.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
			kplanExaminationService.add(k);
			msg.setCode("200");
			msg.setStatus("200");
			msg.setMsg("触点增加成功");
		} catch (Exception e) {
		msg.setCode("201");
		msg.setStatus("201");
		msg.setMsg("系统异常，请联系管理员");
		}
		return JSON.toJSON(msg);
	}
	
	/**触点触点标签删除
	 * @param id
	 * @return
	 */
	public  Object examinationDel(Integer id) {
		msgRes msg=new msgRes();
		try {
			this.kplanExaminationService.removeById(id, KplanExamination.class);
			msg.setCode("200");
			msg.setMsg("触点标签标签删除成功");
			msg.setStatus("200");
		} catch (Exception e) {
			msg.setCode("999");
			msg.setMsg("触点标签删除异常");
			msg.setStatus("999");
		}
		return JSON.toJSON(msg);
	}
}
