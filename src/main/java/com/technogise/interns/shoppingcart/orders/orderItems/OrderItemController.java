package com.technogise.interns.shoppingcart.orders.orderItems;

import com.technogise.interns.shoppingcart.dto.OrderItem;
import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import com.technogise.interns.shoppingcart.dto.Product;
import io.swagger.annotations.ApiOperation;
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
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/customers/{customerId}/orders/{orderId}")
public class OrderItemController {
    final List<OrdersOrderItem> orderItemList = new ArrayList<>();


    @GetMapping(value = "/orderItems", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds all oredeItems",
            notes = "Returns all the orderItemss",
            response = OrderItem.class)
    public ResponseEntity<CollectionModel<EntityModel<OrdersOrderItem>>> getAllOrderItems(@PathVariable UUID customerId, @PathVariable UUID orderId){
        List<EntityModel<OrdersOrderItem>> entityModelList= new ArrayList<>();
        for(OrdersOrderItem ordersOrderItem : orderItemList) {
            EntityModel<OrdersOrderItem> resource = EntityModel.of(ordersOrderItem);
            WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getOrderItem(ordersOrderItem.getId(),customerId,orderId));
            resource.add(linkToSelf.withSelfRel());
            entityModelList.add(resource);
        }
        CollectionModel<EntityModel<OrdersOrderItem>> resourceList = CollectionModel.of(entityModelList);
        WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getAllOrderItems(customerId, orderId));
        resourceList.add(linkToSelf.withSelfRel());
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(resourceList);
    }
    @GetMapping("/orderItems/{orderItemId}")
    public ResponseEntity<EntityModel<OrdersOrderItem>> getOrderItem(@PathVariable UUID orderItemId, @PathVariable UUID customerId, @PathVariable UUID orderId){
        OrdersOrderItem orderItem = findById(orderItemId);
        EntityModel<OrdersOrderItem> resource = EntityModel.of(orderItem);

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllOrderItems(customerId, orderId));
        WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getOrderItem(orderItemId, customerId, orderId));

        resource.add(linkTo.withRel("all-orderItems"));
        resource.add(linkToSelf.withSelfRel());

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(resource);
    }

    @PostMapping(path = "/orderItems")
    public EntityModel<OrdersOrderItem> createOrderItem(@RequestBody OrdersOrderItem newOrderItem, @PathVariable UUID customerId, @PathVariable UUID orderId){
        newOrderItem.setId(UUID.randomUUID());
        orderItemList.add(newOrderItem);
        EntityModel<OrdersOrderItem> resource = EntityModel.of(newOrderItem);

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllOrderItems(customerId, orderId));
        WebMvcLinkBuilder linkToGetSelf = linkTo(methodOn(this.getClass()).getOrderItem(newOrderItem.getId(),customerId,orderId));

        resource.add(linkTo.withRel("all-orderItems"));
        resource.add(linkToGetSelf.withSelfRel());

        return resource;
    }
    @PutMapping("/orderItems/{orderItemId}")
    public ResponseEntity<OrdersOrderItem> updateOrderItem(@RequestBody OrdersOrderItem newOrderItem , @PathVariable UUID orderItemId, @PathVariable UUID customerId, @PathVariable UUID orderId)
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
    public ResponseEntity<HttpStatus> deleteOrderItem(@PathVariable UUID orderItemId, @PathVariable UUID customerId, @PathVariable UUID orderId){
        OrdersOrderItem orderItem= findById(orderItemId);
        if(orderItem!=null){
            orderItemList.remove(orderItem);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
        {  return new ResponseEntity<>(HttpStatus.NOT_FOUND);        }
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
