package com.kplan.phonecard.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class UserDetailsDto extends org.springframework.security.core.userdetails.User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	public UserDetailsDto(Long id,String username, String password,  
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);
		this.id=id;
	}
	public Long getId() {
		return id;
	}



}
