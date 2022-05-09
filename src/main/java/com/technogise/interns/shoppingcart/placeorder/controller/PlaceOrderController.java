package com.technogise.interns.shoppingcart.placeorder.controller;

import com.technogise.interns.shoppingcart.dto.Order;
import com.technogise.interns.shoppingcart.enums.PaymentType;
import com.technogise.interns.shoppingcart.placeorder.placeorderrepresentation.PlaceOrderLinks;
import com.technogise.interns.shoppingcart.placeorder.placeorderrepresentation.Representation;
import com.technogise.interns.shoppingcart.placeorder.service.PlaceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PlaceOrderController {
    @Autowired
    private PlaceOrderService placeOrderService;
    @Autowired
    private PlaceOrderLinks placeOrderLinks;
    @Autowired
    private Representation representation;

    @PostMapping(path = "/customers/{customerId}/pay",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Order>> proceedToPay(@PathVariable UUID customerId, @RequestBody PaymentType paymentType) {
            Order order = placeOrderService.placeOrder(customerId, paymentType);
            return new ResponseEntity(representation.placeOrderRepresentation(order,customerId), HttpStatus.CREATED);
    }
}
