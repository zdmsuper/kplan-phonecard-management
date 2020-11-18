package com.kplan.phonecard.controller;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.dto.ManagerInfoDTO;
import com.kplan.phonecard.enums.GenderEnum;
import com.kplan.phonecard.enums.UserStatusEnum;
import com.kplan.phonecard.manager.ManagerInfoManager;
import com.kplan.phonecard.query.ManagerInfoQuery;
import com.kplan.phonecard.service.ManagerInfoService;
import com.kplan.phonecard.utils.MultiPartFileUtil;
import com.kplan.phonecard.vo.ResultMessageDTO;

@Controller
@RequestMapping("/managerInfo")
public class ManagerInfoController extends AbstractBaseController {
	private static final Logger logger = LoggerFactory.getLogger(ManagerInfoController.class);
	@Autowired
	ManagerInfoManager managerInfoManager;
	@Autowired
	ManagerInfoService managerInfoService;
	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping("/list")
	public String list(Map<String, Object> map, ManagerInfoQuery query) {
		try {
			this.getCurrentUserDetails().ifPresent(cu -> {
				query.getNotinIdList().add(cu.getId());
			});

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Page<ManagerInfo> page = this.managerInfoManager.findMemberInfos(query, this.getPageRequest());

		map.put("query", query);
		map.put("page", page);
		return "managerInfo/list";
	}

	@RequestMapping("/edit")
	public String edit(Map<String, Object> map, ManagerInfoQuery query) {
		ManagerInfo managerInfo = this.managerInfoService.findByIdOrNew(query.getDomain().getId());
		if (Objects.isNull(managerInfo.getBasicUserInfo().getGender())) {
			managerInfo.getBasicUserInfo().setGender(GenderEnum.MALE);
		}
		map.put("item", managerInfo);
		return "managerInfo/edit";
	}

	/**
	 * 用户停用 Mr.Zhang
	 * 
	 * @param map
	 * @param dto
	 * @return
	 */
	@RequestMapping("/editInfo")
	@ResponseBody
	public ResultMessageDTO<ManagerInfo> editInfo(Map<String, Object> map, @Validated ManagerInfoDTO dto) {
		if (this.getCurrentUserDetails().orElse(new ManagerInfo()).getId() == dto.getId()) {
			return ResultMessageDTO.failed("不能改变当前登录用户状态");
		}
		ManagerInfo managerInfo = this.managerInfoService.findByIdOrException(dto.getId());
		managerInfo.setUserStatus(UserStatusEnum.LOCKED);
		this.managerInfoService.update(managerInfo);
		return ResultMessageDTO.success();
	}

	/**
	 * 用户启用 Mr.Zhang
	 * 
	 * @param map
	 * @param dto
	 * @return
	 */
	@RequestMapping("/enableInfo")
	@ResponseBody
	public ResultMessageDTO<ManagerInfo> enableInfo(Map<String, Object> map, @Validated ManagerInfoDTO dto) {
		if (this.getCurrentUserDetails().orElse(new ManagerInfo()).getId() == dto.getId()) {
			return ResultMessageDTO.failed("不能改变当前登录用户状态");
		}
		ManagerInfo managerInfo = this.managerInfoService.findByIdOrException(dto.getId());
		managerInfo.setUserStatus(UserStatusEnum.VALID);
		this.managerInfoService.update(managerInfo);
		return ResultMessageDTO.success();
	}

	@RequestMapping("/show")
	public String show(Map<String, Object> map, ManagerInfoQuery query) {
		ManagerInfo managerInfo = this.managerInfoService.findById(query.getDomain().getId()).orElse(new ManagerInfo());
		map.put("item", managerInfo);
		return "managerInfo/show";
	}

	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public ResultMessageDTO<ManagerInfo> saveOrUpdate(Map<String, Object> map, @Validated ManagerInfoDTO dto,
			@RequestParam(name = "headImg") MultipartFile headImg) {
		try {
			String base64HeadImg = MultiPartFileUtil.imageFileToBase64Str(headImg).orElse(null);
			dto.setBase64HeadImg(base64HeadImg);
			dto.setLoginPwd(this.encodePassword(dto));
			return this.managerInfoManager.saveOrUpdate(dto).withoutData();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultMessageDTO.failed("服务端出错");
		}
	}

	private String encodePassword(ManagerInfoDTO dto) {
		String pwd = StringUtils.trimToNull(dto.getLoginPwd());
		if (pwd != null) {
			return this.passwordEncoder.encode(pwd);
		}

		return "";

	}
}
