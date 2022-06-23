package com.jaw.menu.ui;

import com.jaw.AbstractControllerTest;
import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuGroup;
import com.jaw.menu.domain.MenuGroupRepository;
import com.jaw.menu.domain.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jaw.Fixtures.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource("/application-dev.properties")
class MenuGroupRestControllerTest extends AbstractControllerTest {

    private static final String BASE_URI = "/api/menu-groups";

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @Autowired
    private MenuRepository menuRepository;

    @DisplayName("새로운 메뉴 그룹을 등록한다.")
    @Test
    void create() throws Exception {
        MenuGroupRequestDTO request = new MenuGroupRequestDTO("블렌디드", "Blended", "blended.jpg");

        mvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("블렌디드"))
            .andExpect(jsonPath("$.englishName").value("Blended"))
            .andExpect(jsonPath("$.representativeImagePath").value("blended.jpg"));
    }

    @DisplayName("메뉴 그룹 목록을 조회한다.")
    @Test
    void findAll() throws Exception {
        MenuGroup coldBrew = menuGroupRepository.save(menuGroup("콜드 브루", "Cold Brew"));
        MenuGroup blonde = menuGroupRepository.save(menuGroup("블론드", "Blonde Coffee"));

        List<MenuGroupResponseDTO> menuGroups = List.of(
            new MenuGroupResponseDTO(coldBrew),
            new MenuGroupResponseDTO(blonde)
        );

        mvc.perform(get(BASE_URI))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(menuGroups)));
    }

    @DisplayName("특정 메뉴 그룹을 조회한다.")
    @Test
    void findById() throws Exception {
        MenuGroup frappuccino = menuGroupRepository.save(menuGroup("프라푸치노", "Frappuccino"));

        MenuGroupResponseDTO menuGroup = new MenuGroupResponseDTO(frappuccino);

        mvc.perform(get(BASE_URI + "/{menuGroupId}", frappuccino.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(menuGroup)));
    }

    @DisplayName("특정 메뉴 그룹 조회 시, 하위의 메뉴 목록을 함께 조회한다.")
    @Test
    void findWithMenusById() throws Exception {
        MenuGroup espresso = menuGroupRepository.save(menuGroup("에스프레소", "Espresso"));
        Menu macchiato = menuRepository.save(menu("에스프레소 마키아또", "Espresso Macchiato", 4_000, espresso));
        Menu conPanna = menuRepository.save(menu("에스프레소 콘 파나", "Espresso Con Panna", 4_200, espresso));

        MenuGroupMenusResponseDTO menuGroup = new MenuGroupMenusResponseDTO(espresso, List.of(macchiato, conPanna));

        mvc.perform(get(BASE_URI + "/{menuGroupId}/menus", espresso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(menuGroup)));
    }

    private Menu menu(String name, String englishName, long price, MenuGroup menuGroup) {
        return Menu.builder()
            .name(name)
            .englishName(englishName)
            .price(price)
            .onSale(true)
            .menuGroup(menuGroup)
            .build();
    }
}
