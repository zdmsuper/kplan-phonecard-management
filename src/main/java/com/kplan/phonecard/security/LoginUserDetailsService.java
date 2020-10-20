package com.kplan.phonecard.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kplan.phonecard.domain.BasicUserInfo;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.dto.UserDetailsDto;
import com.kplan.phonecard.enums.UserStatusEnum;
import com.kplan.phonecard.manager.ManagerInfoManager;
import com.kplan.phonecard.query.ManagerInfoQuery;
import com.kplan.phonecard.service.ManagerInfoService;

@Service
public class LoginUserDetailsService implements UserDetailsService {
	@Autowired
	PasswordEncoder passwordEncoder;// = new BCryptPasswordEncoder();
	@Autowired
	ManagerInfoManager managerInfoManager;
	@Autowired
	ManagerInfoService managerInfoService;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		return this.findUserDetails(loginId);
	}

	private UserDetails findUserDetails(String loginId) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority("admin"));
		ManagerInfoQuery query = new ManagerInfoQuery();
//		query.getDomain().setId(-1L);
		long count = this.managerInfoManager.count(query);
		if (count == 0) {
			UserDetails userDetails = new UserDetailsDto(1L, loginId, passwordEncoder.encode("abc"), authorities);
			return userDetails;
		} else {

			BasicUserInfo basicUserInfo = new BasicUserInfo();
			query.getDomain().setBasicUserInfo(basicUserInfo);
			if (this.loginWithEmail(loginId)) {
				basicUserInfo.setEmail(loginId);
			} else if (this.loginWithPhone(loginId)) {
				basicUserInfo.setPhone(loginId);
			} else {
				basicUserInfo.setLoginId(loginId);
			}
			ManagerInfo managerInfo = this.managerInfoManager.findMemberInfos(query, Pageable.unpaged()).get()
					.findFirst().orElse(new ManagerInfo());
			if (managerInfo.getId() == null) {
				throw new BadCredentialsException("用户名或密码错误");
			}
			if (UserStatusEnum.VALID != managerInfo.getUserStatus()) {
				throw new LockedException("用户已经被锁定");
			}

			UserDetails userDetails = new UserDetailsDto(managerInfo.getId(), loginId, managerInfo.getLoginPwd(),
					authorities);
			return userDetails;
		}

	}

	private boolean loginWithEmail(String loginId) {
		if (StringUtils.isNotBlank(loginId)) {
			return loginId.contains("@");
		}
		return false;
	}

	private boolean loginWithPhone(String loginId) {
		List<Character> chs = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '8');
		if (StringUtils.isNotBlank(loginId) && loginId.trim().length() == 11) {
			for (Character ch : loginId.toCharArray()) {
				if (!chs.contains(ch)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}
