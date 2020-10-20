package com.kplan.phonecard.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kplan.phonecard.domain.PhoneRuleResult;
import com.kplan.phonecard.domain.errorMsg;
import com.kplan.phonecard.domain.kplan_phone_number;
import com.kplan.phonecard.domain.unicom_post_city_code;
import com.kplan.phonecard.service.unicom_post_city_codeService;
import com.kplan.phonecard.utils.HttpUtils;
import com.kplan.phonecard.utils.PhoneRuleUtils;

import one.util.streamex.StreamEx;

@Component
@Transactional
public class UnicomPostcityCodeManager extends BaseManager{
	@Autowired
	unicom_post_city_codeService unicomCityService;
	
	public List<unicom_post_city_code> findByPrivoin(){
		String sql="select  province_code,province_name from unicom_post_city_code group by province_code,province_name";
		List<Object[]> l=unicomCityService.getNativeResultList(sql);
		List<unicom_post_city_code> resultList = StreamEx.of(l).map(r -> {
			unicom_post_city_code b = new unicom_post_city_code();
			b.setProvince_code((String) r[0]);
			b.setProvince_name((String) r[1]);
			return b;
		}).toList();
		return resultList;
	}
	
	public List<unicom_post_city_code> findBycity(String province_code){
		String sql="select  city_code,city_name from unicom_post_city_code where province_code='"+province_code+"' group by city_code,city_name";
		List<Object[]> l=unicomCityService.getNativeResultList(sql);
		List<unicom_post_city_code> resultList = StreamEx.of(l).map(r -> {
			unicom_post_city_code b = new unicom_post_city_code();
			b.setCity_code((String) r[0]);
			b.setCity_name((String) r[1]);
			return b;
		}).toList();
		return resultList;
	}
	
	public List<unicom_post_city_code> qryDistrict(String city_code){
		String sql="select  district_code,district_name from unicom_post_city_code where city_code='"+city_code+"' group by district_code,district_name";
		List<Object[]> l=unicomCityService.getNativeResultList(sql);
		List<unicom_post_city_code> resultList = StreamEx.of(l).map(r -> {
			unicom_post_city_code b = new unicom_post_city_code();
			b.setId((String) r[0]);
			b.setDistrict_name((String) r[1]);
			return b;
		}).toList();
		return resultList;
	}
	
	/**
	 * 尾号匹配选号
	 * 
	 * @param phoneNum
	 * @return
	 */
	public Object qryPhonesNum(String phoneNum) {
		String url = "http://59.110.18.76:8888/kplan/kcqapi/selectNumLastNUm?provinceCode=81&cityCode=810&searchCategory=3&goodsId=981610241535&amounts=20&searchType=02&searchValue="
				+ phoneNum;
		String[] result = HttpUtils.doGet(url, 6000);
		List<kplan_phone_number> l = new ArrayList<kplan_phone_number>();
		if ("SUCCEESS".equals(result[0])) {
			JSONObject dataJson = JSONObject.parseObject(result[1]);
			String status = dataJson.getString("status");
			String code = dataJson.getString("code");
			if ("200".equals(status) && "200".equals(status)) {
				JSONObject datasJson = dataJson.getJSONObject("data");
				String dataStr = datasJson.getString("phoNe");
				// 联通获取的电话号码转换为号码集合
				List<PhoneRuleResult> phoneResult = PhoneRuleUtils.ordersToPhone(dataStr);
				if(phoneResult!=null&&phoneResult.size()>0) {
				for (PhoneRuleResult p : phoneResult) {
					kplan_phone_number ph=(kplan_phone_number) this.unicomCityService.getById(p.getPhone(), kplan_phone_number.class);
					if(ph==null) {
					kplan_phone_number k =  new kplan_phone_number();
					k.setCity_code("810");
					k.setCity_name("成都市");
					k.setId(p.getPhone());
					k.setProvince_code("81");
					k.setProvince_name("四川");
					k.setUse_not(0);
					k.setIs_effective(0);
					k.setRule_name("尾号匹配");
					k.setRender_num(8);
					k.setRender_starindex(4);
					k.setCread_date(new Date());
					k.setLast_date(new Date());
					k.setPhone_source("手工单尾号匹配");
					k.setPriority_name(1);
					l.add(k);
					this.unicomCityService.add(k);;
					}else {
						kplan_phone_number k =  new kplan_phone_number();
						k.setCity_code("810");
						k.setCity_name("成都市");
						k.setId(p.getPhone());
						k.setProvince_code("81");
						k.setProvince_name("四川");
						k.setUse_not(0);
						k.setIs_effective(0);
						k.setRule_name("尾号匹配");
						k.setRender_num(8);
						k.setRender_starindex(4);
						k.setCread_date(new Date());
						k.setLast_date(new Date());
						k.setPhone_source("手工单尾号匹配");
						k.setPriority_name(1);
						l.add(k);
					}
				}
				return l;
				}else {
					errorMsg msg=new errorMsg();
					msg.setStatus("201");
					msg.setMsg("尾号匹配不到可用号码");
					return JSON.toJSON(msg);
				}
			}
		}
		errorMsg msg=new errorMsg();
		msg.setStatus("201");
		msg.setMsg("尾号匹配不到可用号码");
		return JSON.toJSON(msg);
		
	}
}
