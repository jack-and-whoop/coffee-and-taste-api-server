package com.jaw.cart.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaCartMenuRepository extends CartMenuRepository, JpaRepository<CartMenu, Long> {

	@Query("select cm from CartMenu cm join fetch cm.menu where cm.cart = :cart")
	List<CartMenu> findAllByCart(@Param("cart") Cart cart);
}
