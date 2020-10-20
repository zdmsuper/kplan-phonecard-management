package com.kplan.phonecard.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kplan.phonecard.domain.ManagerInfo;

@Service
public class ManagerInfoService extends BaseService<ManagerInfo> {

	public ManagerInfoService() {
		super("未能查到管理员信息");
	}

	public Page<ManagerInfo> findAll(Specification<ManagerInfo> spec, Pageable pageable) {
		return super.getDao().findAll(spec, pageable);
	}

	public Page<ManagerInfo> findAll(ManagerInfo memberInfo, Pageable pageable) {
		Example<ManagerInfo> e = Example.of(memberInfo);
		return super.getDao().findAll(e, pageable);
	}

}
