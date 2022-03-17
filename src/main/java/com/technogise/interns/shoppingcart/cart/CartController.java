package com.technogise.interns.shoppingcart.cart;

import com.technogise.interns.oops.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CartController {

    @GetMapping(value="/cart" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartItem>> getAllProducts() {

        List<CartItem> productList = new ArrayList();

        CartItem product = new CartItem("Laptop", BigDecimal.TEN, false);
        productList.add(product);

        CartItem product1 = new CartItem("Laptop", BigDecimal.TEN, false);
        productList.add(product1);

        return new ResponseEntity(productList, HttpStatus.OK);
    }
}
