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
import com.kplan.phonecard.query.KplanMolicioustagQuery;
import com.kplan.phonecard.service.KplanMolicioustagService;

@Component
@Transactional
public class KplanMolicioustagManager extends BaseManager{
	@Autowired
	private KplanMolicioustagService kplanMolicioustagService;
	
	public Page<KplanMolicioustag> qryMaliciTag(@NotNull KplanMolicioustagQuery query, Pageable pageable){
		Specification<KplanMolicioustag> spec = new Specification<KplanMolicioustag>() {
			public Predicate toPredicate(Root<KplanMolicioustag> r, CriteriaQuery<?> qr, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if(query.getDomain().getBusiness_code()!=null) {
					list.add(cb.equal(r.get("business_code"), query.getDomain().getBusiness_code()));
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return this.kplanMolicioustagService.findAll(spec, pageable);
	}
	public Object maliciOusDel(Integer maliciousId) {
		msgRes msg=new msgRes();
		try {
			this.kplanMolicioustagService.removeById(maliciousId, KplanMolicioustag.class);
			msg.setCode("200");
			msg.setMsg("恶意标签删除成功");
			msg.setStatus("200");
		} catch (Exception e) {
			msg.setCode("999");
			msg.setMsg("恶意标签删除异常");
			msg.setStatus("999");
		}
		return JSON.toJSON(msg);
	}
	
	public Object savaMalicious(String maliciousTag,String maliciousTagCode,String businesstype,String businesscode) {
		KplanMolicioustag tag=new KplanMolicioustag();
		msgRes msg=new msgRes();
		tag.setAddgroup("回捞业务");
		tag.setBusiness_code(businesscode);
		tag.setBusiness_type(businesstype);
		tag.setMalicious_tag(maliciousTag);
		tag.setMalicious_code(maliciousTagCode);
		try {
			this.kplanMolicioustagService.add(tag);
			msg.setCode("200");
			msg.setStatus("200");
			msg.setMsg("恶意标签添加成功");
		} catch (Exception e) {
			msg.setCode("999");
			msg.setStatus("999");
			msg.setMsg("恶意标签添加异常");
		}
		return JSON.toJSON(msg);
	}
	
	
}
