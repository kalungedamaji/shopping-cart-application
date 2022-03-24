package com.technogise.interns.shoppingcart.orders.orderItems;

import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers/{customerId}/orders/{orderId}")
public class OrderItemController {
    List<OrdersOrderItem> orderItemList = new ArrayList<>();
    @GetMapping("/orderItems")
    public ResponseEntity<List<OrdersOrderItem>> getAllOrderItems(){
        return new ResponseEntity<>(orderItemList,HttpStatus.OK);
    }
    @GetMapping("/orderItems/{orderItemId}")
    public ResponseEntity<OrdersOrderItem> getOrderItem(@PathVariable UUID orderItemId){
        OrdersOrderItem orderItem = findById(orderItemId);
        return new ResponseEntity<>(orderItem,HttpStatus.OK);
    }
    @PostMapping("/orderItems")
    public ResponseEntity<OrdersOrderItem> createOrderItem(@RequestBody OrdersOrderItem newOrderItem){
        newOrderItem.setId(UUID.randomUUID());
        orderItemList.add(newOrderItem);
        return new ResponseEntity<>(newOrderItem,HttpStatus.CREATED);
    }
    @PutMapping("/orderItems/{orderItemId}")
    public ResponseEntity<OrdersOrderItem> updateOrderItem(@RequestBody OrdersOrderItem newOrderItem , @PathVariable UUID orderItemId)
    {
        OrdersOrderItem orderItem= findById(orderItemId);
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
    public ResponseEntity deleteOrderItem(@PathVariable UUID orderItemId){
        OrdersOrderItem orderItem= findById(orderItemId);
        if(orderItem!=null){
            orderItemList.remove(orderItem);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);        }
    }
    public OrdersOrderItem findById(UUID orderItemId){
        for(OrdersOrderItem orderItem : orderItemList){
            if(orderItem.getId().equals(orderItemId)){
                return orderItem;
            }
        }
        return null;
    }

    public List<OrdersOrderItem> getOrderItemList() {
        return orderItemList;
    }
}
