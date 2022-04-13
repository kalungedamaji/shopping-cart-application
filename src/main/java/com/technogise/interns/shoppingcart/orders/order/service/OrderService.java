package com.technogise.interns.shoppingcart.orders.order.service;

import com.technogise.interns.shoppingcart.dto.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    public Order createOrder(Order order) {
        return order;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>();
    }
}
