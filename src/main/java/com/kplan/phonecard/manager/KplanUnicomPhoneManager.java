package com.kplan.phonecard.manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kplan.phonecard.domain.KplanSecondaryOrders;
import com.kplan.phonecard.domain.KplanUnicomPhone;
import com.kplan.phonecard.domain.UnicomPostCityCode;
import com.kplan.phonecard.query.KplanUnicomPhoneQuery;
import com.kplan.phonecard.service.KplanUnicomPhoneService;
import com.sun.istack.NotNull;

import one.util.streamex.StreamEx;

@Component
@Transactional
public class KplanUnicomPhoneManager extends BaseManager {
	@Autowired
	KplanUnicomPhoneService kplanUnicomPhoneService;
	/**获取联通官方号码库
	 * @param query
	 * @param pageable
	 * @return
	 */
	public Page<KplanUnicomPhone> list(@NotNull KplanUnicomPhoneQuery query, Pageable pageable) {
		Specification<KplanUnicomPhone> spec = new Specification<KplanUnicomPhone>() {
			@Override
			public Predicate toPredicate(Root<KplanUnicomPhone> r, CriteriaQuery<?> qr, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (query.getCreatedDateStart() != null && query.getCreatedDateEnd() != null) {
					list.add(cb.between(r.get("crea_date"), query.getCreatedDateStart(),
							query.getCreatedDateEnd()));
				}
				if(query.getDomain().getMakestatus()!=null) {
					list.add(cb.equal(r.get("makestatus"),query.getDomain().getMakestatus().getCode()));
				}
				if(query.getDomain().getSection_no()!=null) {
					list.add(cb.equal(r.get("section_no"), query.getDomain().getSection_no()));
				}
				if(query.getDomain().getMiddle_secition_no()!=null) {
					list.add(cb.equal(r.get("middle_secition_no"), query.getDomain().getMiddle_secition_no()));
				}
				if(query.getDomain().getInclude_four()!=null) {
					list.add(cb.equal(r.get("include_four"), query.getDomain().getInclude_four()));
				}
				if(query.getDomain().getPhone_num()!=null) {
					list.add(cb.equal(r.get("phone_num"), query.getDomain().getPhone_num()));
				}
				if(query.getDomain().getRule_name()!=null) {
					list.add(cb.equal(r.get("rule_name"), query.getDomain().getRule_name()));
				}
				if(query.getKeyword()!=null) {
					list.add(cb.or(cb.equal(r.get("phone"), query.getKeyword()),cb.equal(r.get("phone_num"), query.getKeyword())));
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
	
	return this.kplanUnicomPhoneService.findAll(spec, pageable);
	}
	
	/**取所有号段
	 * @return
	 */
	public List<KplanUnicomPhone> qrySectionNo(){
		String sql="select section_no from kplan_unicom_phone group by section_no order by section_no ";
		List<Object> l=this.kplanUnicomPhoneService.getNativeResultList(sql);
		List<KplanUnicomPhone> resultList = StreamEx.of(l).map(r -> {
			KplanUnicomPhone b = new KplanUnicomPhone();
			b.setSection_no(String.valueOf(r));
			return b;
		}).toList();
		return resultList;
	}
	
	
	/**查询规则
	 * @param section_no
	 * @return
	 */
	public List<KplanUnicomPhone> qryRuleNameList(String section_no) {
		String sql="select rule_name from kplan_unicom_phone where section_no='"+section_no+"'  group by rule_name  ";
		List<Object> l=this.kplanUnicomPhoneService.getNativeResultList(sql);
		List<KplanUnicomPhone> resultList = StreamEx.of(l).map(r -> {
			KplanUnicomPhone b = new KplanUnicomPhone();
			b.setRule_name(String.valueOf(r));
			return b;
		}).toList();
		return resultList;
	}
	/**根据号段获取号段
	 * @param section_no
	 * @return
	 */
	public Object qryRuleName(String section_no) {
		String sql="select rule_name from kplan_unicom_phone where section_no='"+section_no+"'  group by rule_name  ";
		List<Object> l=this.kplanUnicomPhoneService.getNativeResultList(sql);
		List<KplanUnicomPhone> resultList = StreamEx.of(l).map(r -> {
			KplanUnicomPhone b = new KplanUnicomPhone();
			b.setRule_name(String.valueOf(r));
			return b;
		}).toList();
		return JSON.toJSON(resultList);
	}
}
