package com.jaw.cart.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCartRepository extends CartRepository, JpaRepository<Cart, Long> {
}
