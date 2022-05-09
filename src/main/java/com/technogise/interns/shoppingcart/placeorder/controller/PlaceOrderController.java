package com.technogise.interns.shoppingcart.placeorder.controller;

import com.technogise.interns.shoppingcart.dto.Order;
import com.technogise.interns.shoppingcart.dto.PayOrderDetail;
import com.technogise.interns.shoppingcart.placeorder.placeorderrepresentation.LinkGenerator;
import com.technogise.interns.shoppingcart.placeorder.service.PlaceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers/{customerId}")
public class PlaceOrderController {
    @Autowired
    private PlaceOrderService placeOrderService;
    @Autowired
    private LinkGenerator linkGenerator;

    @PostMapping(path = "/pay",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Order>> proceedToPay(@PathVariable UUID customerId, @RequestBody PayOrderDetail paymentType) {
            Order order = placeOrderService.placeOrder(customerId, paymentType);
            return new ResponseEntity<>(linkGenerator.addLinks(order,customerId), HttpStatus.CREATED);
    }
}
