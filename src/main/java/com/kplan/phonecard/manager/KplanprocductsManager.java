package com.kplan.phonecard.manager;

import java.text.ParseException;
import java.util.ArrayList;
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
import com.kplan.phonecard.domain.CoreOrdersMarketk;
import com.kplan.phonecard.domain.Kplanprocducts;
import com.kplan.phonecard.domain.kplanscorders;
import com.kplan.phonecard.domain.msgRes;
import com.kplan.phonecard.enums.OrderStatusEnum;
import com.kplan.phonecard.query.CoreOrdersMarketkQuery;
import com.kplan.phonecard.query.KplanprocductsQuery;
import com.kplan.phonecard.service.KplanprocductService;
import com.kplan.phonecard.utils.DateUtils;

@Component
@Transactional
public class KplanprocductsManager extends BaseManager{
	@Autowired
	KplanprocductService kplanprocductService;
	
	public Kplanprocducts qryProcduct(String procDuctName) {
		String sql="from Kplanprocducts where procduct_code like '%"+procDuctName+"%'";
		List<Kplanprocducts> l=this.kplanprocductService.getResultList(sql);
		if(l!=null&&l.size()>0) {
			return l.get(0);
		}
		return null;
		
	}
	
	public Page<Kplanprocducts> qryProDuct(@NotNull KplanprocductsQuery query, Pageable pageable){
		Specification<Kplanprocducts> spec = new Specification<Kplanprocducts>() {
			public Predicate toPredicate(Root<Kplanprocducts> r, CriteriaQuery<?> qr, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return this.kplanprocductService.findAll(spec, pageable);
	}
	
	public Object savaProDucts(String procduct_code,String procduct_name) {
		msgRes msg=new msgRes();
		Kplanprocducts p=new Kplanprocducts();
		p.setProcduct_code(procduct_code);
		p.setProcduct_name(procduct_name);
		try {
			this.kplanprocductService.add(p);
			msg.setCode("200");
			msg.setStatus("200");
			msg.setMsg("产品添加成功");
		} catch (Exception e) {
		msg.setCode("201");
		msg.setStatus("201");
		msg.setMsg("添加产品异常，请联系管理员");
		}
		return JSON.toJSON(msg);
	}
	
	public Object proDuctDel(Integer id) {
		msgRes msg=new msgRes();
		try {
			this.kplanprocductService.removeById(id, Kplanprocducts.class);
			msg.setCode("200");
			msg.setStatus("200");
			msg.setMsg("产品删除成功");
		} catch (Exception e) {
			msg.setCode("201");
			msg.setStatus("201");
			msg.setMsg("删除产品异常，请联系管理员");
		}
		return JSON.toJSON(msg);
	}
}
