package com.kplan.phonecard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kplan.phonecard.domain.KplanChannelNumberDetail;
import com.kplan.phonecard.domain.ManagerInfo;

@Service
public class ChannelService extends BaseService<KplanChannelNumberDetail> {

	public ChannelService() {
		super("");
	}
	public Page<KplanChannelNumberDetail> findAll(Specification<KplanChannelNumberDetail> spec, Pageable pageable) {
		return super.getDao().findAll(spec, pageable);
	}
}
