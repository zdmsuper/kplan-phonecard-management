package com.kplan.phonecard.query;

import java.lang.reflect.ParameterizedType;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.kplan.phonecard.base.BaseDomain;

public abstract class BaseQuery<T extends BaseDomain> {
	private Long currentShopId;
	
	private T domain;

	public BaseQuery() {
		try {
			this.domain = this.genericType().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

	}

	private Class<T> genericType() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> beanClass = (Class<T>) type.getActualTypeArguments()[0];
		return beanClass;
	}

	private String keyword;
	private Boolean firstEntry = Boolean.TRUE;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDateStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDateEnd;

	public Date getCreatedDateStart() {
		if (this.createdDateStart != null) {
			this.createdDateStart = Date.from(this.createdDateStart.toInstant().atZone(ZoneId.systemDefault())
					.withHour(0).withMinute(0).withSecond(0).toInstant());
		}
		return createdDateStart;
	}

	public void setCreatedDateStart(Date createdDateStart) {
		this.createdDateStart = createdDateStart;
	}

	public Date getCreatedDateEnd() {
		if (this.createdDateEnd != null) {
			this.createdDateEnd = Date.from(this.createdDateEnd.toInstant().atZone(ZoneId.systemDefault()).withHour(23)
					.withMinute(59).withSecond(59).toInstant());
		}
		return createdDateEnd;
	}

	public void setCreatedDateEnd(Date createdDateEnd) {
		this.createdDateEnd = createdDateEnd;
	}

	public Boolean getFirstEntry() {
		return firstEntry;
	}

	public void setFirstEntry(Boolean firstEntry) {
		this.firstEntry = firstEntry;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getKeywordStartMatch(){
		if (StringUtils.isNotBlank(this.keyword)) {
			return  StringUtils.trimToEmpty(this.keyword) + "%";
		}
		return "";

	}

	public String getKeywordEndPattern() {
		if (StringUtils.isNotBlank(this.keyword)) {
			return "%" + StringUtils.trimToEmpty(this.keyword) ;
		}
		return "";
	}

	public String getKeywordMiddlePattern() {
		if (StringUtils.isNotBlank(this.keyword)) {
			return "%" + StringUtils.trimToEmpty(this.keyword) + "%";
		}

		return "";
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public T getDomain() {
		return this.domain;
	}

	public void setDomain(T domain) {
		this.domain = domain;
	}

	public Long getCurrentShopId() {
		return currentShopId;
	}

	public void setCurrentShopId(Long currentShopId) {
		this.currentShopId = currentShopId;
	}
	
}
