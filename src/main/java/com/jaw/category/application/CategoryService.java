package com.jaw.category.application;

import java.util.List;
import java.util.stream.Collectors;

import com.jaw.menu.ui.MenuGroupResponseDTO;
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

    public CategoryResponseDTO findById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(IllegalArgumentException::new);

        List<MenuGroupResponseDTO> menuGroups = category.getMenuGroups().stream()
            .map(menuGroup -> new MenuGroupResponseDTO(menuGroup.getId(), menuGroup.getName(), menuGroup.getEnglishName()))
            .collect(Collectors.toList());

        return new CategoryResponseDTO(category.getId(), category.getName(), menuGroups);
    }
}
