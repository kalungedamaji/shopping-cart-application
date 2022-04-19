package com.technogise.interns.shoppingcart.orders.order.mapper;

import com.technogise.interns.shoppingcart.dto.Order;
import com.technogise.interns.shoppingcart.orders.order.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order map(OrderEntity orderEntity){
        return new Order();
    }

    public OrderEntity mapToEntity(Order order){
        return new OrderEntity();
    }
}
