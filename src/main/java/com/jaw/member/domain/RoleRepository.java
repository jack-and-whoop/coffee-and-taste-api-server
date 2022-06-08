package com.jaw.member.domain;

import java.util.List;

public interface RoleRepository {

	Role save(Role role);

	List<Role> findAllByUserId(Long userId);
}
