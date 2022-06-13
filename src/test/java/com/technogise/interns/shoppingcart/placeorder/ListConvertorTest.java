package com.technogise.interns.shoppingcart.placeorder;

import com.technogise.interns.shoppingcart.dto.CartItem;
import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import com.technogise.interns.shoppingcart.placeorder.convertor.ListConvertor;
import com.technogise.interns.shoppingcart.placeorder.convertor.ItemConvertor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;



public class ListConvertorTest {

    private ItemConvertor itemConvertor = Mockito.mock(ItemConvertor.class);

    private ListConvertor listConvertor = new ListConvertor(itemConvertor);


    @Test
    public void shouldConvertCartItemListToOrderItemList(){
        List<CartItem> cartItemList = new ArrayList<>();

        CartItem cartItem = new CartItem();
        cartItem.setId(UUID.fromString("c0f4ea01-da2e-41d2-b1df-fce6202c8128"));
        cartItem.setName("Iron");
        cartItem.setImage("image");
        cartItem.setPrice(BigDecimal.valueOf(10.0));
        cartItem.setQuantity(5);
        cartItem.setCustomerId(UUID.fromString("0325b6a7-b147-4335-b323-8380b3612709"));
        cartItemList.add(cartItem);

        List<OrdersOrderItem> orderItemList = new ArrayList<>();
        OrdersOrderItem orderItem = new OrdersOrderItem();
        orderItem.setId(UUID.fromString("edb9b593-757e-4bf1-82a2-6d73495f1020"));
        orderItem.setImage("image");
        orderItem.setName("Iron");
        orderItem.setPrice(BigDecimal.valueOf(10.0));
        orderItem.setQuantity(5);
        orderItemList.add(orderItem);
        Mockito.when(itemConvertor.convertCartItemToOrderItem(cartItem)).thenReturn(orderItem);

        List<OrdersOrderItem> actualOrderItemList = listConvertor.cartItemListToOrderItemListConvertor(cartItemList);
        assertThat(actualOrderItemList,is(orderItemList));
    }
}
