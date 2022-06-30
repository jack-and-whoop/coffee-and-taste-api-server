package com.jaw;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jaw.auth.JwtUtil;
import com.jaw.category.domain.Category;
import com.jaw.member.domain.Member;
import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuGroup;

public class Fixtures {

	public static final String SECRET_KEY = "this-is-coffee-and-taste-api-server";
	public static final JwtUtil JWT_UTIL = new JwtUtil(SECRET_KEY);
	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

	private Fixtures() {
	}

	public static Member member() {
		return Member.builder()
			.id(1L)
			.name("홍길동")
			.nickname("hong")
			.email("hong@example.com")
			.password(PASSWORD_ENCODER.encode("1234"))
			.birthDate(LocalDate.of(1992, 1, 1))
			.phoneNumber("010-1234-5678")
			.build();
	}

	public static Member other() {
		return Member.builder()
			.id(2L)
			.name("김길동")
			.nickname("kim")
			.email("kim@example.com")
			.password(PASSWORD_ENCODER.encode("5678"))
			.birthDate(LocalDate.of(1994, 1, 1))
			.phoneNumber("010-2222-3333")
			.build();
	}

	public static Category category(String name) {
		return new Category(name);
	}

	public static MenuGroup menuGroup(String name, String englishName) {
		return MenuGroup.builder()
			.name(name)
			.englishName(englishName)
			.build();
	}

	public static MenuGroup menuGroup(String name, String englishName, Category category) {
		return MenuGroup.builder()
			.name(name)
			.englishName(englishName)
			.category(category)
			.build();
	}

	public static Menu menu(String name, Long price, MenuGroup menuGroup) {
		return Menu.builder()
			.name(name)
			.price(price)
			.onSale(true)
			.menuGroup(menuGroup)
			.build();
	}

	public static Menu menu(String name, Long price) {
		return Menu.builder()
			.name(name)
			.price(price)
			.onSale(true)
			.build();
	}

	public static Menu menu(Long id, String name, Long price) {
		return Menu.builder()
			.id(id)
			.name(name)
			.price(price)
			.onSale(true)
			.build();
	}
}
