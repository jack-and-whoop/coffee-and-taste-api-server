package com.jaw.order.ui;

import static com.jaw.Fixtures.*;
import static org.mockito.ArgumentMatchers.*;
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

import com.jaw.member.application.AuthenticationService;
import com.jaw.member.domain.Role;
import com.jaw.menu.domain.Menu;
import com.jaw.order.application.OrderService;
import com.jaw.order.domain.Order;
import com.jaw.order.domain.OrderMenu;

@WebMvcTest(OrderRestController.class)
class OrderRestControllerTest {

    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.1Zx-1BRb0VJflU1JBYaP_FqrL6S53uRBn5DhYablbfw";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private AuthenticationService authenticationService;

    private Order order;

    @BeforeEach
    void setup() {
        Menu menu = menu("콜드 브루", 4_900L);

        OrderMenu orderMenu = new OrderMenu(menu, 1L);
        orderMenu.setId(1L);

        order = new Order();
        order.setId(1L);
        order.setOrderMenus(List.of(orderMenu));

        given(authenticationService.roles(any())).willReturn(List.of(new Role(1L, "ROLE_ADMIN")));
        given(orderService.create(any(OrderRequestDTO.class), any(Long.class))).willReturn(new OrderResponseDTO(order));
        given(orderService.findById(any())).willReturn(new OrderResponseDTO(order));
        given(orderService.findAll()).willReturn(List.of(new OrderResponseDTO(order)));
    }

    @DisplayName("새로운 주문을 등록한다.")
    @Test
    void create() throws Exception {
        OrderRequestDTO request = new OrderRequestDTO(1L, 1L);

        mvc.perform(post("/api/orders")
                .header("Authorization", "Bearer " + VALID_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$..orderMenus[0].menu.name").value("콜드 브루"))
            .andExpect(jsonPath("$..orderMenus[0].menu.price").value(4_900));
    }

    @DisplayName("인증된 토큰을 전달하지 않으면 새로운 주문을 등록할 수 없다.")
    @Test
    void createWithoutToken() throws Exception {
        OrderRequestDTO request = new OrderRequestDTO(1L, 1L);

        mvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(request)))
            .andExpect(status().isUnauthorized());
    }

    @DisplayName("주문 목록을 조회한다.")
    @Test
    void findAll() throws Exception {
        List<OrderResponseDTO> response = List.of(new OrderResponseDTO(order));

        mvc.perform(get("/api/orders")
                .header("Authorization", "Bearer " + VALID_TOKEN))
            .andExpect(status().isOk())
            .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(response)));
    }

    @DisplayName("특정 주문을 조회한다.")
    @Test
    void findById() throws Exception {
        OrderResponseDTO response = new OrderResponseDTO(order);

        mvc.perform(get("/api/orders/{id}", 1L)
                .header("Authorization", "Bearer " + VALID_TOKEN))
            .andExpect(status().isOk())
            .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(response)));
    }
}
