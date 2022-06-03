package com.jaw.menu.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaw.menu.application.MenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource("/application-dev.properties")
class MenuRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MenuService menuService;

    @DisplayName("새로운 메뉴를 등록한다.")
    @Test
    void create() throws Exception {
        MenuRequestDTO request = menu("아이스 아메리카노", "Iced Americano", 4000);

        mvc.perform(post("/api/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("아이스 아메리카노"))
            .andExpect(jsonPath("$.englishName").value("Iced Americano"))
            .andExpect(jsonPath("$.price").value(4000))
            .andExpect(jsonPath("$.onSale").value(true));
    }

    @DisplayName("메뉴 목록을 조회한다.")
    @Test
    void findAll() throws Exception {
        MenuResponseDTO mangoBanana = menuService.create(menu("망고 바나나", "Mango Banana", 6000));
        MenuResponseDTO peachAndLemon = menuService.create(menu("피치 & 레몬", "Peach & Lemon", 6000));

        List<MenuResponseDTO> menus = List.of(mangoBanana, peachAndLemon);

        mvc.perform(get("/api/menus"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(menus)));
    }

    @DisplayName("특정 메뉴를 조회한다.")
    @Test
    void findById() throws Exception {
        MenuResponseDTO mangoBanana = menuService.create(menu("망고 바나나", "Mango Banana", 6000));

        mvc.perform(get("/api/menus/{menuId}", mangoBanana.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(mangoBanana)));
    }

    private MenuRequestDTO menu(String name, String englishName, long price) {
        return new MenuRequestDTO(name, englishName, price, true);
    }
}