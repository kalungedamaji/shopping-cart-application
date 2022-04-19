package com.technogise.interns.shoppingcart.cart.mapper;

import com.technogise.interns.shoppingcart.cart.entity.CartItemEntity;
import com.technogise.interns.shoppingcart.dto.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    public CartItem entityToCartItemConvertor (CartItemEntity cartItemEntity) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemEntity.getId());
        cartItem.setName(cartItemEntity.getName());
        cartItem.setPrice(cartItemEntity.getPrice());
        cartItem.setQuantity(cartItemEntity.getQuantity());
        cartItem.setImage(cartItemEntity.getImage());

        return cartItem;
    }

    public CartItemEntity cartItemToEntityConvertor(CartItem cartItem){

        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setId(cartItem.getId());
        cartItemEntity.setName(cartItem.getName());
        cartItemEntity.setImage(cartItem.getImage());
        cartItemEntity.setPrice(cartItem.getPrice());
        cartItemEntity.setQuantity(cartItem.getQuantity());

        return cartItemEntity;
    }
}
