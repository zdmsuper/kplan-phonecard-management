package com.kplan.phonecard.controller;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.dto.UserDetailsDto;
import com.kplan.phonecard.service.ManagerInfoService;

public class AbstractBaseController {
	private static final Logger logger = LoggerFactory.getLogger(AbstractBaseController.class);
	// 直接注入也是可以的,但是不太容易理解
	@Autowired
	ManagerInfoService managerInfoService;
//	@Autowired
//	protected HttpServletRequest request;
//
//	@Autowired
//	protected HttpServletResponse response;

//	@InitBinder
//	protected void initBinder(WebDataBinder webDataBinder) {
//		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
//	}
	private ServletRequestAttributes getServletRequestAttributes() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
		return servletRequestAttributes;
		// HttpServletRequest request =servletRequestAttributes.getRequest();
		// HttpServletResponse response = servletRequestAttributes.getResponse();

	}

	protected HttpServletRequest getRequest() {
		return this.getServletRequestAttributes().getRequest();

	}

	protected HttpServletResponse getResponse() {
		return this.getServletRequestAttributes().getResponse();

	}

	protected Optional<ManagerInfo> getCurrentUserDetails() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetailsDto) {
			UserDetailsDto customerUserDetails = (UserDetailsDto) principal;
			return managerInfoService.findById(customerUserDetails.getId());

		} else {
			// System.out.println(principal.equals("anonymousUser"));
			return Optional.empty();
		}

	}

	protected PageRequest getPageRequest(Sort... sorts) {

		int pageIndex = this.parsePageIndex();
		int pageSize = this.parsePageSize();
		Sort sort = Sort.unsorted();
		if (sorts != null) {
			sort = Stream.of(sorts).reduce(sort, Sort::and);
		}

		PageRequest pageRequest = this.buildPageRequest(pageIndex, pageSize, sort);
		return pageRequest;

	}

	/**
	 * 获取分页请求
	 * 
	 * @return
	 */
	protected PageRequest getPageRequest() {
		int pageIndex = this.parsePageIndex();
		int pageSize = this.parsePageSize();
		Sort sort = null;
		try {
			String sortName = this.getParameter("sortName");
			String sortOrder = this.getParameter("sortOrder");
			if (StringUtils.isNoneBlank(sortName) && StringUtils.isNoneBlank(sortOrder)) {
				if (sortOrder.equalsIgnoreCase("desc")) {
					sort =  Sort.by(Direction.DESC, sortName);
				} else {
					sort =  Sort.by(Direction.ASC, sortName);
				}
			} else {
				sort =   Sort.by(Direction.DESC, "id");

			}

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		PageRequest pageRequest = this.buildPageRequest(pageIndex, pageSize, sort);
		return pageRequest;
	}
	
	
	protected PageRequest getPageRequests() {
		int pageIndex = this.parsePageIndex();
		int pageSize = this.parsePageSize();
		Sort sort = null;
		try {
			
			String sortName ="createtime";
			String sortOrder = "desc";
			if (StringUtils.isNoneBlank(sortName) && StringUtils.isNoneBlank(sortOrder)) {
				if (sortOrder.equalsIgnoreCase("desc")) {
					sort =  Sort.by(Direction.DESC, sortName);
				} else {
					sort =  Sort.by(Direction.ASC, sortName);
				}
			} else {
				sort =   Sort.by(Direction.DESC, "id");

			}

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		PageRequest pageRequest = this.buildPageRequest(pageIndex, pageSize, sort);
		return pageRequest;
	}

	private PageRequest buildPageRequest(Integer pageIndex, Integer pageSize, Sort sort) {
		PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, sort);
		return pageRequest;
	}

	private Integer parsePageIndex() {
		Integer pageIndex = 0;
		try {
			pageIndex = Integer.parseInt(this.getParameter("pageIndex"));
		} catch (Exception e) {
			pageIndex = 0;
		}
		return pageIndex;

	}

	private Integer parsePageSize() {
		Integer pageSize = 15;
		try {
			pageSize = Integer.parseInt(this.getParameter("pageSize"));
		} catch (Exception e) {
			pageSize = 15;
		}
		return pageSize;

	}

	/**
	 * 获取分页请求
	 * 
	 * @param sort 排序条件
	 * @return
	 */
	protected PageRequest getPageRequest(Sort sort) {
		int pageIndex = this.parsePageIndex();
		int pageSize = this.parsePageSize();
		try {
			if (Objects.nonNull(sort)) {
				String sortName = this.getParameter("sortName");
				String sortOrder = this.getParameter("sortOrder");
				if (StringUtils.isNoneBlank(sortName) && StringUtils.isNoneBlank(sortOrder)) {
					if (sortOrder.equalsIgnoreCase("desc")) {
						sort.and( Sort.by(Direction.DESC, sortName));
					} else {
						sort.and( Sort.by(Direction.ASC, sortName));
					}
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		PageRequest pageRequest = this.buildPageRequest(pageIndex, pageSize, sort);
		return pageRequest;
	}

	protected String redirect(String path) {
		return "redirect:" + path;
	}

	protected void forward(String path) {
		try {
			this.getRequest().getRequestDispatcher(path).forward(this.getRequest(), this.getResponse());
		} catch (ServletException | IOException e) {

		}
	}

	protected String getParameter(String key) {
		String value = StringUtils.trimToNull(this.getRequest().getParameter(key));

		return value;
	}
}
