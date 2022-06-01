package com.jaw.menu.ui;

import com.jaw.menu.application.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/menus")
public class MenuRestController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuResponseDTO> create(@RequestBody MenuRequestDTO request) {
        MenuResponseDTO response = menuService.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuResponseDTO>> findAll() {
        return ResponseEntity.ok(menuService.findAll());
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDTO> findById(@PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.findById(menuId));
    }
}
