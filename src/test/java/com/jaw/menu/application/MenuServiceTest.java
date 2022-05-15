package com.jaw.menu.application;

import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuRepository;
import com.jaw.menu.ui.MenuRequestDTO;
import com.jaw.menu.ui.MenuResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuServiceTest {

    private MenuRepository menuRepository;
    private MenuService menuService;

    @BeforeEach
    void setup() {
        menuRepository = new InMemoryMenuRepository();
        menuService = new MenuService(menuRepository);
    }

    @DisplayName("새로운 메뉴를 등록한다.")
    @Test
    void create() {
        String name = "아이스 카페 아메리카노";
        BigDecimal price = BigDecimal.valueOf(4_500);
        MenuRequestDTO request = new MenuRequestDTO(name, price, true);
        MenuResponseDTO response = menuService.create(request);
        assertAll(
            () -> assertThat(response.getId()).isEqualTo(1L),
            () -> assertThat(response.getName()).isEqualTo(name),
            () -> assertThat(response.getPrice()).isEqualTo(price),
            () -> assertThat(response.isOnSale()).isTrue()
        );
    }

    @DisplayName("전체 메뉴 목록을 조회한다.")
    @Test
    void findAll() {
        Menu menu1 = menu("바닐라 플랫 화이트", 5_900, true);
        Menu menu2 = menu("아이스 카페 모카", 5_500, false);
        menuRepository.save(menu1);
        menuRepository.save(menu2);
        List<MenuResponseDTO> menus = menuService.findAll();
        assertThat(menus).hasSize(2);
    }

    private Menu menu(String name, long price, boolean onSale) {
        return new Menu(name, BigDecimal.valueOf(price), onSale);
    }
}