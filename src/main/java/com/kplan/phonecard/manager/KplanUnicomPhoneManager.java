package com.kplan.phonecard.manager;

import java.util.ArrayList;
import java.util.Date;
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
import com.kplan.phonecard.domain.msgRes;
import com.kplan.phonecard.enums.MakePhoneStatusEnum;
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
	
	/**文件上传
	 * @param data
	 * @return
	 */
	public String uploadFile(List<Object> data) {
		msgRes msg = new msgRes();
		if(data!=null&&data.size()>0) {
			KplanUnicomPhone phone;
			try {
				for(Object l:data) {
					List<String> p=(List<String>) l;
					phone=new KplanUnicomPhone();
					phone.setPhone(p.get(0));
					phone.setSection_no(p.get(1));
					phone.setMiddle_secition_no(p.get(2));
					phone.setInclude_four(p.get(3));
					phone.setRule_name(p.get(4));
					phone.setCrea_date(new Date());
					phone.setMakestatus(MakePhoneStatusEnum.make);
					this.kplanUnicomPhoneService.add(phone);
				}
				
			} catch (Exception e) {
				msg.setCode("201");
				msg.setStatus("201");
				msg.setMsg("号码入库异常、请联系管理员");
				return JSON.toJSONString(msg);
			}
		}
		msg.setCode("200");
		msg.setStatus("200");
		msg.setMsg("号码上传成功");
		return JSON.toJSONString(msg);
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
	
	/**号码已使用锁定
	 * @param id
	 * @return
	 */
	public Object lockPhone(Long id) {
		msgRes msg = new msgRes();
		
		KplanUnicomPhone p=this.kplanUnicomPhoneService.findByIdOrNew(id);
		if(p!=null) {
			p.setMakestatus(MakePhoneStatusEnum.makeNot);
			try {
				this.kplanUnicomPhoneService.update(p);
				msg.setCode("200");
				msg.setStatus("200");
				msg.setMsg("号码操作成功");
			} catch (Exception e) {
				msg.setCode("299");
				msg.setStatus("299");
				msg.setMsg("号码号码更新错误");
			}
		}else {
			msg.setCode("201");
			msg.setStatus("201");
			msg.setMsg("查找不到号码");
		}
		
		return JSON.toJSON(msg);
	}
	
	/**号码已使用解锁
	 * @param id
	 * @return
	 */
	public Object UnlockPhone(Long id) {
		msgRes msg = new msgRes();
		KplanUnicomPhone p=this.kplanUnicomPhoneService.findByIdOrNew(id);
		if(p!=null) {
			p.setMakestatus(MakePhoneStatusEnum.make);
			try {
				this.kplanUnicomPhoneService.update(p);
				msg.setCode("200");
				msg.setStatus("200");
				msg.setMsg("号码操作成功");
			} catch (Exception e) {
				msg.setCode("299");
				msg.setStatus("299");
				msg.setMsg("号码号码更新错误");
			}
		}else {
			msg.setCode("201");
			msg.setStatus("201");
			msg.setMsg("查找不到号码");
		}
		
		return JSON.toJSON(msg);
	}
}
