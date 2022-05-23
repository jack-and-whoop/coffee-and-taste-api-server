package com.jaw.cart.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCartMenuRepository extends CartMenuRepository, JpaRepository<CartMenu, Long> {
}
