package com.technogise.interns.shoppingcart.cart.repository;

import com.technogise.interns.shoppingcart.cart.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartItemEntity, UUID> {
    List<CartItemEntity> findByCustomerId(UUID customerId);

    void deleteByCustomerId(UUID customerId);
}
