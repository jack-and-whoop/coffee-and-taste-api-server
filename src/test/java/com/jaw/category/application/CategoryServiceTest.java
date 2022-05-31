package com.jaw.category.application;

import com.jaw.category.domain.Category;
import com.jaw.category.ui.CategoryRequestDTO;
import com.jaw.category.ui.CategoryResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryServiceTest {

    private InMemoryCategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setup() {
        categoryRepository = new InMemoryCategoryRepository();
        categoryService = new CategoryService(categoryRepository);
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
}
