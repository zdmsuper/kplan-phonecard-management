package com.kplan.phonecard.manager;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kplan.phonecard.domain.BasicUserInfo;
import com.kplan.phonecard.service.BasicUserInfoService;

@Component
public class BasicUserInfoManager extends BaseManager {
	@Autowired
	BasicUserInfoService basicUserInfoService;

//	public ResultMessageDTO<CategoryInfo> saveOrUpdate(CategoryInfoDTO parentDto, @NotNull CategoryInfoDTO dto) {
//
//		return super.run(() -> {
//			super.checkArgs(dto);
//			if (Objects.isNull(dto.getId())) {
//				return this.add(parentDto, dto);
//			} else {
//				return this.update(parentDto, dto);
//			}
//
//		});
//
//	}
//
//	private ResultMessageDTO<CategoryInfo> add(CategoryInfoDTO parentDto, @NotNull CategoryInfoDTO dto) {
//		return super.run(() -> {
//			super.checkArgs(dto);
//			CategoryInfo parent = null;
//			if (parentDto != null && parentDto.getId() != null) {
//				parent = this.categoryInfoService.findByIdOrException(parentDto.getId());
//			}
//			CategoryInfo categoryInfo = super.mapper.map(dto, CategoryInfo.class);
//			categoryInfo.setParent(parent);
//			this.categoryInfoService.insert(categoryInfo);
//			return ResultMessageDTO.success(categoryInfo);
//		});
//
//	}
//
//	private ResultMessageDTO<CategoryInfo> update(CategoryInfoDTO parentDto, @NotNull CategoryInfoDTO dto) {
//		return super.run(() -> {
//			super.checkArgs(dto);
//			CategoryInfo categoryInfo = this.categoryInfoService.findByIdOrException(dto.getId());
//			categoryInfo.setName(dto.getName());
//			categoryInfo.setRemark(dto.getRemark());
//			this.categoryInfoService.update(categoryInfo);
//			return ResultMessageDTO.success(categoryInfo);
//		});
//
//	}
//	@Transactional(readOnly = true)
//	public List<CategoryInfo> findCategoryInfos(CategoryLevelEnum level) {
//		if (Objects.isNull(level)) {
//			return Collections.emptyList();
//		}
//		CategoryInfo example = new CategoryInfo();
//		example.setLevel(level);
//		return this.categoryInfoService.findAll(example, Pageable.unpaged()).getContent();
//	}
	@Transactional(readOnly = true)
	public Optional<BasicUserInfo> findById(Long id) {
		return this.basicUserInfoService.findById(id);
	}
	

	
//	@Transactional(readOnly = true)
//	public Page<CategoryInfo> findCategoryInfos(CategoryInfoQuery queryVo, Pageable pageable) {
//
//		Specification<CategoryInfo> spec = (Root<CategoryInfo> root, CriteriaQuery<?> query,
//				CriteriaBuilder builder) -> {
//			Map<String, Predicate> predMap = Collections.emptyMap();// super.buildEqualPredicates(root, query, builder,
//																	// queryVo);
//			return builder.and(predMap.values().toArray(new Predicate[0]));
//		};
//		return this.categoryInfoService.findAll(spec, pageable);
//	}
}
