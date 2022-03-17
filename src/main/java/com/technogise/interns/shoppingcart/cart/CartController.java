package com.technogise.interns.shoppingcart.cart;

import com.technogise.interns.shoppingcart.dto.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CartController {

    @GetMapping(value="/cart" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cart>> getAllProducts() {

        List<Cart> productList = new ArrayList();

        Cart cartItem = new Cart();

        cartItem.setId(UUID.randomUUID());
        cartItem.setImage("image");
        cartItem.setName("Laptop");
        cartItem.setPrice(BigDecimal.TEN);
        cartItem.setQuantity(1);

        productList.add(cartItem);

        Cart cartItem1 = new Cart();

        cartItem1.setId(UUID.randomUUID());
        cartItem1.setImage("image");
        cartItem1.setName("Mobile");
        cartItem1.setPrice(BigDecimal.TEN);
        cartItem1.setQuantity(2);
        productList.add(cartItem1);
        return new ResponseEntity(productList, HttpStatus.OK);
    }

    @GetMapping(value="/cart/{id}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cart>> getAllProducts(@PathVariable(value = "id") UUID productId) {

        List<Cart> productList = new ArrayList();

        Cart cartItem = new Cart();

        cartItem.setId(productId);
        cartItem.setImage("image");
        cartItem.setName("Laptop");
        cartItem.setPrice(BigDecimal.TEN);
        cartItem.setQuantity(1);
        productList.add(cartItem);

        return new ResponseEntity(productList, HttpStatus.OK);
    }
}
