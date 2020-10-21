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

import com.kplan.phonecard.domain.KplanChannelNumber;
import com.kplan.phonecard.query.KplanChannelNumberQuery;
import com.kplan.phonecard.service.KplanChannelNumberService;

@Component
@Transactional
public class KplanChannelNumberManager extends BaseManager{
	@Autowired
	KplanChannelNumberService kplanChannelNumberService;
	@Transactional(readOnly = true)
	public Page<KplanChannelNumber> findChannelInfos(@NotNull KplanChannelNumberQuery query, Pageable pageable){
		
		Specification<KplanChannelNumber> spec=new Specification<KplanChannelNumber>() {
			List<Predicate> list = new ArrayList<>();
			@Override
			public Predicate toPredicate(Root<KplanChannelNumber> r, CriteriaQuery<?> qr,
					CriteriaBuilder cb) {
//				cb.equal(r.get("id"), 23L)
				list.add(cb.equal(r.get("open_status"), 0));
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return this.kplanChannelNumberService.findAll(spec,pageable);
	}
}
