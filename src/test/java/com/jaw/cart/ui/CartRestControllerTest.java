package com.jaw.cart.ui;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaw.cart.application.CartService;
import com.jaw.cart.domain.CartMenu;
import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;
import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource("/application-dev.properties")
class CartRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private CartService cartService;

	private Member member;

	@BeforeEach
	void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac)
			.addFilters(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
			.alwaysDo(print())
			.build();

		member = memberRepository.save(Member.builder()
			.name("홍길동")
			.nickname("hong")
			.birthDate(LocalDate.now())
			.email("hong@gmail.com")
			.phoneNumber("010-1234-5678")
			.build());
	}

	@DisplayName("장바구니에 담긴 모든 메뉴를 조회한다.")
	@Test
	void findAll() throws Exception {
		Menu icedAmericano = menuRepository.save(menu("아이스 아메리카노", 4_500));
		Menu mangoBanana = menuRepository.save(menu("망고 바나나 블렌디드", 6_300));

		CartMenu icedAmericanoInCart = cartService.addMenu(member.getId(), icedAmericano, 1);
		CartMenu mangoBananaInCart = cartService.addMenu(member.getId(), mangoBanana, 1);

		List<CartMenuResponseDTO> responses = List.of(
			new CartMenuResponseDTO(icedAmericanoInCart),
			new CartMenuResponseDTO(mangoBananaInCart)
		);

		mvc.perform(MockMvcRequestBuilders.get("/members/{memberId}/cart", member.getId()))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(responses)));
	}

	private Menu menu(String name, long price) {
		return Menu.builder()
			.name(name)
			.price(price)
			.build();
	}
}
