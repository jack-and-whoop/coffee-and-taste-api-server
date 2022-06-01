package com.jaw.category.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaw.category.application.CategoryService;
import com.jaw.category.domain.Category;
import com.jaw.category.domain.CategoryRepository;
import com.jaw.menu.domain.MenuGroup;
import com.jaw.menu.domain.MenuGroupRepository;
import com.jaw.menu.ui.MenuGroupResponseDTO;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource("/application-dev.properties")
class CategoryRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @DisplayName("새로운 카테고리를 등록한다.")
    @Test
    void create() throws Exception {
        CategoryRequestDTO request = new CategoryRequestDTO("coffee");
        CategoryResponseDTO response = new CategoryResponseDTO(1L, "coffee");

        mvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("카테고리 목록을 조회한다.")
    @Test
    void findAll() throws Exception {
        CategoryResponseDTO coffee = categoryService.create(new CategoryRequestDTO("coffee"));
        CategoryResponseDTO bread = categoryService.create(new CategoryRequestDTO("bread"));

        mvc.perform(get("/api/categories"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(List.of(coffee, bread))));
    }

    @DisplayName("특정 카테고리를 조회한다.")
    @Test
    void findById() throws Exception {
        Category category = categoryRepository.save(new Category("음료"));
        menuGroupRepository.save(menuGroup("리저브 에스프레소", "Reserve Espresso", category));
        menuGroupRepository.save(menuGroup("리저브 드립", "Reserve Drip", category));

        CategoryResponseDTO response = categoryService.findById(category.getId());

        mvc.perform(get("/api/categories/" + category.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("특정 카테고리 조회 시, 하위의 메뉴 그룹 목록을 함께 조회한다.")
    @Test
    void findWithMenuGroupsById() throws Exception {
        Category category = categoryRepository.save(new Category("탄산음료"));
		MenuGroup coke = menuGroupRepository.save(menuGroup("콜라", "Coke", category));
		MenuGroup cider = menuGroupRepository.save(menuGroup("사이다", "Cider", category));

		CategoryMenuGroupsResponseDTO response = CategoryMenuGroupsResponseDTO.builder()
			.id(category.getId())
			.name(category.getName())
			.menuGroups(List.of(
				new MenuGroupResponseDTO(coke.getId(), "콜라", "Coke"),
				new MenuGroupResponseDTO(cider.getId(), "사이다", "Cider")))
			.build();

		mvc.perform(get("/api/categories/{categoryId}/menu-groups", category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    private MenuGroup menuGroup(String name, String englishName, Category category) {
        return MenuGroup.builder()
            .name(name)
            .englishName(englishName)
            .category(category)
            .build();
    }
}
