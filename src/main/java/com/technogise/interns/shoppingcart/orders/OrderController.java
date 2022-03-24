package com.technogise.interns.shoppingcart.orders;

import com.technogise.interns.shoppingcart.dto.Order;
import com.technogise.interns.shoppingcart.dto.Order2;
import com.technogise.interns.shoppingcart.dto.OrderItem;
import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import com.technogise.interns.shoppingcart.orders.orderItems.OrderItemController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers/{customerId}")
public class OrderController {

    List<Order> orderList = new ArrayList();

    @GetMapping(value="/orders" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity(orderList, HttpStatus.OK);
    }

    @GetMapping(value="/orders/{orderId}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getOrderById(@PathVariable(value = "orderId") UUID orderId) {

        Order order = findById(orderId);
        if (order == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Order2 newOrder = new Order2();
        List<OrdersOrderItem> ordersOrderItemList = new ArrayList<>();
        ordersOrderItemList = order.getOrderItems();
        OrdersOrderItem ordersOrderItem = ordersOrderItemList.get(0);
        OrderItem orderItem = new OrderItem();

        orderItem.setId(ordersOrderItem.getId());
        orderItem.setName(ordersOrderItem.getName());
        orderItem.setImage(ordersOrderItem.getImage());
        orderItem.setPrice(ordersOrderItem.getPrice());
        orderItem.setQuantity(ordersOrderItem.getQuantity());

        newOrder.setTimestamp(order.getTimestamp());
        newOrder.setId(order.getId());
        newOrder.setOrderPaymentType(order.getOrderPaymentType());
        newOrder.setOrderPaymentStatus(order.getOrderPaymentStatus());

        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);
        newOrder.setOrderItems(orderItemList);

        return new ResponseEntity(newOrder, HttpStatus.OK);
    }

    @PostMapping(path = "/orders",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        OrderItemController orderItemController  =new OrderItemController();

        order.setId(UUID.randomUUID());
        Instant instant = Instant.now();
        order.setTimestamp(instant);


        orderList.add(order);
        // order.setOrderItems(orderItemController.getOrderItemList());
      //  System.out.println(orderItemController.getOrderItemList());
        return new ResponseEntity(order, HttpStatus.CREATED);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Order> replaceOrder(@RequestBody Order newOrder, @PathVariable(value = "orderId")UUID orderId) {
        Order order = findById(orderId);
        if (order != null) {
            order.setTimestamp(newOrder.getTimestamp());
            order.setOrderPaymentType(newOrder.getOrderPaymentType());
            order.setOrderPaymentStatus(newOrder.getOrderPaymentStatus());
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable(value = "orderId") UUID orderId) {
        Order order = findById(orderId);
        if (order != null) {
            orderList.remove(order);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Order findById(UUID orderId){
        for(Order order : orderList)
        {
            if(orderId.equals(order.getId()))
            {
                return order;
            }
        }
        return null;
    }
}





