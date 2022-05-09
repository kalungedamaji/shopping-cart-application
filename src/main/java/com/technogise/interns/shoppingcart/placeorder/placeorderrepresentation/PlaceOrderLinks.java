package com.technogise.interns.shoppingcart.placeorder.placeorderrepresentation;

import com.technogise.interns.shoppingcart.orders.order.OrderController;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlaceOrderLinks{
    public Link prepareLink(UUID customerId){
        Link allOrdersLink =linkTo(methodOn(OrderController.class).getAllOrders(customerId)).withRel("all-orders");
    return allOrdersLink;
    }
}