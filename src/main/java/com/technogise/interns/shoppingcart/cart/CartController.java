package com.technogise.interns.shoppingcart.cart;

import com.technogise.interns.oops.Product;
import com.technogise.interns.shoppingcart.dto.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CartController {
    @GetMapping("/cart")
    public ResponseEntity<List<Product>> getAllCartItems(){
        List<CartItem> cartList = new ArrayList();

        CartItem cartItem = new CartItem();
        cartItem.setName("Laptop");
        cartItem.setPrice(BigDecimal.TEN);
        cartItem.setImage("Laptop Img");
        cartItem.setQuantity(2);
        cartList.add(cartItem);

        CartItem cartItem1 = new CartItem();
        cartItem1.setName("Laptop");
        cartItem1.setPrice(BigDecimal.TEN);
        cartItem1.setImage("Laptop Img");
        cartItem1.setQuantity(2);
        cartList.add(cartItem1);

        return new ResponseEntity(cartList, HttpStatus.OK) ;
    }
}
