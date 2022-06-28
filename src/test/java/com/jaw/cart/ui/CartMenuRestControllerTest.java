package com.jaw.cart.ui;

import static com.jaw.Fixtures.*;
import static org.mockito.ArgumentMatchers.*;
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

import com.jaw.cart.application.CartMenuService;
import com.jaw.cart.domain.Cart;
import com.jaw.cart.domain.CartMenu;
import com.jaw.member.application.AuthenticationService;

@WebMvcTest(CartMenuRestController.class)
class CartMenuRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CartMenuService cartMenuService;

	@MockBean
	private AuthenticationService authenticationService;

	@BeforeEach
	void setup() {
		Cart cart = new Cart(member());
		CartMenu cartMenu = new CartMenu(cart, menu("아이스 아메리카노", 1_000L), 1L);
		CartMenu updated = new CartMenu(cart, menu("아이스 아메리카노", 1_000L), 2L);

		given(cartMenuService.findById(any(Long.class))).willReturn(new CartMenuResponseDTO(cartMenu));
		given(cartMenuService.changeQuantity(any(Long.class), any(Long.class))).willReturn(new CartMenuResponseDTO(updated));
	}

	@DisplayName("특정 장바구니 메뉴를 조회한다.")
	@Test
	void findById() throws Exception {
		mvc.perform(get("/api/cart-menus/{id}", 1L))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.menu.name").value("아이스 아메리카노"))
			.andExpect(jsonPath("$.menu.price").value(1_000L));
	}

	@DisplayName("장바구니에 담긴 메뉴의 수량을 변경한다.")
	@Test
	void changeQuantity() throws Exception {
		CartMenuUpdateDTO updateRequest = new CartMenuUpdateDTO(1L, 2L);

		mvc.perform(patch("/api/cart-menus/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(updateRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.quantity").value(2L));
	}

	@DisplayName("장바구니에 담긴 메뉴를 삭제한다.")
	@Test
	void deleteById() throws Exception {
		mvc.perform(delete("/api/cart-menus/{id}", 1L))
			.andExpect(status().isNoContent());
	}
}
