package com.technogise.interns.shoppingcart.orders.order.mapper;

import com.technogise.interns.shoppingcart.dto.Order;
import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import com.technogise.interns.shoppingcart.orders.order.entity.OrderEntity;
import com.technogise.interns.shoppingcart.orders.orderItems.entity.OrdersOrderItemEntity;
import com.technogise.interns.shoppingcart.orders.orderItems.mapper.OrdersOrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    @Autowired
    OrdersOrderItemMapper orderListMapper;

    public Order map(OrderEntity orderEntity){

        List<OrdersOrderItem> orderItemList = orderEntity.getOrderItems()
                .stream()
                .map(orderListMapper:: mapOrdersOrderItemEntity)
                .collect(Collectors.toList());

        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setTimestamp(orderEntity.getTimestamp());
        order.setOrderStatus(orderEntity.getOrderStatus());
        order.setOrderPaymentStatus(orderEntity.getOrderPaymentStatus());
        order.setOrderPaymentType(orderEntity.getOrderPaymentType());
        order.setOrderItems(orderItemList);

        return order;


    }

    public OrderEntity mapToEntity(Order order){

        List<OrdersOrderItemEntity> ordersOrderItemEntityList = order.getOrderItems()
                .stream()
                .map(orderListMapper:: mapOrdersOrderItem)
                .collect(Collectors.toList());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setTimestamp(order.getTimestamp());
        orderEntity.setOrderStatus(order.getOrderStatus());
        orderEntity.setOrderPaymentStatus(order.getOrderPaymentStatus());
        orderEntity.setOrderPaymentType(order.getOrderPaymentType());
        orderEntity.setOrderItems(ordersOrderItemEntityList);

        return orderEntity;
    }
}
