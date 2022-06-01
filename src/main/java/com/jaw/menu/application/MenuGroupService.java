package com.jaw.menu.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.menu.domain.Menu;
import com.jaw.menu.domain.MenuGroup;
import com.jaw.menu.domain.MenuGroupRepository;
import com.jaw.menu.domain.MenuRepository;
import com.jaw.menu.ui.MenuGroupMenusResponseDTO;
import com.jaw.menu.ui.MenuGroupRequestDTO;
import com.jaw.menu.ui.MenuGroupResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MenuGroupService {

    private final MenuGroupRepository menuGroupRepository;
    private final MenuRepository menuRepository;

    public MenuGroupResponseDTO create(MenuGroupRequestDTO request) {
        MenuGroup menuGroup = menuGroupRepository.save(request.toEntity());
        return new MenuGroupResponseDTO(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponseDTO> findAll() {
        return menuGroupRepository.findAll()
            .stream()
            .map(MenuGroupResponseDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MenuGroupResponseDTO findById(Long menuGroupId) {
        MenuGroup menuGroup = menuGroupRepository.findById(menuGroupId)
            .orElseThrow(IllegalArgumentException::new);
        return new MenuGroupResponseDTO(menuGroup);
    }

    @Transactional(readOnly = true)
    public MenuGroupMenusResponseDTO findWithMenusById(Long menuGroupId) {
        MenuGroup menuGroup = menuGroupRepository.findById(menuGroupId)
            .orElseThrow(IllegalArgumentException::new);
        List<Menu> menus = menuRepository.findAllByMenuGroupId(menuGroupId);
        return new MenuGroupMenusResponseDTO(menuGroup, menus);
    }
}
