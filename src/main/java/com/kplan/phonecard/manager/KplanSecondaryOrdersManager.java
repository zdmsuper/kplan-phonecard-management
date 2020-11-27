package com.kplan.phonecard.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kplan.phonecard.domain.CoreOrdersMarketk;
import com.kplan.phonecard.domain.KplanSecondaryOrders;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.domain.UnicomPostCityCode;
import com.kplan.phonecard.domain.msgRes;
import com.kplan.phonecard.domain.entity.BackTitle;
import com.kplan.phonecard.enums.ProStatusEnum;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;
import com.kplan.phonecard.query.ManagerInfoQuery;
import com.kplan.phonecard.query.kplanscordersQuery;
import com.kplan.phonecard.service.KplanSecondaryOrdersService;
import com.kplan.phonecard.utils.DateUtils;

import one.util.streamex.StreamEx;
@Component
@Transactional
public class KplanSecondaryOrdersManager extends BaseManager{
	@Autowired
	KplanSecondaryOrdersService kplanSecondaryOrdersService;
	public String upLoadorDers(List<Object> data,kplanscordersQuery query,ManagerInfo info) {
		
		msgRes msg=new msgRes();
		KplanSecondaryOrders o;
		try {
			if(data!=null&&data.size()>0) {
				for(Object l:data) {
					List<String> list=(List<String>) l;
					o=new KplanSecondaryOrders();
					o.setPlace_order_time(new Date());
					o.setOrder_no(list.get(0));
					o.setPro_status(ProStatusEnum.CREADORDER);
					o.setOrder_source(query.getKeyword());
					if(info!=null) {
						o.setOperator(info.getBasicUserInfo().getUserRealName()+DateUtils.getTodayDate());
					}
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
				}
				if(query.getKeyword()!=null) {
					list.add(cb.equal(r.get("phone_num"), query.getKeyword()));
				}
				if(query.getDomain().getOperator()!=null) {
					list.add(cb.equal(r.get("operator"), query.getDomain().getOperator()));
				}
				if(query.getDomain().getOrder_source()!=null) {
					list.add(cb.equal(r.get("order_source"), query.getDomain().getOrder_source()));
				}
				if(query.getDomain().getPro_status()!=null) {
					list.add(cb.equal(r.get("pro_status"), query.getDomain().getPro_status()));
				}
//				list.add(cb.or(cb.equal(r.get("pro_status"), 1),cb.equal(r.get("pro_status"), 99)));
//				list.add(cb.or(cb.equal(r.get("pro_status"), 99)));
//				list.add(cb.and());
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return this.kplanSecondaryOrdersService.findAll(spec, pageable);
	}
	
	public List<BackTitle> qryTitle(Date date,Date date2){
		String sql="select operator from kplan_secondary_orders where pro_date>='"+date+"' and  pro_date<='"+date2+"' group by operator ";
		List<Object> l = this.kplanSecondaryOrdersService.getNativeResultList(sql);
		List<BackTitle> resultList = StreamEx.of(l).map(r -> {
			BackTitle b = new BackTitle();
			b.setExportFileTitle(r.toString() );
			return b;
		}).toList();
		return resultList;
				
	}
	public List<KplanSecondaryOrders> exExcel(String operator){
		String sql="from KplanSecondaryOrders where operator='"+operator+"'";
		return this.kplanSecondaryOrdersService.getResultList(sql);
	}
	
}
