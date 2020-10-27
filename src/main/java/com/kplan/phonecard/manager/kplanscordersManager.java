package com.kplan.phonecard.manager;

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

import com.kplan.phonecard.domain.KplanSecondaryOrders;
import com.kplan.phonecard.domain.kplanscorders;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;
import com.kplan.phonecard.query.kplanscordersQuery;
import com.kplan.phonecard.service.KplanScordersService;

@Component
@Transactional
public class kplanscordersManager extends BaseManager{
	@Autowired
	KplanScordersService KplanScordersService;
	public Page<kplanscorders> qryList(@NotNull kplanscordersQuery query, Pageable pageable){
		Specification<kplanscorders> spec=new Specification<kplanscorders>() {
			@Override
			public Predicate toPredicate(Root<kplanscorders> r, CriteriaQuery<?> qr,
					CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if(query.getCreatedDateStart()!=null&&query.getCreatedDateEnd()!=null) {
					list.add(cb.between(r.get("place_order_duration"), query.getCreatedDateStart(), query.getCreatedDateEnd()));
				}
//				list.add(cb.or(cb.equal(r.get("pro_status"), 1),cb.equal(r.get("pro_status"), 99)));
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return this.KplanScordersService.findAll(spec, pageable);
	}
}
