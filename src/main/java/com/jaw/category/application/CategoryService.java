package com.jaw.category.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.category.domain.Category;
import com.jaw.category.domain.CategoryRepository;
import com.jaw.category.ui.CategoryRequestDTO;
import com.jaw.category.ui.CategoryResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDTO create(CategoryRequestDTO request) {
        Category category = categoryRepository.save(request.toEntity());
        return new CategoryResponseDTO(category.getId(), category.getName());
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
            .stream()
            .map(category -> new CategoryResponseDTO(category.getId(), category.getName()))
            .collect(Collectors.toList());
    }
}
