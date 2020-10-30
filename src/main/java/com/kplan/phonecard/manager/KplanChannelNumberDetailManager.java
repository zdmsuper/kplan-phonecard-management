package com.kplan.phonecard.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kplan.phonecard.domain.KplanChannelNumberDetail;
import com.kplan.phonecard.domain.resList;
import com.kplan.phonecard.query.KplanChannelNumberDetailQuery;
import com.kplan.phonecard.service.ChannelService;
@Component
@Transactional
public class KplanChannelNumberDetailManager extends BaseManager {
	@Autowired
	ChannelService channelService;
	/**查询工号处理列表
	 * @param query
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<KplanChannelNumberDetail> findChannelInfos(@NotNull KplanChannelNumberDetailQuery query, Pageable pageable){
		
		Specification<KplanChannelNumberDetail> spec=new Specification<KplanChannelNumberDetail>() {
			
			@Override
			public Predicate toPredicate(Root<KplanChannelNumberDetail> r, CriteriaQuery<?> qr,
					CriteriaBuilder cb) {
//				cb.equal(r.get("id"), 23L)
				List<Predicate> list = new ArrayList<>();
				if(query.getCreatedDateStart()!=null&&query.getCreatedDateEnd()!=null) {
					list.add( cb.between(r.get("creadDate"), query.getCreatedDateStart(), query.getCreatedDateEnd()));
					
				}
//				if(query.getDomain().getDeliveryNum()==0) {
					list.add(cb.notEqual(r.get("deliveryNum"), 0));
//				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return this.channelService.findAll(spec,pageable);
	}
	
	public Object findList(@NotNull KplanChannelNumberDetailQuery query){
		String sql="select kplan_agent_name from kplan_channel_number_detail where cread_date>='"+query.getCreatedDateStart()+"' and cread_date<='"+query.getCreatedDateEnd()+"' and delivery_num!=0 group by kplan_agent_name";
		List<Object> l=channelService.getNativeResultList(sql);
		resList li = null;
		List<Object> reli = null;
		List<Object> reList=new ArrayList<Object>();
		if(l!=null&&l.size()>0) {
			for(Object d:l) {
				li=new resList();
				li.setName(String.valueOf(d));
				String sqlDetail="from KplanChannelNumberDetail where creadDate>='"+query.getCreatedDateStart()+"' and creadDate<='"+query.getCreatedDateEnd()+"' and  kplanAgentName='"+d+"'";
				List<KplanChannelNumberDetail> ld=channelService.getResultList(sqlDetail);
				if(ld!=null&&ld.size()>0) {
					reli=new ArrayList<Object>();
					for(KplanChannelNumberDetail  del:ld) {
						reli.add(del.getDeliveryNum());
					}
					li.setlNum(reli);
				}
				reList.add(li);
			}
		}
		return reList;
	}
}
