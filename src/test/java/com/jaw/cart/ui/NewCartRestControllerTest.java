package com.jaw.cart.ui;

import static com.jaw.Fixtures.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jaw.cart.application.CartService;
import com.jaw.cart.domain.Cart;
import com.jaw.member.application.AuthenticationService;
import com.jaw.member.domain.Member;

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
		Member member = member();

		given(cartService.create(any(Long.class))).willReturn(new Cart(member));
	}

	@DisplayName("회원의 장바구니를 생성한다.")
	@Test
	void create() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/api/carts")
				.header("Authorization", "Bearer " + VALID_TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}
}
