package com.kplan.phonecard.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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


	public List<KplanPhoneNumber> findPhoneList(String phoneRule,String procductCode,String provicnCode){
		String sql = null;
		if(StringUtils.trimToNull(phoneRule)==null) {
			if("81".equals(provicnCode)) {
				sql = "select phone,province_name,city_name,rule_name,last_date from kplan_phone_number where province_code='81' and city_code='810' and use_not=0 and rule_name!='尾号匹配'  and product_code='"+procductCode+"' order by random(),last_date desc limit 2";
			}
			if("85".equals(provicnCode)) {
				sql = "select phone,province_name,city_name,rule_name,last_date from kplan_phone_number where province_code='85' and city_code='850' and use_not=0 and rule_name!='尾号匹配'  and product_code='"+procductCode+"' order by random(),last_date desc limit 2";
			}
			
		}else {
			if("81".equals(provicnCode)) {
				sql = "select phone,province_name,city_name,rule_name,last_date from kplan_phone_number where province_code='81' and city_code='810' and use_not=0  and rule_name='"+phoneRule+"' and product_code='"+procductCode+"' order by random(),last_date desc limit 2";
			}
			if("85".equals(provicnCode)) {
				sql = "select phone,province_name,city_name,rule_name,last_date from kplan_phone_number where province_code='85' and city_code='850' and use_not=0  and rule_name='"+phoneRule+"' and product_code='"+procductCode+"' order by random(),last_date desc limit 2";
			}else {
				sql = "select phone,province_name,city_name,rule_name,last_date from kplan_phone_number where province_code='81' and city_code='810' and use_not=0  and rule_name='"+phoneRule+"' and product_code='"+procductCode+"' order by random(),last_date desc limit 2";
			}
		}
		if(StringUtils.trimToNull(sql)==null) {
			sql = "select phone,province_name,city_name,rule_name,last_date from kplan_phone_number where province_code='81' and city_code='810' and use_not=0 and rule_name!='尾号匹配'  and product_code='"+procductCode+"' order by random(),last_date desc limit 2";
		}
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	List<Object[]> result =this.kplanPhoneService.getNativeResultList(sql);
	List<KplanPhoneNumber> resultList = StreamEx.of(result).map(r -> {
		KplanPhoneNumber b = new KplanPhoneNumber();
		b.setId((String) r[0]);
		b.setProvince_name((String) r[1]);
		b.setCity_name((String) r[2]);
		b.setRule_name((String) r[3]);
		b.setLast_date((Date) r[4]);
		return b;
	}).toList();
		return resultList;
	}
	
	public List<KplanPhoneNumber> findPhoneRuleList(String product_code,String ordersource){
		String procince="81";
		String cityCode="810";
		if("贵州".equals(ordersource)) {
			 procince="85";
			 cityCode="850";
		}
		String sql = "select rule_name from kplan_phone_number where province_code=?1 and city_code=?2 and use_not=0 and rule_name!='尾号匹配'  and product_code='"+product_code+"'  group by rule_name";
		List<Object> result =this.kplanPhoneService.getNativeResultList(sql,procince,cityCode);
		List<KplanPhoneNumber> resultList = StreamEx.of(result).map(r -> {
			KplanPhoneNumber b = new KplanPhoneNumber();
			b.setRule_name( r.toString());
			return b;
		}).toList();
			return resultList;
		}



}
