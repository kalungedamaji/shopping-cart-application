package com.technogise.interns.shoppingcart.placeorder.convertor;

import com.technogise.interns.shoppingcart.dto.CartItem;
import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ListConvertor {

    private ItemConvertor itemConvertor;

    public ListConvertor(ItemConvertor itemConvertor){
        this.itemConvertor = itemConvertor;
    }
    public List<OrdersOrderItem> cartItemListToOrderItemListConvertor(List<CartItem> cartItemList){
        List<OrdersOrderItem> orderItemList= cartItemList
                .stream()
                .map(itemConvertor::convertCartItemToOrderItem)
                .collect(Collectors.toList());
        return orderItemList;
    }
}
