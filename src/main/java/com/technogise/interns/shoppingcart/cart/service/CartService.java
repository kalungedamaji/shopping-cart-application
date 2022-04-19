package com.technogise.interns.shoppingcart.cart.service;

import com.technogise.interns.shoppingcart.cart.entity.CartItemEntity;
import com.technogise.interns.shoppingcart.cart.mapper.CartMapper;
import com.technogise.interns.shoppingcart.cart.repository.CartRepository;
import com.technogise.interns.shoppingcart.dto.CartItem;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {

        @Autowired
        private CartRepository cartRepository;
        @Autowired
        private CartMapper cartMapper;

        public List<CartItem> getAllCartItems() {
                return cartRepository.findAll()
                       .stream()
                       .map(cartMapper::entityToCartItemConvertor)
                       .collect(Collectors.toList());
        }

        public CartItem addProductToCart(CartItem cartItem){
                cartItem.setId(UUID.randomUUID());

                CartItemEntity cartItemEntity = cartRepository.save(cartMapper.cartItemToEntityConvertor(cartItem));
               return cartMapper.entityToCartItemConvertor(cartItemEntity);
        }


        public void deleteCartItemById(UUID cartItemId) {
                if(cartRepository.findById(cartItemId).isPresent()) {
                        cartRepository.deleteById(cartItemId);
                }
                else{
                        throw new EntityNotFoundException(CartItem.class, "id", cartItemId.toString());
                }
        }

        public CartItem getCartItemById(UUID cartItemId) {

                Optional<CartItemEntity> optionalCartItemEntity = cartRepository.findById(cartItemId);
                if(optionalCartItemEntity.isPresent()){
                        return cartMapper.entityToCartItemConvertor(optionalCartItemEntity.get());
                }
                else{
                        throw new EntityNotFoundException(CartItem.class, "id", cartItemId.toString());
                }

        }
        public CartItem updateCartItem(CartItem cartItemDetail, UUID cartItemId) {
                if(cartRepository.findById(cartItemId).isPresent()) {
                        cartItemDetail.setId(cartItemId);
                        CartItemEntity customerEntity = cartRepository.save(cartMapper.cartItemToEntityConvertor(cartItemDetail));

                        return Optional.of(cartMapper.entityToCartItemConvertor(customerEntity)).get();
                }
                else{
                        throw new EntityNotFoundException(CartItem.class,"id",cartItemId.toString());
                }
        }

}
