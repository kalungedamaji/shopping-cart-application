package com.technogise.interns.shoppingcart.placeorder.placeorderrepresentation;

import com.technogise.interns.shoppingcart.dto.Order;
import com.technogise.interns.shoppingcart.orders.order.OrderController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LinkGenerator {

    public EntityModel<Order> addLinks(Order order, UUID customerId){
        EntityModel<Order> orderEntityModel = EntityModel.of(order);
        Link allOrdersLink =linkTo(methodOn(OrderController.class).getAllOrders(customerId)).withRel("all-orders");

        orderEntityModel.add(allOrdersLink);
        return orderEntityModel;
    }
}
