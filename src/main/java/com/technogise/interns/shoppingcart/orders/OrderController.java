package com.technogise.interns.shoppingcart.orders;

import com.technogise.interns.shoppingcart.dto.Orders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderController {

    List<Orders> orderList = new ArrayList();

    @GetMapping(value="/customers/{customerId}/orders" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Orders>> getAllOrders() {

        return new ResponseEntity(orderList, HttpStatus.OK);
    }

    @GetMapping(value="/customers/{customerId}/orders/{orderId}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Orders>> getOrderById(@PathVariable(value = "orderId") UUID orderId) {

        Orders order = findById(orderId);

        return new ResponseEntity(order, HttpStatus.OK);
    }

    @PostMapping(path = "/customers/{customerId}/orders",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orders> createOrder(@RequestBody Orders order) {

        order.setId(UUID.randomUUID());
//        LocalDate localDate = LocalDate.now();
//        order.setDate(localDate);
//        LocalTime localTime = LocalTime.now();
//        order.setTime(localTime);
//        orderList.add(order);
        Instant instant = Instant.now();
        order.setTimestamp(instant);
        orderList.add(order);
        return new ResponseEntity(order, HttpStatus.CREATED);
    }

    @PutMapping("/customers/{customerId}/orders/{orderId}")
    public ResponseEntity<Orders> replaceOrder(@RequestBody Orders newOrder, @PathVariable(value = "orderId")UUID orderId) {
        Orders order = findById(orderId);
        if (order != null) {
            order.setTimestamp(newOrder.getTimestamp());
            order.setOrderPaymentType(newOrder.getOrderPaymentType());
            order.setOrderPaymentStatus(newOrder.getOrderPaymentStatus());

            return new ResponseEntity(order, HttpStatus.OK);

        }
        else {
            throw new RuntimeException("Order Not found");
        }
    }

    @DeleteMapping("/customers/{customerId}/orders/{orderId}")
    void deleteOrder(@PathVariable(value = "orderId") UUID orderId) {
        Orders order = findById(orderId);
        if (order != null) {
            orderList.remove(order);
        }else {
            throw new RuntimeException("Order Not found");
        }

    }

    public Orders findById(UUID orderId){
        for(Orders order : orderList)
        {
            if(orderId.equals(order.getId()))
            {
                return order;
            }
        }
        return null;
    }
}
