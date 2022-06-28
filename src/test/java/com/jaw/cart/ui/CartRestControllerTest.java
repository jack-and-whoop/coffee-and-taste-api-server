package com.jaw.cart.ui;

import static com.jaw.Fixtures.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.jaw.AbstractControllerTest;
import com.jaw.cart.application.CartService;
import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;
import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuRepository;

class CartRestControllerTest extends AbstractControllerTest {

	private static final String BASE_URI = "/api/members/{memberId}/cart";
	private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.1Zx-1BRb0VJflU1JBYaP_FqrL6S53uRBn5DhYablbf0";

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private CartService cartService;

	private Member member;
	private Member other;

	@BeforeEach
	void setup() {
		member = memberRepository.save(member());
		other = memberRepository.save(other());
	}

	@DisplayName("유효한 인증 토큰을 함께 전달할 경우, 장바구니에 메뉴를 추가할 수 있다.")
	@Test
	void addMenuWithValidToken() throws Exception {
		Menu americano = menuRepository.save(menu("아메리카노", 4_000L));

		CartMenuRequestDTO request = new CartMenuRequestDTO(americano.getId(), 1);

		mvc.perform(post(BASE_URI, member.getId())
				.header("Authorization", "Bearer " + JWT_UTIL.encode(member.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.menu.id").value(americano.getId()))
			.andExpect(jsonPath("$.menu.name").value(americano.getName()))
			.andExpect(jsonPath("$.menu.price").value(americano.getPrice()))
			.andExpect(jsonPath("$.quantity").value(1));
	}

	@DisplayName("다른 회원의 장바구니에 메뉴를 담을 수 없다.")
	@Test
	void addMenuToOtherCart() throws Exception {
		Menu mangoBanana = menuRepository.save(menu("망고 바나나 블렌디드", 6_300L));

		CartMenuRequestDTO request = new CartMenuRequestDTO(mangoBanana.getId(), 1);

		mvc.perform(post(BASE_URI, other.getId())
				.header("Authorization", "Bearer " + JWT_UTIL.encode(member.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(request)))
			.andExpect(status().isForbidden());
	}

	@DisplayName("장바구니에 메뉴 추가 요청 시, 인증 토큰이 유효하지 않을 경우 HTTP 401 응답을 내려준다.")
	@Test
	void addMenuWithInvalidToken() throws Exception {
		Menu americano = menuRepository.save(menu("아메리카노", 4_000L));

		CartMenuRequestDTO request = new CartMenuRequestDTO(americano.getId(), 1);

		mvc.perform(post(BASE_URI, member.getId())
				.header("Authorization", "Bearer " + INVALID_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(request)))
			.andExpect(status().isUnauthorized());
	}

	@DisplayName("장바구니에 메뉴 요청 시, 인증 정보가 없는 경우 HTTP 401 응답을 내려준다.")
	@Test
	void addMenuWithoutToken() throws Exception {
		Menu americano = menuRepository.save(menu("아메리카노", 4_000L));

		CartMenuRequestDTO request = new CartMenuRequestDTO(americano.getId(), 1);

		mvc.perform(post(BASE_URI, member.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(request)))
			.andExpect(status().isUnauthorized());
	}

	@DisplayName("유효한 인증 토큰이 전달될 경우, 장바구니에 담긴 모든 메뉴를 조회할 수 있다.")
	@Test
	void findAllWithValidToken() throws Exception {
		Menu icedAmericano = menuRepository.save(menu("아이스 아메리카노", 4_500L));
		Menu mangoBanana = menuRepository.save(menu("망고 바나나 블렌디드", 6_300L));

		CartMenuRequestDTO icedAmericanoRequest = new CartMenuRequestDTO(icedAmericano.getId(), 1);
		CartMenuRequestDTO mangoBananaRequest = new CartMenuRequestDTO(mangoBanana.getId(), 2);

		CartMenuResponseDTO icedAmericanoInCart = cartService.addMenu(member.getId(), member.getId(), icedAmericanoRequest);
		CartMenuResponseDTO mangoBananaInCart = cartService.addMenu(member.getId(), member.getId(), mangoBananaRequest);
		List<CartMenuResponseDTO> responses = List.of(icedAmericanoInCart, mangoBananaInCart);

		mvc.perform(get(BASE_URI, member.getId())
				.header("Authorization", "Bearer " + JWT_UTIL.encode(member.getId())))
			.andExpect(status().isOk())
			.andExpect(content().json(OBJECT_MAPPER.writeValueAsString(responses)));
	}

	@DisplayName("회원은 다른 회원의 장바구니에 담긴 메뉴 목록을 조회할 수 없다.")
	@Test
	void findAllOtherUserCart() throws Exception {
		mvc.perform(get(BASE_URI, other.getId())
				.header("Authorization", "Bearer " + JWT_UTIL.encode(member.getId())))
			.andExpect(status().isForbidden());
	}

	@DisplayName("장바구니에 담긴 모든 메뉴 조회 요청 시, 인증 토큰이 유효하지 않을 경우 HTTP 401 응답을 내려준다.")
	@Test
	void findAllWithInvalidToken() throws Exception {
		mvc.perform(get(BASE_URI, member.getId())
				.header("Authorization", "Bearer " + INVALID_TOKEN))
			.andExpect(status().isUnauthorized());
	}

	@DisplayName("장바구니에 담긴 모든 메뉴 조회 요청 시, 인증 정보가 없는 경우 HTTP 401 응답을 내려준다.")
	@Test
	void findAllWithoutToken() throws Exception {
		mvc.perform(get(BASE_URI, member.getId()))
			.andExpect(status().isUnauthorized());
	}

	@DisplayName("장바구니에 담긴 메뉴를 수정한다.")
	@Test
	void update() throws Exception {
		Menu mixCoffee = menuRepository.save(menu("믹스 커피", 300L));
		CartMenuResponseDTO mixCoffeeCartMenu =
			cartService.addMenu(member.getId(), member.getId(), new CartMenuRequestDTO(mixCoffee.getId(), 1));

		CartMenuUpdateDTO request = new CartMenuUpdateDTO(mixCoffeeCartMenu.getId(), 2L);

		mvc.perform(patch(BASE_URI, member.getId())
				.header("Authorization", "Bearer " + JWT_UTIL.encode(member.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.quantity").value(2L));
	}

	@DisplayName("장바구니에 담긴 메뉴를 삭제한다.")
	@Test
	void remove() throws Exception {
		Menu mixCoffee = menuRepository.save(menu("믹스 커피", 300L));
		CartMenuResponseDTO cartMenu = cartService.addMenu(member.getId(), member.getId(), new CartMenuRequestDTO(mixCoffee.getId(), 1));

		CartMenuDeleteRequestDTO request = new CartMenuDeleteRequestDTO(cartMenu.getId());

		mvc.perform(delete(BASE_URI, member.getId())
				.header("Authorization", "Bearer " + JWT_UTIL.encode(member.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(request)))
			.andExpect(status().isNoContent());
	}
}
