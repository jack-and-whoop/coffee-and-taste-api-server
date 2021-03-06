package com.jaw.menu.application;

import static com.jaw.Fixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaw.menu.domain.MenuGroup;
import com.jaw.menu.ui.MenuGroupMenusResponseDTO;
import com.jaw.menu.ui.MenuGroupRequestDTO;
import com.jaw.menu.ui.MenuGroupResponseDTO;

class MenuGroupServiceTest {

    private InMemoryMenuGroupRepository menuGroupRepository;
    private InMemoryMenuRepository menuRepository;
    private MenuGroupService menuGroupService;

    @BeforeEach
    void setup() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        menuRepository = new InMemoryMenuRepository();
        menuGroupService = new MenuGroupService(menuGroupRepository, menuRepository);
    }

    @AfterEach
    void teardown() {
        menuRepository.clear();
        menuGroupRepository.clear();
    }

    @DisplayName("새로운 메뉴 그룹을 등록한다.")
    @Test
    void create() {
        String name = "에스프레소";
        String englishName = "Espresso";
        String imagePath = "/images/espresso.jpg";
        MenuGroupResponseDTO response = menuGroupService.create(new MenuGroupRequestDTO(name, englishName, imagePath));
        assertAll(
            () -> assertThat(response.getId()).isEqualTo(1L),
            () -> assertThat(response.getName()).isEqualTo(name),
            () -> assertThat(response.getEnglishName()).isEqualTo(englishName),
            () -> assertThat(response.getRepresentativeImagePath()).isEqualTo(imagePath)
        );
    }

    @DisplayName("전체 메뉴 그룹 목록을 조회한다.")
    @Test
    void findAll() {
        menuGroupRepository.save(menuGroup("블렌디드", "Blended"));
        menuGroupRepository.save(menuGroup("프라푸치노", "Frappuccino"));
        List<MenuGroupResponseDTO> menuGroups = menuGroupService.findAll();
        assertThat(menuGroups).hasSize(2);
    }

    @DisplayName("특정 메뉴 그룹을 조회한다.")
    @Test
    void findById() {
        MenuGroup created = menuGroupRepository.save(menuGroup("티바나", "Teavana"));
        MenuGroupResponseDTO menuGroup = menuGroupService.findById(created.getId());
        assertThat(menuGroup.getId()).isEqualTo(created.getId());
        assertThat(menuGroup.getName()).isEqualTo(created.getName());
    }

    @DisplayName("특정 메뉴 그룹 조회 시, 하위의 메뉴 목록을 함께 조회한다.")
    @Test
    void findWithMenusById() {
        MenuGroup blended = menuGroupRepository.save(menuGroup("블렌디드", "Blended"));
        menuRepository.save(menu("망고 바나나", 6_100L, blended));
        menuRepository.save(menu("피치 & 레몬", 6_200L, blended));

        MenuGroupMenusResponseDTO menuGroup = menuGroupService.findWithMenusById(blended.getId());
        assertThat(menuGroup.getName()).isEqualTo(blended.getName());
        assertThat(menuGroup.getEnglishName()).isEqualTo(blended.getEnglishName());
        assertThat(menuGroup.getMenus()).hasSize(2);
    }
}
