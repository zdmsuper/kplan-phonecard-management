package com.kplan.phonecard.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kplan.phonecard.domain.PhoneRuleResult;
import com.kplan.phonecard.domain.KplanPhoneNumber;
import com.kplan.phonecard.domain.UnicomPostCityCode;
import com.kplan.phonecard.domain.entity.errorMsg;
import com.kplan.phonecard.service.UnicomPostcityCodeService;
import com.kplan.phonecard.utils.HttpUtils;
import com.kplan.phonecard.utils.PhoneRuleUtils;

import one.util.streamex.StreamEx;

@Component
@Transactional
public class UnicomPostcityCodeManager extends BaseManager {
	@Autowired
	UnicomPostcityCodeService unicomCityService;

	public List<UnicomPostCityCode> findByPrivoin() {
		String sql = "select  province_code,province_name from unicom_post_city_code group by province_code,province_name";
		List<Object[]> l = unicomCityService.getNativeResultList(sql);
		List<UnicomPostCityCode> resultList = StreamEx.of(l).map(r -> {
			UnicomPostCityCode b = new UnicomPostCityCode();
			b.setProvince_code((String) r[0]);
			b.setProvince_name((String) r[1]);
			return b;
		}).toList();
		return resultList;
	}
	
	public UnicomPostCityCode findByPrivoin(String cityName,String dirName){
		String sql = "from UnicomPostCityCode where city_name like '%"+cityName+"%' and district_name like '%"+dirName+"%'";
		List<UnicomPostCityCode> city=this.unicomCityService.getResultList(sql);
		if(city!=null&&city.size()>0) {
			return city.get(0);
			
		}
		return null;
	}

	public List<UnicomPostCityCode> city() {
		String sql = "SELECT province_code,province_name,city_code,city_name,district_code,district_name FROM unicom_post_city_code  GROUP BY province_code,province_name,city_code,city_name,district_code,district_name";
		List<Object[]> l = unicomCityService.getNativeResultList(sql);
		List<UnicomPostCityCode> resultList = StreamEx.of(l).map(r -> {
			UnicomPostCityCode b = new UnicomPostCityCode();
			b.setProvince_code((String) r[0]);
			b.setProvince_name((String) r[1]);
			b.setCity_code((String) r[2]);
			b.setCity_name((String) r[3]);
			b.setId((String) r[4]);
			b.setDistrict_name((String) r[5]);
			return b;
		}).toList();
		return resultList;
	}

	public List<UnicomPostCityCode> cityDetail(String provinceCode, String cityCode) {
		String sql = null;
		if (StringUtils.trimToNull(provinceCode) != null && StringUtils.trimToNull(cityCode) != null) {
			sql = "SELECT province_code,province_name,city_code,city_name,district_code,district_name FROM unicom_post_city_code where province_code='"+provinceCode+"' and city_code='"+cityCode+"'  GROUP BY province_code,province_name,city_code,city_name,district_code,district_name";
		}
		if(StringUtils.trimToNull(provinceCode) != null&&StringUtils.trimToNull(cityCode) == null) {
			sql = "SELECT province_code,province_name,city_code,city_name,district_code,district_name FROM unicom_post_city_code where province_code='"+provinceCode+"' GROUP BY province_code,province_name,city_code,city_name,district_code,district_name";
		}
		if (StringUtils.trimToNull(provinceCode) == null && StringUtils.trimToNull(cityCode) == null) {
		sql = "SELECT province_code,province_name,city_code,city_name,district_code,district_name FROM unicom_post_city_code  GROUP BY province_code,province_name,city_code,city_name,district_code,district_name";
		}
		List<Object[]> l = unicomCityService.getNativeResultList(sql);
		List<UnicomPostCityCode> resultList = StreamEx.of(l).map(r -> {
			UnicomPostCityCode b = new UnicomPostCityCode();
			b.setProvince_code((String) r[0]);
			b.setProvince_name((String) r[1]);
			b.setCity_code((String) r[2]);
			b.setCity_name((String) r[3]);
			b.setId((String) r[4]);
			b.setDistrict_name((String) r[5]);
			return b;
		}).toList();
		return resultList;
	}

	public List<UnicomPostCityCode> findBycity(String province_code) {
		String sql = "select  city_code,city_name from unicom_post_city_code where province_code='" + province_code
				+ "' group by city_code,city_name";
		List<Object[]> l = unicomCityService.getNativeResultList(sql);
		List<UnicomPostCityCode> resultList = StreamEx.of(l).map(r -> {
			UnicomPostCityCode b = new UnicomPostCityCode();
			b.setCity_code((String) r[0]);
			b.setCity_name((String) r[1]);
			return b;
		}).toList();
		return resultList;
	}

	public List<UnicomPostCityCode> qryDistrict(String city_code) {
		String sql = "select  district_code,district_name from unicom_post_city_code where city_code='" + city_code
				+ "' group by district_code,district_name";
		List<Object[]> l = unicomCityService.getNativeResultList(sql);
		List<UnicomPostCityCode> resultList = StreamEx.of(l).map(r -> {
			UnicomPostCityCode b = new UnicomPostCityCode();
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
	public Object qryPhonesNum(String phoneNum, String procductCode, String procductName) {
		String url = "http://59.110.18.76:8888/kplan/kcqapi/selectNumLastNUm?provinceCode=81&cityCode=810&searchCategory=3&goodsId="
				+ procductCode + "&amounts=2&searchType=02&searchValue=" + phoneNum;
		String[] result = HttpUtils.doGet(url, 6000);
		List<KplanPhoneNumber> l = new ArrayList<KplanPhoneNumber>();
		if ("SUCCEESS".equals(result[0])) {
			JSONObject dataJson = JSONObject.parseObject(result[1]);
			String status = dataJson.getString("status");
			String code = dataJson.getString("code");
			if ("200".equals(status) && "200".equals(status)) {
				JSONObject datasJson = dataJson.getJSONObject("data");
				String dataStr = datasJson.getString("phoNe");
				// 联通获取的电话号码转换为号码集合
				List<PhoneRuleResult> phoneResult = PhoneRuleUtils.ordersToPhone(dataStr);
				if (phoneResult != null && phoneResult.size() > 0) {
					for (PhoneRuleResult p : phoneResult) {
						KplanPhoneNumber ph = (KplanPhoneNumber) this.unicomCityService.getById(p.getPhone(),
								KplanPhoneNumber.class);
						if (ph == null) {
							KplanPhoneNumber k = new KplanPhoneNumber();
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
							k.setProduct_code(procductCode);
							k.setProduct_name(procductName);
							k.setPriority_name(1);
							if(l.size()<2) {
							l.add(k);
							}
							this.unicomCityService.add(k);
							;
						} else {
							KplanPhoneNumber k = new KplanPhoneNumber();
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
							k.setProduct_code(procductCode);
							k.setProduct_name(procductName);
							k.setPriority_name(1);
							if(l.size()<2) {
							l.add(k);
							}
						}
					}
					return l;
				} else {
					errorMsg msg = new errorMsg();
					msg.setStatus("201");
					msg.setMsg("尾号匹配不到可用号码");
					return JSON.toJSON(msg);
				}
			}
		}
		errorMsg msg = new errorMsg();
		msg.setStatus("201");
		msg.setMsg("尾号匹配不到可用号码");
		return JSON.toJSON(msg);

	}

	public UnicomPostCityCode findById(String dirsCode) {
		String sql = "from UnicomPostCityCode where id='" + dirsCode.trim() + "'";
		List<UnicomPostCityCode> l = this.unicomCityService.getResultList(sql);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}
}
