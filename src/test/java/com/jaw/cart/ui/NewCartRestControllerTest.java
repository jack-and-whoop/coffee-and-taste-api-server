package com.jaw.cart.ui;

import static com.jaw.Fixtures.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

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
import com.jaw.cart.domain.CartMenu;
import com.jaw.member.application.AuthenticationService;
import com.jaw.member.domain.Member;
import com.jaw.menu.domain.Menu;
import com.jaw.order.domain.Order;
import com.jaw.order.domain.OrderMenu;

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

	@DisplayName("장바구니에 메뉴를 추가한다.")
	@Test
	void addMenu() throws Exception {
		Menu americano = menu("아메리카노", 1_000L);
		Cart cart = new Cart(member());

		CartMenu cartMenu = new CartMenu(cart, americano, 1L);
		cart.addMenu(cartMenu);

		given(cartService.addCartMenu(any(Long.class), any(Long.class), any(CartMenuRequestDTO.class)))
			.willReturn(new CartResponseDTO(cart));

		mvc.perform(post("/api/carts/1/cart-menus")
				.header("Authorization", "Bearer " + VALID_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(new CartMenuRequestDTO(americano.getId(), 1L))))
			.andExpect(status().isCreated())
			.andExpect(content().json(OBJECT_MAPPER.writeValueAsString(new CartResponseDTO(cart))));
	}

	@DisplayName("장바구니에 담긴 메뉴를 주문한다.")
	@Test
	void orderCartMenus() throws Exception {
		Member member = member();
		Menu mixCoffee = menu(1L, "믹스 커피", 1_000L);
		Menu americano = menu(2L, "아메리카노", 1_000L);
		Order order = new Order(member, List.of(new OrderMenu(mixCoffee, 1L), new OrderMenu(americano, 2L)));

		given(cartService.orderCartMenus(any(Long.class), any(Long.class), any(CartMenuOrderRequestDTO.class)))
			.willReturn(new CartMenuOrderResponseDTO(order));

		CartMenuOrderRequestDTO orderRequest = new CartMenuOrderRequestDTO();
		orderRequest.setCartMenuIds(List.of(mixCoffee.getId(), americano.getId()));

		mvc.perform(post("/api/carts/1/order")
				.header("Authorization", "Bearer " + VALID_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(orderRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.orderMenus[0].menu.name").value("믹스 커피"))
			.andExpect(jsonPath("$.orderMenus[0].quantity").value("1"))
			.andExpect(jsonPath("$.orderMenus[1].menu.name").value("아메리카노"))
			.andExpect(jsonPath("$.orderMenus[1].quantity").value("2"));
	}
}
