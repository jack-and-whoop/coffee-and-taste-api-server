package com.jaw.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.jaw.member.domain.Role;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class UserAuthentication extends AbstractAuthenticationToken {

	private final Long userId;

	public UserAuthentication(Long userId, List<Role> roles) {
		super(authorities(roles));
		this.userId = userId;
	}

	private static List<GrantedAuthority> authorities(List<Role> roles) {
		return roles.stream()
			.map(role -> new SimpleGrantedAuthority(role.getName()))
			.collect(Collectors.toList());
	}

	public Long getUserId() {
		return userId;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public Object getPrincipal() {
		return userId;
	}

	@Override
	public Object getCredentials() {
		return null;
	}
}
