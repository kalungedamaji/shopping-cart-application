package com.technogise.interns.shoppingcart.cart.service;

import com.technogise.interns.shoppingcart.cart.entity.CartItemEntity;
import com.technogise.interns.shoppingcart.cart.repository.CartRepository;
import com.technogise.interns.shoppingcart.dto.CartItem;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.MatcherAssert;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private CartRepository cartRepository;



    @Test
    public void testGetAllCartItemsShouldReturnAllCartItemsWhenCartContainsOneItem() {
        List<CartItem> expectedCartItemList = new ArrayList<>();
        CartItem cartItem = new CartItem();
        UUID id = UUID.randomUUID();
        cartItem.setId(id);
        cartItem.setName("Dove");
        cartItem.setImage("Dove Image");
        cartItem.setPrice(BigDecimal.valueOf(59.99));
        cartItem.setQuantity(5);
        expectedCartItemList.add(cartItem);

        List<CartItemEntity> cartItemEntityList = new ArrayList<>();
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setId(cartItem.getId());
        cartItemEntity.setName(cartItem.getName());
        cartItemEntity.setImage(cartItem.getImage());
        cartItemEntity.setPrice(cartItem.getPrice());
        cartItemEntity.setQuantity(cartItem.getQuantity());
        cartItemEntityList.add(cartItemEntity);
        Mockito.when(cartRepository.findAll()).thenReturn(cartItemEntityList);
        List<CartItem> actualCartItemList = cartService.getAllCartItems();

        MatcherAssert.assertThat(actualCartItemList, is(expectedCartItemList));

    }

}
