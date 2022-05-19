package com.jaw.category.application;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaw.category.domain.Category;
import com.jaw.category.domain.CategoryRepository;
import com.jaw.category.ui.CategoryRequestDTO;
import com.jaw.category.ui.CategoryResponseDTO;

class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setup() {
        categoryRepository = new InMemoryCategoryRepository();
        categoryService = new CategoryService(categoryRepository);
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
}
