package com.jaw.menu.ui;

import com.jaw.menu.application.MenuGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/menu-groups")
@RestController
public class MenuGroupRestController {

    private final MenuGroupService menuGroupService;

    @PostMapping
    public ResponseEntity<MenuGroupResponseDTO> create(@RequestBody MenuGroupRequestDTO request) {
        MenuGroupResponseDTO response = menuGroupService.create(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupResponseDTO>> findAll() {
        return ResponseEntity.ok(menuGroupService.findAll());
    }

    @GetMapping("/{menuGroupId}")
    public ResponseEntity<MenuGroupResponseDTO> findById(@PathVariable Long menuGroupId) {
        return ResponseEntity.ok(menuGroupService.findById(menuGroupId));
    }
}
