package com.kplan.phonecard.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kplan.phonecard.domain.KplanDoorPersonnel;
import com.kplan.phonecard.service.KplanDoorPersonnelService;

import one.util.streamex.StreamEx;

@Component
@Transactional
public class KplanDoorPersonnelManager extends BaseManager{
	@Autowired
	KplanDoorPersonnelService kplanDoorPersonnelService;
	
	public List< KplanDoorPersonnel> qrycompary(){
		String sql=" select company from kplan_door_personnel group by company";
		List<Object> result =this.kplanDoorPersonnelService.getNativeResultList(sql);
		List<KplanDoorPersonnel> resultList = StreamEx.of(result).map(r -> {
			KplanDoorPersonnel b = new KplanDoorPersonnel();
			b.setCompany((String) r);
			return b;
		}).toList();
			return resultList;
	}
	
	public Object qryPersonnel(String personnel) {
		String sql="select * from kplan_door_personnel where company='"+personnel+"'";
		List<Object[]> result =this.kplanDoorPersonnelService.getNativeResultList(sql);
		List<KplanDoorPersonnel> resultList = StreamEx.of(result).map(r -> {
			KplanDoorPersonnel b = new KplanDoorPersonnel();
			b.setUsername((String) r[1]);
			return b;
		}).toList();
			return resultList;
	}
}
