package com.jaw.auth;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.jaw.member.domain.Role;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		UserAuthentication that = (UserAuthentication)o;
		return Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), userId);
	}
}
