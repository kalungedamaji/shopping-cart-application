package com.technogise.interns.shoppingcart.placeorder.convertor;

import com.technogise.interns.shoppingcart.dto.CartItem;
import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import org.springframework.stereotype.Component;

@Component
public class ItemConvertor {

    public OrdersOrderItem convertCartItemToOrderItem(CartItem cartItem) {
        OrdersOrderItem ordersOrderItem = new OrdersOrderItem();
        ordersOrderItem.setName(cartItem.getName());
        ordersOrderItem.setImage(cartItem.getImage());
        ordersOrderItem.setPrice(cartItem.getPrice());
        ordersOrderItem.setQuantity(cartItem.getQuantity());
        return ordersOrderItem;
    }
}
