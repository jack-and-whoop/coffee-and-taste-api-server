package com.jaw.category.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.category.domain.Category;
import com.jaw.category.domain.CategoryRepository;
import com.jaw.category.ui.CategoryMenuGroupsResponseDTO;
import com.jaw.category.ui.CategoryRequestDTO;
import com.jaw.category.ui.CategoryResponseDTO;
import com.jaw.menu.domain.MenuGroup;
import com.jaw.menu.domain.MenuGroupRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MenuGroupRepository menuGroupRepository;

    public CategoryResponseDTO create(CategoryRequestDTO request) {
        Category category = categoryRepository.save(request.toEntity());
        return new CategoryResponseDTO(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
            .stream()
            .map(CategoryResponseDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO findById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(IllegalArgumentException::new);
        return new CategoryResponseDTO(category);
    }

    @Transactional(readOnly = true)
    public CategoryMenuGroupsResponseDTO findWithMenuGroupsById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(IllegalArgumentException::new);
        List<MenuGroup> menuGroups = menuGroupRepository.findAllByCategoryId(categoryId);
        return new CategoryMenuGroupsResponseDTO(category, menuGroups);
    }
}
