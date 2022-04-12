package com.technogise.interns.shoppingcart.orders.order;

import com.technogise.interns.shoppingcart.dto.*;
import com.technogise.interns.shoppingcart.orders.order.service.OrderService;
import com.technogise.interns.shoppingcart.store.ProductController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/customers/{customerId}")
public class OrderController {
    @Autowired
    private OrderService orderService;

    List<Order> orderList = new ArrayList<>();

    @GetMapping(value="/orders" ,produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all the orders",
             notes = "Returns  all the orders from the shopping cart",
             response = Order.class)
    public ResponseEntity<CollectionModel<EntityModel<Order>>> getAllOrders(@PathVariable(value = "customerId") UUID customerId) {
        List<EntityModel<Order>> entityModelList = new ArrayList<>();
        for(Order order : orderService.getAllOrders()){
            EntityModel<Order> resource = EntityModel.of(order);
            WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getOrderById(order.getId(), customerId));
            resource.add(linkToSelf.withSelfRel());
            entityModelList.add(resource);
        }
        CollectionModel<EntityModel<Order>> resourceList = CollectionModel.of((entityModelList));
        WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getAllOrders(customerId));
        WebMvcLinkBuilder linkTo = linkTo(methodOn(ProductController.class).getAllProducts());
        resourceList.add(linkToSelf.withSelfRel());
        resourceList.add(linkTo.withRel("product-store"));

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(resourceList);
    }

    @GetMapping(value="/orders/{orderId}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get individual order",
            notes = "Returns the order information from the shopping cart",
            response = Order.class)
    public ResponseEntity<EntityModel<Order2>> getOrderById(@PathVariable(value = "orderId") UUID orderId, @PathVariable(value = "customerId") UUID customerId) {

        Order order = findById(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Order2 newOrder = new Order2();
        List<OrdersOrderItem> ordersOrderItemList;
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

        EntityModel<Order2> resource = EntityModel.of(newOrder);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllOrders(customerId));
        WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getOrderById(orderId, customerId));
        resource.add(linkTo.withRel("all-orders"));
        resource.add(linkToSelf.withSelfRel());

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(resource);
    }

    @PostMapping(path = "/orders",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Order>> createOrder(@RequestBody Order order, @PathVariable(value = "customerId") UUID customerId) {


        order = orderService.createOrder(order);
        EntityModel<Order> resource = EntityModel.of(order);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllOrders(customerId));
        WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getOrderById(order.getId(), customerId));
        resource.add(linkTo.withRel("all-orders"));
        resource.add(linkToSelf.withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
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





