package com.jaw.member.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;

public class InMemoryMemberRepository implements MemberRepository {

	private final Map<Long, Member> members = new HashMap<>();

	@Override
	public Member save(Member member) {
		members.put(member.getId(), member);
		return member;
	}

	@Override
	public List<Member> findAll() {
		return new ArrayList<>(members.values());
	}
}
