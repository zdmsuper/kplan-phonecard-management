package com.kplan.phonecard.manager;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kplan.phonecard.domain.KplanPhoneNumber;
import com.kplan.phonecard.service.KplanPhoneNumberService;

import one.util.streamex.StreamEx;

@Component
@Transactional
public class KplanPhonenumBerManager extends BaseManager{
@Autowired
KplanPhoneNumberService kplanPhoneService;


	public List<KplanPhoneNumber> findPhoneList(String phoneRule){
		String sql;
		if(StringUtils.trimToNull(phoneRule)==null) {
			sql = "select phone,province_name,city_name,rule_name from kplan_phone_number where province_code='81' and city_code='810' and use_not=0 and rule_name!='尾号匹配'  order by random() limit 6";
		}else {
			sql = "select phone,province_name,city_name,rule_name from kplan_phone_number where province_code='81' and city_code='810' and use_not=0  and rule_name='"+phoneRule+"' order by random() limit 6";
		}
	 
	List<Object[]> result =this.kplanPhoneService.getNativeResultList(sql);
	List<KplanPhoneNumber> resultList = StreamEx.of(result).map(r -> {
		KplanPhoneNumber b = new KplanPhoneNumber();
		b.setId((String) r[0]);
		b.setProvince_name((String) r[1]);
		b.setCity_name((String) r[2]);
		b.setRule_name((String) r[3]);
		return b;
	}).toList();
		return resultList;
	}
	
	public List<KplanPhoneNumber> findPhoneRuleList(){
		String sql = "select rule_name from kplan_phone_number where province_code='81' and city_code='810' and use_not=0 and rule_name!='尾号匹配'  group by rule_name";
		List<Object> result =this.kplanPhoneService.getNativeResultList(sql);
		List<KplanPhoneNumber> resultList = StreamEx.of(result).map(r -> {
			KplanPhoneNumber b = new KplanPhoneNumber();
			b.setRule_name( r.toString());
			return b;
		}).toList();
			return resultList;
		}



}
