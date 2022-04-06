package com.technogise.interns.shoppingcart.cart.service;

import com.technogise.interns.shoppingcart.dto.CartItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebMvcTest(value=CartService.class)
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Test
    public void TestGetAllCartItemsShouldReturnAllCartItemsWhenCartContainsOneItem() {
        List<CartItem> expectedCartItemList = new ArrayList<>();
        CartItem cartItem = new CartItem();
        UUID id = UUID.randomUUID();
        cartItem.setId(id);
        cartItem.setName("Dove");
        cartItem.setImage("Dove Image");
        cartItem.setPrice(BigDecimal.valueOf(59.99));
        cartItem.setQuantity(5);
        expectedCartItemList.add(cartItem);

        List<CartItem> actualCartItemList = cartService.getAllCartItems();

        assertEquals(expectedCartItemList, actualCartItemList);
    }
}
