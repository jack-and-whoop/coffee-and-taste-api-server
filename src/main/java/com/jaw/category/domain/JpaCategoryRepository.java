package com.jaw.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaCategoryRepository extends CategoryRepository, JpaRepository<Category, Long> {

    @Override
    @Query("select c from Category c join fetch c.menuGroups where c.id = :id")
    Optional<Category> findById(@Param("id") Long categoryId);
}
