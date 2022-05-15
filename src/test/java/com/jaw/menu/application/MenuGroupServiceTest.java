package com.jaw.menu.application;

import com.jaw.menu.domain.MenuGroup;
import com.jaw.menu.domain.MenuGroupRepository;
import com.jaw.menu.ui.MenuGroupRequestDTO;
import com.jaw.menu.ui.MenuGroupResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuGroupServiceTest {

    private MenuGroupRepository menuGroupRepository;
    private MenuGroupService menuGroupService;

    @BeforeEach
    void setup() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        menuGroupService = new MenuGroupService(menuGroupRepository);
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
        menuGroupRepository.save(new MenuGroup("블렌드"));
        menuGroupRepository.save(new MenuGroup("디카페인 커피"));
        List<MenuGroupResponseDTO> menuGroups = menuGroupService.findAll();
        assertThat(menuGroups).hasSize(2);
    }
}
