package com.jaw.category.ui;

import com.jaw.category.application.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/categories")
@RestController
public class CategoryRestController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryRequestDTO request) {
        CategoryResponseDTO response = categoryService.create(request);
        return ResponseEntity.created(URI.create("/api/categories/" + response.getId()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.findById(categoryId));
    }

    @GetMapping("/{categoryId}/menu-groups")
    public ResponseEntity<CategoryMenuGroupsResponseDTO> findWithMenuGroupsById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.findWithMenuGroupsById(categoryId));
    }

}
