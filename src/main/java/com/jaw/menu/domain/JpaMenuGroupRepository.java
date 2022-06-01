package com.jaw.menu.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, Long> {

    @Override
    @Query("select mg from MenuGroup mg join fetch mg.menus where mg.id = :id")
    Optional<MenuGroup> findById(@Param("id") Long id);
}
