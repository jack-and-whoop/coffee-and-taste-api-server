package com.jaw.menu.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuRepository;
import com.jaw.menu.ui.MenuRequestDTO;
import com.jaw.menu.ui.MenuResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuResponseDTO create(MenuRequestDTO request) {
        Menu menu = menuRepository.save(request.toEntity());
        return new MenuResponseDTO(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDTO> findAll() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
            .map(MenuResponseDTO::new)
            .collect(Collectors.toList());
    }

    public MenuResponseDTO findById(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(IllegalArgumentException::new);
        return new MenuResponseDTO(menu);
    }
}
