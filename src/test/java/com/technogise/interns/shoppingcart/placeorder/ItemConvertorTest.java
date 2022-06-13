package com.technogise.interns.shoppingcart.placeorder;

import com.technogise.interns.shoppingcart.dto.CartItem;
import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import com.technogise.interns.shoppingcart.placeorder.convertor.ItemConvertor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
public class ItemConvertorTest {

    @Autowired
    private ItemConvertor itemConvertor;
    @Test
    public void testCase(){
        int a=1;
    }

//    @Test
//    public void shouldConvertCartItemToOrderItem(){
//        CartItem cartItem = new CartItem();
//        cartItem.setId(UUID.fromString("c0f4ea01-da2e-41d2-b1df-fce6202c8128"));
//        cartItem.setName("Iron");
//        cartItem.setImage("image");
//        cartItem.setPrice(BigDecimal.valueOf(10.0));
//        cartItem.setQuantity(5);
//        cartItem.setCustomerId(UUID.fromString("0325b6a7-b147-4335-b323-8380b3612709"));
//
//        OrdersOrderItem orderItem = itemConvertor.convertCartItemToOrderItem(cartItem);
//
//        assertThat(orderItem.getImage(),is(cartItem.getImage()));
//        assertThat(orderItem.getName(),is(cartItem.getName()));
//        assertThat(orderItem.getPrice(),is(cartItem.getPrice()));
//        assertThat(orderItem.getQuantity(),is(cartItem.getQuantity()));
//
//    }
}
