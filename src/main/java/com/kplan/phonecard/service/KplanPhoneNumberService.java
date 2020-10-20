package com.kplan.phonecard.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class KplanPhoneNumberService {
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
}
