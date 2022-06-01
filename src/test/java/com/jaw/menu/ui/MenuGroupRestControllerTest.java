package com.jaw.menu.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuGroup;
import com.jaw.menu.domain.MenuGroupRepository;
import com.jaw.menu.domain.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource("/application-dev.properties")
class MenuGroupRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac)
            .addFilters(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
            .alwaysDo(print())
            .build();
    }

    @DisplayName("새로운 메뉴 그룹을 등록한다.")
    @Test
    void create() throws Exception {
        MenuGroupRequestDTO request = new MenuGroupRequestDTO("블렌디드", "Blended");

        mvc.perform(post("/api/menu-groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("블렌디드"))
            .andExpect(jsonPath("$.englishName").value("Blended"));
    }

    @DisplayName("메뉴 그룹 목록을 조회한다.")
    @Test
    void findAll() throws Exception {
        MenuGroup coldBrew = menuGroupRepository.save(new MenuGroup("콜드 브루", "Cold Brew", null));
        MenuGroup blonde = menuGroupRepository.save(new MenuGroup("블론드", "Blonde Coffee", null));

        List<MenuGroupResponseDTO> menuGroups = List.of(
            new MenuGroupResponseDTO(coldBrew),
            new MenuGroupResponseDTO(blonde)
        );

        mvc.perform(get("/api/menu-groups"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(menuGroups)));
    }

    @DisplayName("특정 메뉴 그룹을 조회한다.")
    @Test
    void findById() throws Exception {
        MenuGroup frappuccino = menuGroupRepository.save(new MenuGroup("프라푸치노", "Frappuccino", null));

        MenuGroupResponseDTO menuGroup = new MenuGroupResponseDTO(frappuccino);

        mvc.perform(get("/api/menu-groups/{menuGroupId}", frappuccino.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(menuGroup)));
    }

    @DisplayName("특정 메뉴 그룹 조회 시, 하위의 메뉴 목록을 함께 조회한다.")
    @Test
    void findWithMenusById() throws Exception {
        MenuGroup espresso = menuGroupRepository.save(new MenuGroup("에스프레소", "Espresso", null));
        Menu macchiato = menuRepository.save(menu("에스프레소 마키아또", "Espresso Macchiato", 4_000, espresso));
        Menu conPanna = menuRepository.save(menu("에스프레소 콘 파나", "Espresso Con Panna", 4_200, espresso));

        MenuGroupMenusResponseDTO menuGroup = new MenuGroupMenusResponseDTO(espresso, List.of(macchiato, conPanna));

        mvc.perform(get("/api/menu-groups/{menuGroupId}/menus", espresso.getId()))
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
