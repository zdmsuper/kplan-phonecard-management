package com.kplan.phonecard.service;

import org.springframework.stereotype.Service;

@Service
public class MemeberCardNoGenerationService {
	public String nexCardNo() {

		return String.valueOf(System.currentTimeMillis());

	}
}
