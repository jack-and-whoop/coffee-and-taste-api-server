package com.jaw.member.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoleRepositoryTest {

	private InMemoryRoleRepository roleRepository;

	@BeforeEach
	void setup() {
		roleRepository = new InMemoryRoleRepository();
	}

	@DisplayName("새로운 Role을 등록한다.")
	@Test
	void save() {
		Role role = roleRepository.save(new Role(1L, "USER"));
		assertThat(role.getName()).isEqualTo("USER");
	}

	@DisplayName("회원의 식별자로 해당 회원의 Role 목록을 조회한다.")
	@Test
	void findAllByUserId() {
		roleRepository.save(new Role(1L, "USER"));
		roleRepository.save(new Role(1L, "ADMIN"));
		roleRepository.save(new Role(2L, "USER"));
		List<Role> roles = roleRepository.findAllByUserId(1L);
		assertThat(roles).hasSize(2);
	}

	@AfterEach
	void teardown() {
		roleRepository.clear();
	}
}
