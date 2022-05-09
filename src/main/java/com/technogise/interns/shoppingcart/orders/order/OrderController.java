package com.technogise.interns.shoppingcart.orders.order;

import com.technogise.interns.shoppingcart.dto.*;
import com.technogise.interns.shoppingcart.orders.order.service.OrderService;
import com.technogise.interns.shoppingcart.store.controller.ProductController;
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


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/customers/{customerId}")
public class OrderController {
    @Autowired
    private OrderService orderService;


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

        Optional<Order> optionalOrder = orderService.getOrderById(orderId);

            Order2 newOrder = new Order2();
            List<OrdersOrderItem> ordersOrderItemList;
            ordersOrderItemList = optionalOrder.get().getOrderItems();
            OrdersOrderItem ordersOrderItem = ordersOrderItemList.get(0);
            OrderItem orderItem = new OrderItem();

            orderItem.setId(ordersOrderItem.getId());
            orderItem.setName(ordersOrderItem.getName());
            orderItem.setImage(ordersOrderItem.getImage());
            orderItem.setPrice(ordersOrderItem.getPrice());
            orderItem.setQuantity(ordersOrderItem.getQuantity());

            newOrder.setTimestamp(optionalOrder.get().getTimestamp());
            newOrder.setId(optionalOrder.get().getId());
            newOrder.setOrderPaymentType(optionalOrder.get().getOrderPaymentType());
            newOrder.setOrderPaymentStatus(optionalOrder.get().getOrderPaymentStatus());

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
    public ResponseEntity<EntityModel<Order>> createOrder(@RequestBody Order optionalOrder, @PathVariable(value = "customerId") UUID customerId) {


        optionalOrder = orderService.createOrder(optionalOrder);
        EntityModel<Order> resource = EntityModel.of(optionalOrder);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllOrders(customerId));
        WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getOrderById(optionalOrder.getId(), customerId));
        resource.add(linkTo.withRel("all-orders"));
        resource.add(linkToSelf.withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<EntityModel<Optional<Order>>> replaceOrder(@RequestBody Order newOrder, @PathVariable(value = "orderId")UUID orderId, @PathVariable(value = "customerId") UUID customerId) {
        Optional<Order> optionalReplacedOrder = orderService.replaceOrder(newOrder, orderId);
            EntityModel<Optional<Order>> resource = EntityModel.of(optionalReplacedOrder);
            WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllOrders(customerId));
            WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getOrderById(orderId, customerId));
            resource.add(linkTo.withRel("all-orders"));
            resource.add(linkToSelf.withSelfRel());

            return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable(value = "orderId") UUID orderId) {
         orderService.deleteOrder(orderId);
            return new ResponseEntity<>(HttpStatus.OK);
    }
}





