package com.jaw.cart.domain;

import java.util.Optional;

public interface CartRepository {

	Cart save(Cart cart);

	Optional<Cart> findByMemberId(Long memberId);

	Optional<Cart> findById(Long id);
}
