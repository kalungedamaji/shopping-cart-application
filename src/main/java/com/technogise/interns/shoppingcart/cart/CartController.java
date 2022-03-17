package com.technogise.interns.shoppingcart.cart;

import com.technogise.interns.oops.Product;
import com.technogise.interns.shoppingcart.dto.CartItem;
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
    @GetMapping("/cart")
    public ResponseEntity<List<Product>> getAllCartItems(){
        List<CartItem> cartList = new ArrayList();

        CartItem cartItem = new CartItem();
        cartItem.setId(UUID.randomUUID());
        cartItem.setName("Laptop");
        cartItem.setPrice(BigDecimal.TEN);
        cartItem.setImage("Laptop Img");
        cartItem.setQuantity(2);
        cartList.add(cartItem);

        CartItem cartItem1 = new CartItem();
        cartItem1.setId(UUID.randomUUID());
        cartItem1.setName("Mug");
        cartItem1.setPrice(BigDecimal.TEN);
        cartItem1.setImage("Mug Img");
        cartItem1.setQuantity(2);
        cartList.add(cartItem1);

        return new ResponseEntity(cartList, HttpStatus.OK) ;
    }
    @GetMapping(value="/cart/{id}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItem> getProduct(@PathVariable(value = "id") String id){
        CartItem cartItem = new CartItem();
        cartItem.setId(UUID.randomUUID());
        cartItem.setName("Laptop");
        cartItem.setPrice(BigDecimal.TEN);
        cartItem.setImage("Laptop Img");
        cartItem.setQuantity(2);
        return new ResponseEntity(cartItem, HttpStatus.OK);
    }
}
