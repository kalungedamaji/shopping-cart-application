package com.technogise.interns.shoppingcart.cart.service;

import com.technogise.interns.shoppingcart.cart.entity.CartItemEntity;
import com.technogise.interns.shoppingcart.cart.mapper.CartMapper;
import com.technogise.interns.shoppingcart.cart.repository.CartRepository;
import com.technogise.interns.shoppingcart.dto.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {

        @Autowired
        private CartRepository cartRepository;

        public List<CartItem> getAllCartItems() {
                return cartRepository.findAll()
                       .stream()
                       .map(CartMapper::entityToCartItemConvertor)
                       .collect(Collectors.toList());
        }

        public CartItem createCartItem(CartItem cartItem){
                cartItem.setId(UUID.randomUUID());

                CartItemEntity cartItemEntity = cartRepository.save(CartMapper.cartItemToEntityConvertor(cartItem));
               return CartMapper.entityToCartItemConvertor(cartItemEntity);
        }




}
