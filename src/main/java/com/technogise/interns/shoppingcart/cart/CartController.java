package com.technogise.interns.shoppingcart.cart;
import com.technogise.interns.shoppingcart.dto.CartItem;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "Finds all cartItems in the cart",
            response = CartItem.class)
    public ResponseEntity<List<CartItem>> getAllCartItems(@PathVariable UUID customerId) {
        return new ResponseEntity<>(cartItemList, HttpStatus.OK);
    }

    @GetMapping(value="/customers/{customerId}/cart/{cartItemId}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds cartItem by id",
            notes = "Provide an id to get specific cart item detail from the shopping cart",
            response = CartItem.class)

    public ResponseEntity<CartItem> getCartItemById(@ApiParam(value = "ID value for the cartItem you need to retrieve",required = true)
                                                    @PathVariable(value = "cartItemId")UUID cartItemId) {
        CartItem cartItem = findById(cartItemId);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PostMapping(path = "/customers/{customerId}/cart",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creates cartItem",
            notes = "Provide values of the attributes to add a cartItem in the shopping cart",
            response = CartItem.class)

    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem, @PathVariable UUID customerId) {
        cartItem.setId(UUID.randomUUID());
        cartItemList.add(cartItem);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @PutMapping("/customers/{customerId}/cart/{cartItemId}")
    @ApiOperation(value = "Updates cartItem by id",
            notes = "Provide an id and value of all the attributes of cartItem, you want to update",
            response = CartItem.class)

    public ResponseEntity<CartItem> replaceCartItem(@RequestBody CartItem newCartItem, @ApiParam(value = "ID value for the cartItem you need to update",required = true) @PathVariable(value = "cartItemId")UUID cartItemId, @PathVariable UUID customerId) {
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
    @ApiOperation(value = "Delete cartItem by id",
            notes = "Provide an id of cartItem, you want to delete",
            response = CartItem.class)
    public ResponseEntity<HttpStatus> deleteCartItem(@ApiParam(value = "ID value for the cartItem you need to delete",required = true)
                                                     @PathVariable(value = "cartItemId") UUID cartItemId,@PathVariable UUID customerId) {
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