package com.technogise.interns.shoppingcart.orders;

import com.technogise.interns.shoppingcart.dto.Order;
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

    final List<Order> orderList = new ArrayList<>();

    @GetMapping(value="/orders" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getAllOrders(@PathVariable UUID customerId) {
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping(value="/orders/{orderId}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "orderId") UUID orderId, @PathVariable UUID customerId) {
        Order order = findById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping(path = "/orders",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@RequestBody Order order, @PathVariable UUID customerId) {

        order.setId(UUID.randomUUID());
        Instant instant = Instant.now();
        order.setTimestamp(instant);
        orderList.add(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Order> replaceOrder(@RequestBody Order newOrder, @PathVariable(value = "orderId")UUID orderId, @PathVariable UUID customerId) {
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
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable(value = "orderId") UUID orderId, @PathVariable UUID customerId) {
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
        {if(orderId.equals(order.getId()))
            {
                return order;
            }
        }
        return null;
    }
}





