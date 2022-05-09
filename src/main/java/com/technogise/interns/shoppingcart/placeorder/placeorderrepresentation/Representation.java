package com.technogise.interns.shoppingcart.placeorder.placeorderrepresentation;

import com.technogise.interns.shoppingcart.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Representation {
    @Autowired
    PlaceOrderLinks placeOrderLinks;

    public EntityModel<Order> placeOrderRepresentation(Order order, UUID customerId){
        EntityModel<Order> orderEntityModel = EntityModel.of(order);
        orderEntityModel.add(placeOrderLinks.prepareLink(customerId));
        return orderEntityModel;
    }
}
