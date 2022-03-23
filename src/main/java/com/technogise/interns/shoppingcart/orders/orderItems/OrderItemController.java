package com.technogise.interns.shoppingcart.orders.orderItems;

import com.technogise.interns.shoppingcart.dto.OrderItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers/{customerId}/orders/{orderId}")
public class OrderItemController {
    final List<OrderItem> orderItemList = new ArrayList<>();
    @GetMapping("/orderItems")
    public ResponseEntity<List<OrderItem>> getAllOrderItems(@PathVariable UUID customerId, @PathVariable UUID orderId){
        return new ResponseEntity<>(orderItemList,HttpStatus.OK);
    }
    @GetMapping("/orderItems/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable UUID orderItemId, @PathVariable UUID customerId, @PathVariable UUID orderId){
        OrderItem orderItem = findById(orderItemId);
        return new ResponseEntity<>(orderItem,HttpStatus.OK);
    }
    @PostMapping("/orderItems")
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem newOrderItem, @PathVariable UUID customerId, @PathVariable UUID orderId){
        newOrderItem.setId(UUID.randomUUID());
        orderItemList.add(newOrderItem);
        return new ResponseEntity<>(newOrderItem,HttpStatus.CREATED);
    }
    @PutMapping("/orderItems/{orderItemId}")
    public ResponseEntity<OrderItem> updateOrderItem(@RequestBody OrderItem newOrderItem , @PathVariable UUID orderItemId, @PathVariable UUID customerId, @PathVariable UUID orderId)
    {
        OrderItem orderItem= findById(orderItemId);
        if(orderItem!=null)
        {
            orderItem.setName(newOrderItem.getName());
            orderItem.setImage(newOrderItem.getImage());
            orderItem.setDescription(newOrderItem.getDescription());
            return new ResponseEntity<>(orderItem,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/orderItems/{orderItemId}")
    public ResponseEntity<HttpStatus> deleteOrderItem(@PathVariable UUID orderItemId, @PathVariable UUID customerId, @PathVariable UUID orderId){
        OrderItem orderItem= findById(orderItemId);
        if(orderItem!=null){
            orderItemList.remove(orderItem);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
        {  return new ResponseEntity<>(HttpStatus.NOT_FOUND);        }
    }
    public OrderItem findById(UUID orderItemId){
        for(OrderItem orderItem : orderItemList){
            if(orderItem.getId().equals(orderItemId)){
                return orderItem;
            }
        }
        return null;
    }

}
