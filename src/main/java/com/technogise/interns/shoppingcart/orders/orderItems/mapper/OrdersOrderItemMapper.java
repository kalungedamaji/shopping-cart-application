package com.technogise.interns.shoppingcart.orders.orderItems.mapper;

import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import com.technogise.interns.shoppingcart.orders.orderItems.entity.OrdersOrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrdersOrderItemMapper {
    public OrdersOrderItem mapOrdersOrderItemEntity(OrdersOrderItemEntity ordersOrderItemEntity){

        OrdersOrderItem ordersOrderItem = new OrdersOrderItem();
        ordersOrderItem.setId(ordersOrderItemEntity.getId());
        ordersOrderItem.setImage(ordersOrderItemEntity.getImage());
        ordersOrderItem.setName(ordersOrderItemEntity.getName());
        ordersOrderItem.setDescription(ordersOrderItemEntity.getDescription());
        ordersOrderItem.setPrice(ordersOrderItemEntity.getPrice());
        ordersOrderItem.setQuantity(ordersOrderItemEntity.getQuantity());
        return  ordersOrderItem;
    }

    public OrdersOrderItemEntity mapOrdersOrderItem(OrdersOrderItem ordersOrderItem) {
        ordersOrderItem.setId(UUID.randomUUID());
        OrdersOrderItemEntity ordersOrderItemEntity = new OrdersOrderItemEntity();
        ordersOrderItemEntity.setId(ordersOrderItem.getId());
        ordersOrderItemEntity.setName(ordersOrderItem.getName());
        ordersOrderItemEntity.setDescription(ordersOrderItem.getDescription());
        ordersOrderItemEntity.setImage(ordersOrderItem.getImage());
        ordersOrderItemEntity.setPrice(ordersOrderItem.getPrice());
        ordersOrderItemEntity.setQuantity(ordersOrderItem.getQuantity());
        return ordersOrderItemEntity;
    }
}
