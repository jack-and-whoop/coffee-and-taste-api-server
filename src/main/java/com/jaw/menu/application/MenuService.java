package com.jaw.menu.application;

import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuRepository;
import com.jaw.menu.ui.MenuRequestDTO;
import com.jaw.menu.ui.MenuResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuResponseDTO create(MenuRequestDTO request) {
        Menu menu = menuRepository.save(request.toEntity());
        return createResponseFromEntity(menu);
    }

    public List<MenuResponseDTO> findAll() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
            .map(this::createResponseFromEntity)
            .collect(Collectors.toList());
    }

    private MenuResponseDTO createResponseFromEntity(Menu menu) {
        return MenuResponseDTO.builder()
            .id(menu.getId())
            .name(menu.getName())
            .price(menu.getPrice())
            .onSale(menu.isOnSale())
            .build();
    }
}
