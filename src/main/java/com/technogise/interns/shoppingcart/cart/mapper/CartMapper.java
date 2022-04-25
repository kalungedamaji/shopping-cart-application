package com.technogise.interns.shoppingcart.cart.mapper;

import com.technogise.interns.shoppingcart.cart.entity.CartItemEntity;
import com.technogise.interns.shoppingcart.dto.CartItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    private final Logger logger = LoggerFactory.getLogger(CartMapper.class);

    public CartItem entityToCartItemConvertor (CartItemEntity cartItemEntity) {
        logger.trace("entityToCartItemConvertor() called with entity: "+cartItemEntity.toString());

        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemEntity.getId());
        cartItem.setName(cartItemEntity.getName());
        cartItem.setPrice(cartItemEntity.getPrice());
        cartItem.setQuantity(cartItemEntity.getQuantity());
        cartItem.setImage(cartItemEntity.getImage());
        logger.trace("Converted cartItem: "+cartItem.toString());
        return cartItem;
    }

    public CartItemEntity cartItemToEntityConvertor(CartItem cartItem){
        logger.trace("cartItemToEntityConvertor() called with entity: "+cartItem.toString());

        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setId(cartItem.getId());
        cartItemEntity.setName(cartItem.getName());
        cartItemEntity.setImage(cartItem.getImage());
        cartItemEntity.setPrice(cartItem.getPrice());
        cartItemEntity.setQuantity(cartItem.getQuantity());
        logger.trace("Converted entity: "+cartItemEntity.toString());
        return cartItemEntity;
    }
}
