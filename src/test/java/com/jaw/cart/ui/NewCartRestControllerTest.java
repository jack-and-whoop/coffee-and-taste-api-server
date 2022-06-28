package com.jaw.cart.ui;

import static com.jaw.Fixtures.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.jaw.cart.application.CartService;
import com.jaw.cart.domain.Cart;
import com.jaw.member.application.AuthenticationService;

@WebMvcTest(NewCartRestController.class)
class NewCartRestControllerTest {

	private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.1Zx-1BRb0VJflU1JBYaP_FqrL6S53uRBn5DhYablbfw";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CartService cartService;

	@MockBean
	private AuthenticationService authenticationService;

	@BeforeEach
	void setup() {
	}

	@DisplayName("회원의 장바구니를 생성한다.")
	@Test
	void create() throws Exception {
		given(cartService.create(any(Long.class))).willReturn(new CartResponseDTO(new Cart(member())));

		mvc.perform(post("/api/carts")
				.header("Authorization", "Bearer " + VALID_TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}

	@DisplayName("특정 장바구니를 조회한다.")
	@Test
	void findById() throws Exception {
		CartResponseDTO cart = new CartResponseDTO(new Cart(member()));
		given(cartService.findById(any(Long.class), any(Long.class))).willReturn(cart);

		mvc.perform(get("/api/carts/1")
				.header("Authorization", "Bearer " + VALID_TOKEN))
			.andExpect(status().isOk())
			.andExpect(content().json(OBJECT_MAPPER.writeValueAsString(cart)));
	}

	@DisplayName("장바구니에 담긴 모든 메뉴를 삭제한다.")
	@Test
	void deleteAllCartMenus() throws Exception {
		CartResponseDTO cart = new CartResponseDTO(new Cart(member()));
		given(cartService.deleteAllCartMenus(any(Long.class), any(Long.class))).willReturn(cart);

		mvc.perform(delete("/api/carts/1/cart-menus")
				.header("Authorization", "Bearer " + VALID_TOKEN))
			.andExpect(status().isNoContent())
			.andExpect(jsonPath("$.cartMenus.size()").value(0));
	}
}
