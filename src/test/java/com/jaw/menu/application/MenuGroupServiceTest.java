package com.jaw.menu.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaw.menu.domain.MenuGroup;
import com.jaw.menu.ui.MenuGroupRequestDTO;
import com.jaw.menu.ui.MenuGroupResponseDTO;

class MenuGroupServiceTest {

    private InMemoryMenuGroupRepository menuGroupRepository;
    private MenuGroupService menuGroupService;

    @BeforeEach
    void setup() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        menuGroupService = new MenuGroupService(menuGroupRepository);
    }

    @AfterEach
    void teardown() {
        menuGroupRepository.clear();
    }

    @DisplayName("새로운 메뉴 그룹을 등록한다.")
    @Test
    void create() {
        String menuGroupName = "에스프레소";
        MenuGroupResponseDTO response = menuGroupService.create(new MenuGroupRequestDTO(menuGroupName));
        assertAll(
            () -> assertThat(response.getId()).isEqualTo(1L),
            () -> assertThat(response.getName()).isEqualTo("에스프레소")
        );
    }

    @DisplayName("전체 메뉴 그룹 목록을 조회한다.")
    @Test
    void findAll() {
        menuGroupRepository.save(menuGroup("블렌디드"));
        menuGroupRepository.save(menuGroup("프라푸치노"));
        List<MenuGroupResponseDTO> menuGroups = menuGroupService.findAll();
        assertThat(menuGroups).hasSize(2);
    }
    
    private MenuGroup menuGroup(String name) {
        return MenuGroup.builder()
            .name(name)
            .build();
    }
}
