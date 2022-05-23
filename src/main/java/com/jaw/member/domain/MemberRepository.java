package com.jaw.member.domain;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

	Member save(Member member);

	List<Member> findAll();

	Optional<Member> findById(Long id);
}
