package com.kplan.phonecard.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kplan.phonecard.dao.Core_orders_market_kDao;
import com.kplan.phonecard.domain.core_orders_market_k;

@Service
public class CoreordersMarketkService {
	@Autowired
	EntityManager em;
	

	public  List getNativeResultList(String sql, Object... paras) {
		Query query = em.createNativeQuery(sql);
		int parameterIndex = 1;
		if (paras != null && paras.length > 0) {
			for (Object obj : paras) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		return query.getResultList();
	}
	@Autowired
	Core_orders_market_kDao Core_orders_market_kDao;

	public Page<core_orders_market_k> findAll(Specification<core_orders_market_k> spec, Pageable pageable) {
		return Core_orders_market_kDao.findAll(spec, pageable);
	}
	
	public  void add(  Object arg0) {
		em.persist(arg0);
	}
	
	public  Object getById(Object arg1, Class arg0) {
		Object o = em.find(arg0, arg1);
		return o;
	}
	
	public  void modify( Object arg0) {
		em.merge(arg0);
	}
	

}
