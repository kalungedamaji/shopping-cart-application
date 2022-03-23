package com.technogise.interns.shoppingcart.cart;
import com.technogise.interns.shoppingcart.dto.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CartController {
    final List<CartItem> cartItemList = new ArrayList<>();

    @GetMapping(value="/customers/{customerId}/cart" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartItem>> getAllCartItems(@PathVariable UUID customerId) {
        return new ResponseEntity<>(cartItemList, HttpStatus.OK);
    }

    @GetMapping(value="/customers/{customerId}/cart/{cartItemId}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItem> getCartItemById(@PathVariable(value = "cartItemId")UUID cartItemId, @PathVariable UUID customerId) {
        CartItem cartItem = findById(cartItemId);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PostMapping(path = "/customers/{customerId}/cart",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem, @PathVariable UUID customerId) {
        cartItem.setId(UUID.randomUUID());
        cartItemList.add(cartItem);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @PutMapping("/customers/{customerId}/cart/{cartItemId}")
    public ResponseEntity<CartItem> replaceCartItem(@RequestBody CartItem newCartItem, @PathVariable(value = "cartItemId")UUID cartItemId, @PathVariable UUID customerId) {
        CartItem cartItem = findById(cartItemId);
        if (cartItem != null) {
            cartItem.setId(cartItemId);
            cartItem.setImage(newCartItem.getImage());
            cartItem.setName(newCartItem.getName());
            cartItem.setQuantity(newCartItem.getQuantity());
            cartItem.setPrice(newCartItem.getPrice());
            return new ResponseEntity<>(cartItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/customers/{customerId}/cart/{cartItemId}")
    public ResponseEntity<HttpStatus> deleteCartItem(@PathVariable(value = "cartItemId") UUID cartItemId,@PathVariable UUID customerId) {
        CartItem cartItem = findById(cartItemId);
        if (cartItem != null) {
            cartItemList.remove(cartItem);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public CartItem findById(UUID cartItemId){
        for(CartItem cartItem : cartItemList)
        {
            if(cartItemId.equals(cartItem.getId()))
            {return cartItem;}
        }
        return null;
    }
}