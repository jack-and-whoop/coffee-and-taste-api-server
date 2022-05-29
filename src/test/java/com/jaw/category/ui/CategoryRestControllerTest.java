package com.jaw.category.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaw.category.application.CategoryService;

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
}
