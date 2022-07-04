package com.jaw.cart.ui;

import static com.jaw.Fixtures.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
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

@WebMvcTest(CartRestController.class)
class CartRestControllerTest {

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

	@DisplayName("특정 장바구니를 조회한다.")
	@Test
	void findById() throws Exception {
		CartResponseDTO cart = new CartResponseDTO(new Cart(member()));
		given(cartService.findByUser(any(Long.class))).willReturn(cart);

		mvc.perform(get("/api/cart/cart-menus")
				.header("Authorization", "Bearer " + VALID_TOKEN))
			.andExpect(status().isOk())
			.andExpect(content().json(OBJECT_MAPPER.writeValueAsString(cart)));
	}

	@DisplayName("장바구니에 담긴 메뉴를 삭제한다.")
	@Test
	void deleteCartMenus() throws Exception {
		CartResponseDTO cart = new CartResponseDTO(new Cart(member()));

		given(authenticationService.parseToken(VALID_TOKEN)).willReturn(1L);

		given(cartService.deleteCartMenus(1L, Arrays.asList(1L, 2L)))
			.willReturn(cart);

		mvc.perform(delete("/api/cart/cart-menus")
				.header("Authorization", "Bearer " + VALID_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(new CartMenuDeleteRequest(Arrays.asList(1L, 2L)))))
			.andExpect(status().isNoContent());
	}

	@DisplayName("장바구니에 메뉴를 추가한다.")
	@Test
	void addMenu() throws Exception {
		Menu americano = menu("아메리카노", 1_000L);
		Cart cart = new Cart(member());
		CartMenu cartMenu = new CartMenu(cart, americano, 1L);
		cart.addMenu(cartMenu);

		given(cartService.addCartMenu(any(Long.class), any(CartMenuRequestDTO.class)))
			.willReturn(new CartMenuResponseDTO(cartMenu));

		mvc.perform(post("/api/cart/cart-menus")
				.header("Authorization", "Bearer " + VALID_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(new CartMenuRequestDTO(americano.getId(), 1L))))
			.andExpect(status().isCreated())
			.andExpect(content().json(OBJECT_MAPPER.writeValueAsString(new CartMenuResponseDTO(cartMenu))));
	}

	@DisplayName("장바구니에 담긴 메뉴를 주문한다.")
	@Test
	void orderCartMenus() throws Exception {
		Member member = member();
		Menu mixCoffee = menu(1L, "믹스 커피", 1_000L);
		Menu americano = menu(2L, "아메리카노", 1_000L);
		Order order = new Order(member, List.of(new OrderMenu(mixCoffee, 1L), new OrderMenu(americano, 2L)));

		given(cartService.orderCartMenus(any(Long.class), any(CartMenuOrderRequestDTO.class)))
			.willReturn(new CartMenuOrderResponseDTO(order));

		CartMenuOrderRequestDTO orderRequest = new CartMenuOrderRequestDTO();
		orderRequest.setCartMenuIds(List.of(mixCoffee.getId(), americano.getId()));

		mvc.perform(post("/api/cart/order")
				.header("Authorization", "Bearer " + VALID_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(orderRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.orderMenus[0].menu.name").value("믹스 커피"))
			.andExpect(jsonPath("$.orderMenus[0].quantity").value("1"))
			.andExpect(jsonPath("$.orderMenus[1].menu.name").value("아메리카노"))
			.andExpect(jsonPath("$.orderMenus[1].quantity").value("2"));
	}

	@DisplayName("장바구니에 담긴 특정 메뉴를 조회한다.")
	@Test
	void findCartMenuById() throws Exception {
		Member member = member();
		Menu menu = menu("아메리카노", 1_000L);

		given(cartService.findCartMenuById(any(Long.class), any(Long.class)))
			.willReturn(new CartMenuResponseDTO(new CartMenu(new Cart(member), menu, 1L)));

		mvc.perform(get("/api/cart/cart-menus/1")
				.header("Authorization", "Bearer " + VALID_TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.menu.name").value("아메리카노"))
			.andExpect(jsonPath("$.menu.price").value(1_000L))
			.andExpect(jsonPath("$.quantity").value(1L));
	}

	@DisplayName("장바구니에 담긴 특정 메뉴의 수량을 변경한다.")
	@Test
	void changeQuantity() throws Exception {
		Member member = member();
		Cart cart = new Cart(member);
		Menu menu = menu("아메리카노", 1_000L);

		CartMenuResponseDTO expected = new CartMenuResponseDTO(new CartMenu(cart, menu, 2L));

		given(cartService.changeCartMenuQuantity(any(Long.class), any(Long.class), any(CartMenuUpdateDTO.class)))
			.willReturn(expected);

		mvc.perform(patch("/api/cart/cart-menus/{id}", 1L)
				.header("Authorization", "Bearer " + VALID_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(new CartMenuUpdateDTO(2L))))
			.andExpect(status().isOk())
			.andExpect(content().json(OBJECT_MAPPER.writeValueAsString(expected)));
	}
}
