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
				if(query.getCreatedDateStart()!=null&&query.getCreatedDateEnd()!=null) {
					return cb.between(r.get("creadDate"), query.getCreatedDateStart(), query.getCreatedDateEnd());
				}else {
				return cb.and();
				}
			}
		};
		return this.channelService.findAll(spec,pageable);
	}
}
