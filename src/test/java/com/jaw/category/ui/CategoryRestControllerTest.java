package com.jaw.category.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.jaw.AbstractControllerTest;
import com.jaw.category.domain.Category;
import com.jaw.category.domain.CategoryRepository;
import com.jaw.menu.domain.MenuGroup;
import com.jaw.menu.domain.MenuGroupRepository;

class CategoryRestControllerTest extends AbstractControllerTest {

	private static final String BASE_URI = "/api/categories";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @DisplayName("새로운 카테고리를 등록한다.")
    @Test
    void create() throws Exception {
        CategoryRequestDTO request = new CategoryRequestDTO("음료");

        mvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("음료"));
    }

    @DisplayName("카테고리 목록을 조회한다.")
    @Test
    void findAll() throws Exception {
		Category beverage = categoryRepository.save(new Category("음료"));
		Category food = categoryRepository.save(new Category("푸드"));

		CategoryResponseDTO beverageResponse = new CategoryResponseDTO(beverage);
		CategoryResponseDTO foodResponse = new CategoryResponseDTO(food);

		mvc.perform(get(BASE_URI))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(List.of(beverageResponse, foodResponse))));
	}

    @DisplayName("특정 카테고리를 조회한다.")
    @Test
    void findById() throws Exception {
        Category category = categoryRepository.save(new Category("음료"));

		CategoryResponseDTO response = new CategoryResponseDTO(category);

		mvc.perform(get(BASE_URI + "/{categoryId}", category.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("특정 카테고리 조회 시, 하위의 메뉴 그룹 목록을 함께 조회한다.")
    @Test
    void findWithMenuGroupsById() throws Exception {
        Category category = categoryRepository.save(new Category("탄산음료"));
		MenuGroup coke = menuGroupRepository.save(menuGroup("콜라", "Coke", category));
		MenuGroup cider = menuGroupRepository.save(menuGroup("사이다", "Cider", category));

		CategoryMenuGroupsResponseDTO response = new CategoryMenuGroupsResponseDTO(category, List.of(coke, cider));

		mvc.perform(get(BASE_URI+ "/{categoryId}/menu-groups", category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    private MenuGroup menuGroup(String name, String englishName, Category category) {
        return MenuGroup.builder()
            .name(name)
            .englishName(englishName)
            .category(category)
            .build();
    }
}
