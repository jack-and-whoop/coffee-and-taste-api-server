package com.jaw.category.application;

import com.jaw.category.domain.Category;
import com.jaw.category.ui.CategoryMenuGroupsResponseDTO;
import com.jaw.category.ui.CategoryRequestDTO;
import com.jaw.category.ui.CategoryResponseDTO;
import com.jaw.menu.application.InMemoryMenuGroupRepository;
import com.jaw.menu.domain.MenuGroup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryServiceTest {

    private InMemoryCategoryRepository categoryRepository;
    private InMemoryMenuGroupRepository menuGroupRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setup() {
        categoryRepository = new InMemoryCategoryRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        categoryService = new CategoryService(categoryRepository, menuGroupRepository);
    }

    @AfterEach
    void teardown() {
        categoryRepository.clear();
    }

    @DisplayName("새로운 카테고리를 등록한다.")
    @Test
    void create() {
        CategoryRequestDTO request = new CategoryRequestDTO("상품");
        CategoryResponseDTO response = categoryService.create(request);
        assertThat(response.getName()).isEqualTo(request.getName());
    }

    @DisplayName("전체 카테고리 목록을 조회한다.")
    @Test
    void findAll() {
        categoryRepository.save(new Category("음료"));
        categoryRepository.save(new Category("푸드"));
        List<CategoryResponseDTO> categories = categoryService.findAll();
        assertThat(categories).hasSize(2);
    }

    @DisplayName("특정 카테고리를 조회한다.")
    @Test
    void findById() {
        Category category = categoryRepository.save(new Category("음료"));
        Optional<Category> foundCategory = categoryRepository.findById(category.getId());
        assertThat(foundCategory).contains(category);
    }

    @DisplayName("특정 카테고리 조회 시, 하위의 메뉴 그룹 목록을 함께 조회한다.")
    @Test
    void findWithMenuGroupsById() {
        Category category = categoryRepository.save(categoryRepository.save(new Category("탄산음료")));

        menuGroupRepository.save(menuGroup("콜라", "Coke", category));
        menuGroupRepository.save(menuGroup("사이다", "Cider", category));

        CategoryMenuGroupsResponseDTO foundCategory = categoryService.findWithMenuGroupsById(category.getId());
        assertThat(foundCategory.getName()).isEqualTo("탄산음료");
        assertThat(foundCategory.getMenuGroups()).hasSize(2);
    }

    private MenuGroup menuGroup(String name, String englishName, Category category) {
        return MenuGroup.builder()
            .name(name)
            .englishName(englishName)
            .category(category)
            .build();
    }
}
