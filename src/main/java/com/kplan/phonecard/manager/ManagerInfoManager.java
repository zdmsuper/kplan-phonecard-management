package com.kplan.phonecard.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kplan.phonecard.domain.BasicUserInfo;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.dto.ManagerInfoDTO;
import com.kplan.phonecard.enums.UserStatusEnum;
import com.kplan.phonecard.exceptions.BusinessException;
import com.kplan.phonecard.query.ManagerInfoQuery;
import com.kplan.phonecard.service.BasicUserInfoService;
import com.kplan.phonecard.service.ManagerInfoService;
import com.kplan.phonecard.vo.ResultMessageDTO;
import com.kplan.phonecard.web.annotation.MultiRequestBody;

import de.cronn.reflection.util.TypedPropertyGetter;

/**
 * 
 * @author wangguofeng
 *
 */
@Component
@Transactional
public class ManagerInfoManager extends BaseManager {
	@Autowired
	ManagerInfoService managerInfoService;
	@Autowired
	BasicUserInfoService basicUserInfoService;

	public ResultMessageDTO<ManagerInfo> saveOrUpdate(@NotNull @MultiRequestBody ManagerInfoDTO dto) {

		return super.run(() -> {
			// 检查数据是否为空
			super.checkArgs(dto);
			// 检查数据是否为空

			// 检查相同手机号码


			if (Objects.isNull(dto.getId())) {
				ManagerInfo managerInfo = this.mapper.map(dto, ManagerInfo.class);
				managerInfo.getBasicUserInfo().setCredentialId(UUID.randomUUID().toString());
				managerInfo.setUserStatus(UserStatusEnum.VALID);
				if (Objects.nonNull(dto.getBase64HeadImg())) {
					managerInfo.getBasicUserInfo().setHeadImg(dto.getBase64HeadImg());
				}
				
				this.basicUserInfoService.insert(managerInfo.getBasicUserInfo());
				this.managerInfoService.insert(managerInfo);
				return ResultMessageDTO.success(managerInfo);
			} else {
				ManagerInfo managerInfo = this.managerInfoService.findByIdOrException(dto.getId());
				if(Objects.nonNull(dto.getLoginPwd())) {
					managerInfo.setLoginPwd(dto.getLoginPwd());
				}
				this.basicUserInfoService.update(managerInfo.getBasicUserInfo());
				this.managerInfoService.update(managerInfo);
				return ResultMessageDTO.success(managerInfo);
			}

		});

	}

	@Transactional(readOnly = true)
	public long count(@NotNull ManagerInfoQuery query) {

		Specification<ManagerInfo> spec = (Root<ManagerInfo> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {

			List<Predicate> list = new ArrayList<>();
			if (Objects.nonNull(query)) {
				list.addAll(super.buildDateRangePreds(root, cq, cb, query));

				if (StringUtils.isNotBlank(query.getKeyword())) {
					String keyword = query.getKeywordStartMatch();
					list.add(cb.or(cb.like(root.get("basicUserInfo").get("userRealName"), keyword),
							cb.like(root.get("basicUserInfo").get("email"), keyword),
							cb.like(root.get("basicUserInfo").get("phone"), keyword)));

				}
				if (query.getDomain() != null) {
					ManagerInfo managerInfo = query.getDomain();
					if (managerInfo.getId() != null) {
						list.add(cb.equal(root.get("id"), managerInfo.getId()));
					}
				}
			}

			return cb.and(list.toArray(new Predicate[0]));
//			return builder.and(predMap.values().toArray(new Predicate[0]));
		};

		return this.managerInfoService.count(spec);

	}

	/**
	 * 查询管理员信息
	 * 
	 * @param memberInfoDTO
	 * @param shopInfos
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<ManagerInfo> findMemberInfos(@NotNull ManagerInfoQuery query, Pageable pageable) {

		Specification<ManagerInfo> spec = (Root<ManagerInfo> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {

			List<Predicate> list = new ArrayList<>();
			if (Objects.nonNull(query)) {
				list.addAll(super.buildDateRangePreds(root, cq, cb, query));

				if (StringUtils.isNotBlank(query.getKeyword())) {
					String keyword = "%"+query.getKeyword() + "%";
					list.add(cb.or(cb.like(root.get("basicUserInfo").get("userRealName"), keyword),
							cb.like(root.get("basicUserInfo").get("email"), keyword),
							cb.like(root.get("basicUserInfo").get("phone"), keyword)));

				}
				if (CollectionUtils.isNotEmpty(query.getNotinIdList())) {
					list.add(root.get("id").in(query.getNotinIdList()).not());
				}
				if (query.getDomain() != null) {
					ManagerInfo managerInfo = query.getDomain();
					if (managerInfo.getUserStatus() != null) {
						list.add(cb.equal(root.get("userStatus"), managerInfo.getUserStatus()));
					}

					if (managerInfo.getBasicUserInfo() != null) {
						Path<?> basicUserInfoExp = root.get("basicUserInfo");
						BasicUserInfo basicUserInfo = managerInfo.getBasicUserInfo();
						if (StringUtils.isNoneEmpty(basicUserInfo.getEmail())) {
							list.add(cb.equal(basicUserInfoExp.get("email"), basicUserInfo.getEmail()));
						}

						if (StringUtils.isNoneEmpty(basicUserInfo.getLoginId())) {
							list.add(cb.equal(basicUserInfoExp.get("loginId"), basicUserInfo.getLoginId()));
						}

						if (StringUtils.isNoneEmpty(basicUserInfo.getPhone())) {
							list.add(cb.equal(basicUserInfoExp.get("phone"), basicUserInfo.getPhone()));
						}
						if (basicUserInfo.getGender() != null) {
							list.add(cb.equal(basicUserInfoExp.get("gender"), basicUserInfo.getGender()));
						}

					}

				}

			}
//			if (Objects.nonNull(managerInfoQueryVO) && Objects.nonNull(managerInfoQueryVO.getDomain())) {
//				ManagerInfo managerInfo = managerInfoQueryVO.getDomain();
//				Method method = PropertyUtils.getMethod(managerInfo.getClass(), ManagerInfo::getPhone);
//				PropertyDescriptor propertyDescriptor = PropertyUtils
//						.getPropertyDescriptorByMethod(managerInfo.getClass(), method);
//				TT<ManagerInfo> tt = new TT<>(ManagerInfo.class);
//				String name = tt.getPropName(ManagerInfo::getPhone);
//
//				if (StringUtils.isNotBlank(managerInfo.getPhone())) {
//					Expression<String> exp = root.get("phone").as(String.class);
//					predMap.put("phone", builder.like(exp, managerInfo.getPhone() + "%"));
//				}
//				if (StringUtils.isNotBlank(managerInfo.getEmail())) {
//					Expression<String> exp = root.get("email").as(String.class);
//					predMap.put("email", builder.like(exp, managerInfo.getEmail() + "%"));
//				}
//				if (StringUtils.isNotBlank(managerInfo.getUserRealName())) {
//					Expression<String> exp = root.get("userRealName").as(String.class);
//					predMap.put("userRealName", builder.like(exp, managerInfo.getUserRealName() + "%"));
//				}
//				if (StringUtils.isNotBlank(managerInfo.getHomeAddress())) {
//					Expression<String> exp = root.get("homeAddress").as(String.class);
//					predMap.put("homeAddress", builder.like(exp, managerInfo.getHomeAddress() + "%"));
//				}
//				if (StringUtils.isNotBlank(managerInfo.getCurrentAddress())) {
//					Expression<String> exp = root.get("currentAddress").as(String.class);
//					predMap.put("currentAddress", builder.like(exp, managerInfo.getCurrentAddress() + "%"));
//				}
//				if (StringUtils.isNotBlank(managerInfo.getRemark())) {
//					Expression<String> exp = root.get("remark").as(String.class);
//					predMap.put("remark", builder.like(exp, managerInfo.getRemark() + "%"));
//				}
//
//			}
			return cb.and(list.toArray(new Predicate[0]));
//			return builder.and(predMap.values().toArray(new Predicate[0]));
		};
		return this.managerInfoService.findAll(spec, pageable);

	}

	public static class TT<T> {
		private Class<T> beanClass;

		public TT(Class<T> beanClass) {
			this.beanClass = beanClass;
		}

		public String getPropName(TypedPropertyGetter<T, ?> propertyGetter) {
			return null;
		}

		public Class getPropType(TypedPropertyGetter<T, ?> propertyGetter) {
			return null;
		}

	}

}
