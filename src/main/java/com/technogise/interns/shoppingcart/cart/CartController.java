package com.technogise.interns.shoppingcart.cart;

import com.technogise.interns.shoppingcart.dto.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers/{customerID}")
public class CartController {

    @GetMapping(value="/cart" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartItem>> getAllCartItems(@PathVariable (value = "customerID") UUID customerID ) {

        List<CartItem> cartItemList = new ArrayList();

        CartItem cartItem = new CartItem();
        cartItem.setId(UUID.randomUUID());
        cartItem.setImage("image");
        cartItem.setName("Laptop");
        cartItem.setPrice(BigDecimal.TEN);
        cartItem.setQuantity(1);
        cartItemList.add(cartItem);

        CartItem cartItem1 = new CartItem();
        cartItem1.setId(UUID.randomUUID());
        cartItem1.setImage("image");
        cartItem1.setName("Mobile");
        cartItem1.setPrice(BigDecimal.TEN);
        cartItem1.setQuantity(2);
        cartItemList.add(cartItem1);
        return new ResponseEntity(cartItemList, HttpStatus.OK);
    }

    @GetMapping(value="/cart/{id}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartItem>> getCartItem(@PathVariable(value = "id") UUID cartItemId , @PathVariable
            (value = "customerID") UUID customerID) {
        CartItem cartItem = new CartItem();

        cartItem.setId(cartItemId);
        cartItem.setImage("image");
        cartItem.setName("Laptop");
        cartItem.setPrice(BigDecimal.TEN);
        cartItem.setQuantity(1);

        return new ResponseEntity(cartItem, HttpStatus.OK);
    }
}
