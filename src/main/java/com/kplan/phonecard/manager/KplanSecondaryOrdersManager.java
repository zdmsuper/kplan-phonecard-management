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
import com.kplan.phonecard.domain.CoreOrdersMarketk;
import com.kplan.phonecard.domain.KplanSecondaryOrders;
import com.kplan.phonecard.domain.msgRes;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;
import com.kplan.phonecard.query.ManagerInfoQuery;
import com.kplan.phonecard.service.KplanSecondaryOrdersService;
@Component
@Transactional
public class KplanSecondaryOrdersManager extends BaseManager{
	@Autowired
	KplanSecondaryOrdersService kplanSecondaryOrdersService;
	public String upLoadorDers(List<Object> data) {
		msgRes msg=new msgRes();
		KplanSecondaryOrders o;
		try {
			if(data!=null&&data.size()>0) {
				for(Object l:data) {
					List<String> list=(List<String>) l;
					o=new KplanSecondaryOrders();
					o.setPlace_order_time(new Date());
					o.setOrder_no(list.get(0));
					o.setPro_status(0);
					o.setOrder_source("CD");
					this.kplanSecondaryOrdersService.add(o);
				}
				msg.setCode("200");
				msg.setStatus("200");
				msg.setMsg("文件上传成功");
				return JSON.toJSONString(msg);
			}else {
				msg.setCode("201");
				msg.setStatus("201");
				msg.setMsg("文件上传时间文件数据为空");
				return JSON.toJSONString(msg);
			}
		} catch (Exception e) {
			msg.setCode("999");
			msg.setStatus("999");
			msg.setMsg("系统异常，请稍后重试");
			return JSON.toJSONString(msg);
		}
		
	}
	
	public Page<KplanSecondaryOrders> qrySeconadryorDer(@NotNull KplanSecondaryOrdersQuery query, Pageable pageable){
		Specification<KplanSecondaryOrders> spec=new Specification<KplanSecondaryOrders>() {
			@Override
			public Predicate toPredicate(Root<KplanSecondaryOrders> r, CriteriaQuery<?> qr,
					CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if(query.getCreatedDateStart()!=null&&query.getCreatedDateEnd()!=null) {
					list.add(cb.between(r.get("place_order_time"), query.getCreatedDateStart(), query.getCreatedDateEnd()));
				}else if(query.getKeyword()!=null) {
					list.add(cb.equal(r.get("phone_num"), query.getKeyword()));
				}
//				list.add(cb.or(cb.equal(r.get("pro_status"), 1),cb.equal(r.get("pro_status"), 99)));
//				list.add(cb.or(cb.equal(r.get("pro_status"), 99)));
//				list.add(cb.and());
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return this.kplanSecondaryOrdersService.findAll(spec, pageable);
	}
	
	
}
