package com.jaw.menu.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.menu.domain.MenuGroup;
import com.jaw.menu.domain.MenuGroupRepository;
import com.jaw.menu.ui.MenuGroupRequestDTO;
import com.jaw.menu.ui.MenuGroupResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MenuGroupService {

    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupResponseDTO create(MenuGroupRequestDTO request) {
        MenuGroup menuGroup = menuGroupRepository.save(request.toEntity());
        return createResponseFromEntity(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponseDTO> findAll() {
        return menuGroupRepository.findAll()
            .stream()
            .map(this::createResponseFromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MenuGroupResponseDTO findById(Long menuGroupId) {
        MenuGroup menuGroup = menuGroupRepository.findById(menuGroupId)
            .orElseThrow(IllegalArgumentException::new);
        return createResponseFromEntity(menuGroup);
    }

    private MenuGroupResponseDTO createResponseFromEntity(MenuGroup menuGroup) {
        return MenuGroupResponseDTO.builder()
            .id(menuGroup.getId())
            .name(menuGroup.getName())
            .englishName(menuGroup.getEnglishName())
            .build();
    }
}
