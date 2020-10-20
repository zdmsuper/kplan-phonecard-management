package com.kplan.phonecard.service;

import org.springframework.stereotype.Service;

import com.kplan.phonecard.domain.BasicUserInfo;

@Service
public class BasicUserInfoService extends BaseService<BasicUserInfo> {

	public BasicUserInfoService() {
		super("未能找到用户信息");
	}

}
