package com.jaw.member.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class InMemoryRoleRepository implements RoleRepository {

	private final Map<Long, Role> roles = new HashMap<>();
	private static long sequence = 0L;

	@Override
	public Role save(Role role) {
		role.setId(++sequence);
		roles.put(role.getId(), role);
		return role;
	}

	@Override
	public List<Role> findAllByUserId(Long userId) {
		return roles.values()
			.stream()
			.filter(role -> Objects.equals(role.getUserId(), userId))
			.collect(Collectors.toList());
	}

	public void clear() {
		sequence = 0;
		roles.clear();
	}
}
