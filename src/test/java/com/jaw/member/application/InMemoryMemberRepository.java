package com.jaw.member.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;

public class InMemoryMemberRepository implements MemberRepository {

	private final Map<Long, Member> members = new HashMap<>();
	private static long sequence = 0L;

	@Override
	public Member save(Member member) {
		member.setId(++sequence);
		members.put(member.getId(), member);
		return member;
	}

	@Override
	public List<Member> findAll() {
		return new ArrayList<>(members.values());
	}

	@Override
	public Optional<Member> findById(Long id) {
		return Optional.ofNullable(members.get(id));
	}

	@Override
	public Optional<Member> findByEmail(String email) {
		return members.values()
			.stream()
			.filter(member -> Objects.equals(member.getEmail(), email))
			.findFirst();
	}

	public void clear() {
		sequence = 0;
		members.clear();
	}
}
