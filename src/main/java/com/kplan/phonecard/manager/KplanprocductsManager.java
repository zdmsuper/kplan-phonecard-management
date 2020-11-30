package com.kplan.phonecard.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kplan.phonecard.domain.Kplanprocducts;
import com.kplan.phonecard.service.KplanprocductService;

@Component
@Transactional
public class KplanprocductsManager extends BaseManager{
	@Autowired
	KplanprocductService kplanprocductService;
	
	public Kplanprocducts qryProcduct(String procDuctName) {
		String sql="from Kplanprocducts where procduct_name like '%"+procDuctName+"%'";
		List<Kplanprocducts> l=this.kplanprocductService.getResultList(sql);
		if(l!=null&&l.size()>0) {
			return l.get(0);
		}
		return null;
		
	}
}
