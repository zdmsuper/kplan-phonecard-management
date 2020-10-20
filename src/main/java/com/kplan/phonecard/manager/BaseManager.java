package com.kplan.phonecard.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.dozermapper.core.Mapper;
import com.kplan.phonecard.base.BaseDomain;
import com.kplan.phonecard.exceptions.BusinessException;
import com.kplan.phonecard.interfaces.BusinessAction;
import com.kplan.phonecard.query.BaseQuery;
import com.kplan.phonecard.vo.ResultMessageDTO;

/**
 * 基础Manager类,提供对参数及相关对象进行检查的通用方法
 * 
 * @author wangguofeng
 *
 */
@Transactional
public abstract class BaseManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseManager.class);
	@Autowired
	protected Mapper mapper;

	

	protected <E extends BaseDomain> List<Predicate> buildDateRangePreds(Root<E> root, CriteriaQuery<?> cq,
			CriteriaBuilder cb, BaseQuery<E> query) {
		List<Predicate> list = new ArrayList<>();
		if (Objects.nonNull(query.getCreatedDateStart())) {
			list.add(cb.greaterThanOrEqualTo(root.get("createdDate"), query.getCreatedDateStart()));
		}
		if (Objects.nonNull(query.getCreatedDateEnd())) {
			list.add(cb.lessThanOrEqualTo(root.get("createdDate"), query.getCreatedDateEnd()));

		}
		return list;

	}

	protected <T> ResultMessageDTO<T> run(BusinessAction<T> action) {
		try {
			ResultMessageDTO<T> result = action.action();
			return result;
		} catch (BusinessException e) {
			return ResultMessageDTO.failed(e.getMessage());
		}

	}

	/**
	 * 检查参数是否为null,或者blank
	 * 
	 * @param objs
	 * @return
	 */
	protected boolean checkArgs(Object... objs) {
		if (objs != null) {
			for (Object obj : objs) {
				if (Objects.isNull(obj) || StringUtils.isBlank(String.valueOf(obj))) {
					throw new BusinessException("参数不能为空");
				}
			}
		}
		return true;

	}

	protected <T extends BaseDomain> Map<String, Predicate> buildEqualPredicates(Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder builder, BaseQuery<T> bean) {
		Map<String, Predicate> resultMap = new HashMap<String, Predicate>();
		if (bean == null || bean.getDomain() == null) {
			return resultMap;
		}
		BaseDomain domain = bean.getDomain();

		BeanMap domainBeanMap = new BeanMap(domain);

		for (Iterator<String> ite = domainBeanMap.keyIterator(); ite.hasNext();) {
			String name = ite.next();
			Class<?> type = domainBeanMap.getType(name);
			Object value = domainBeanMap.get(name);
			if (value != null && !(value instanceof BaseDomain) && !name.equals("class")) {
				Expression<?> expre = root.get(name).as(type);
				Predicate pred = builder.equal(expre, value);
				resultMap.put(name, pred);
			}

		}
		return resultMap;

	}

}
