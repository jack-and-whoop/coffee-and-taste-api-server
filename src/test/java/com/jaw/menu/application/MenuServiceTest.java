package com.jaw.menu.application;

import static com.jaw.Fixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaw.menu.domain.Menu;
import com.jaw.menu.ui.MenuRequestDTO;
import com.jaw.menu.ui.MenuResponseDTO;

class MenuServiceTest {

    private InMemoryMenuRepository menuRepository;
    private MenuService menuService;

    @BeforeEach
    void setup() {
        menuRepository = new InMemoryMenuRepository();
        menuService = new MenuService(menuRepository);
    }

    @AfterEach
    void teardown() {
        menuRepository.clear();
    }

    @DisplayName("새로운 메뉴를 등록한다.")
    @Test
    void create() {
        String name = "아이스 카페 아메리카노";
        String englishName = "Iced Caffe Americano";
        long price = 4_500;
        MenuRequestDTO request = new MenuRequestDTO(name, englishName, price, true);

        MenuResponseDTO response = menuService.create(request);

        assertAll(
            () -> assertThat(response.getId()).isEqualTo(1L),
            () -> assertThat(response.getName()).isEqualTo(name),
            () -> assertThat(response.getPrice()).isEqualTo(BigDecimal.valueOf(price)),
            () -> assertThat(response.isOnSale()).isTrue()
        );
    }

    @DisplayName("전체 메뉴 목록을 조회한다.")
    @Test
    void findAll() {
        Menu menu1 = menu("바닐라 플랫 화이트", 5_900L);
        Menu menu2 = menu("아이스 카페 모카", 5_500L);
        menuRepository.save(menu1);
        menuRepository.save(menu2);

        List<MenuResponseDTO> menus = menuService.findAll();

        assertThat(menus).hasSize(2);
    }

    @DisplayName("특정 메뉴를 조회한다.")
    @Test
    void findById() {
        Menu menu = menuRepository.save(menu("바닐라 플랫 화이트", 5_900L));

        MenuResponseDTO foundMenu = menuService.findById(menu.getId());

        assertAll(
            () -> assertThat(foundMenu.getId()).isEqualTo(menu.getId()),
            () -> assertThat(foundMenu.getName()).isEqualTo(menu.getName()),
            () -> assertThat(foundMenu.getPrice()).isEqualTo(menu.getPrice()),
            () -> assertThat(foundMenu.isOnSale()).isEqualTo(menu.isOnSale())
        );
    }
}
