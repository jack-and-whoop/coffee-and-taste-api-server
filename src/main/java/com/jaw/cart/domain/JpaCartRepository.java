package com.jaw.cart.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaCartRepository extends CartRepository, JpaRepository<Cart, Long> {

	@Override
	@Query("select c from Cart c join fetch c.cartMenus join fetch c.member where c.id = :id")
	Optional<Cart> findById(@Param("id") Long id);
}
