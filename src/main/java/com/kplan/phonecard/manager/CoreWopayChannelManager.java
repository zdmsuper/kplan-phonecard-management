package com.kplan.phonecard.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kplan.phonecard.domain.CoreWopayChannel;
import com.kplan.phonecard.domain.msgRes;
import com.kplan.phonecard.query.CoreWopayChannelQuery;
import com.kplan.phonecard.service.CoreWopayChannelService;

@Component
@Transactional
public class CoreWopayChannelManager extends BaseManager{
	@Autowired
	CoreWopayChannelService coreWopayChannelService;
	
	
	public Page<CoreWopayChannel> qryJobList(@NotNull CoreWopayChannelQuery query, Pageable pageable){
Specification<CoreWopayChannel> spec=new Specification<CoreWopayChannel>() {
			
			@Override
			public Predicate toPredicate(Root<CoreWopayChannel> r, CriteriaQuery<?> qr,
					CriteriaBuilder cb) {
//				cb.equal(r.get("id"), 23L)
				List<Predicate> list = new ArrayList<>();
				if(query.getCreatedDateStart()!=null&&query.getCreatedDateEnd()!=null) {
					list.add( cb.between(r.get("create_time"), query.getCreatedDateStart(), query.getCreatedDateEnd()));
					
				}
				if(StringUtils.trimToNull(query.getKeyword())!=null) {
					list.add(cb.and(cb.or(cb.equal(r.get("channel_code"), query.getKeyword()),
							cb.equal(r.get("operator"), query.getKeyword()),cb.equal(r.get("job_num"), query.getKeyword()))));
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return this.coreWopayChannelService.findAll(spec,pageable);
	}
	public Object savaBankJob(String jobnum,String branch,String channelnature,String operator,String channelcode) {
		msgRes msg=new msgRes();
		CoreWopayChannel c=new CoreWopayChannel();
		try {
			c.setJob_num(jobnum);
			c.setBranch(branch);
			c.setChannel_nature(channelnature);
			c.setOperator(operator);
			c.setChannel_code(channelcode);
			c.setCreate_time(new Date());
			this.coreWopayChannelService.add(c);
			msg.setCode("200");
			msg.setStatus("200");
			msg.setMsg("工号添加成功");
		} catch (Exception e) {
			msg.setCode("222");
			msg.setStatus("222");
			msg.setMsg("工号添加异常请联系管理员");
		}
		return JSON.toJSON(msg);
	}
	
	public Object del(String jobId) {
		msgRes msg=new msgRes();
		try {
			coreWopayChannelService.deleteById(Long.parseLong(jobId));
//			this.coreWopayChannelService.removeById(jobId, CoreWopayChannel.class);
			msg.setCode("200");
			msg.setStatus("200");
			msg.setMsg("工号删除成功");
		} catch (Exception e) {
			msg.setCode("222");
			msg.setStatus("222");
			msg.setMsg("工号删除异常请联系管理员");
		}
		return JSON.toJSON(msg);
	}
}
