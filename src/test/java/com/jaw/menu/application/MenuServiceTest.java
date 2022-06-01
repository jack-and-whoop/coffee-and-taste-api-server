package com.jaw.menu.application;

import com.jaw.menu.domain.Menu;
import com.jaw.menu.ui.MenuRequestDTO;
import com.jaw.menu.ui.MenuResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        BigDecimal price = BigDecimal.valueOf(4_500);
        MenuRequestDTO request = MenuRequestDTO.builder()
            .name(name)
            .price(price)
            .onSale(true)
            .build();
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
        Menu menu1 = menu("바닐라 플랫 화이트", "Vanilla Flat White", 5_900, true);
        Menu menu2 = menu("아이스 카페 모카", "Iced Caffe Mocha", 5_500, false);
        menuRepository.save(menu1);
        menuRepository.save(menu2);
        List<MenuResponseDTO> menus = menuService.findAll();
        assertThat(menus).hasSize(2);
    }

    @DisplayName("특정 메뉴를 조회한다.")
    @Test
    void findById() {
        Menu menu = menuRepository.save(menu("바닐라 플랫 화이트", "Vanilla Flat White", 5_900, true));
        MenuResponseDTO foundMenu = menuService.findById(menu.getId());
        assertAll(
            () -> assertThat(foundMenu.getId()).isEqualTo(menu.getId()),
            () -> assertThat(foundMenu.getName()).isEqualTo(menu.getName()),
            () -> assertThat(foundMenu.getPrice()).isEqualTo(menu.getPrice()),
            () -> assertThat(foundMenu.isOnSale()).isEqualTo(menu.isOnSale())
        );
    }

    private Menu menu(String name, String englishName, long price, boolean onSale) {
        return Menu.builder()
            .name(name)
            .englishName(englishName)
            .price(BigDecimal.valueOf(price))
            .onSale(onSale)
            .build();
    }
}
