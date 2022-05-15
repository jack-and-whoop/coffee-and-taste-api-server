package com.jaw.menu.application;

import com.jaw.menu.domain.MenuGroup;
import com.jaw.menu.domain.MenuGroupRepository;
import com.jaw.menu.ui.MenuGroupRequestDTO;
import com.jaw.menu.ui.MenuGroupResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    private MenuGroupResponseDTO createResponseFromEntity(MenuGroup menuGroup) {
        return new MenuGroupResponseDTO(menuGroup.getId(), menuGroup.getName());
    }
}
