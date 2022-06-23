package com.jaw.menu.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.jaw.AbstractControllerTest;
import com.jaw.menu.application.MenuService;

class MenuRestControllerTest extends AbstractControllerTest {

    private static final String BASE_URI = "/api/menus";

    @Autowired
    private MenuService menuService;

    @DisplayName("새로운 메뉴를 등록한다.")
    @Test
    void create() throws Exception {
        MenuRequestDTO request = menuCreateRequest("아이스 아메리카노", "Iced Americano", 4_000);

        mvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("아이스 아메리카노"))
            .andExpect(jsonPath("$.englishName").value("Iced Americano"))
            .andExpect(jsonPath("$.price").value(4_000))
            .andExpect(jsonPath("$.onSale").value(true));
    }

    @DisplayName("메뉴 목록을 조회한다.")
    @Test
    void findAll() throws Exception {
        MenuResponseDTO mangoBanana = menuService.create(menuCreateRequest("망고 바나나", "Mango Banana", 6_000));
        MenuResponseDTO peachAndLemon = menuService.create(menuCreateRequest("피치 & 레몬", "Peach & Lemon", 6_000));

        List<MenuResponseDTO> menus = List.of(mangoBanana, peachAndLemon);

        mvc.perform(get(BASE_URI))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(menus)));
    }

    @DisplayName("특정 메뉴를 조회한다.")
    @Test
    void findById() throws Exception {
        MenuResponseDTO mangoBanana = menuService.create(menuCreateRequest("망고 바나나", "Mango Banana", 6_000));

        mvc.perform(get(BASE_URI + "/{menuId}", mangoBanana.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(mangoBanana)));
    }

    private MenuRequestDTO menuCreateRequest(String name, String englishName, long price) {
        return new MenuRequestDTO(name, englishName, price, true);
    }
}
