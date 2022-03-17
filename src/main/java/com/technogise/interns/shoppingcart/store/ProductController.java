package com.technogise.interns.shoppingcart.store;

//import com.technogise.interns.oops.Product;

import com.technogise.interns.shoppingcart.dto.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
@RestController

public class ProductController {
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productList = new ArrayList<>();
        Product product = new Product();

        product.setId(UUID.randomUUID());
        product.setName("Laptop");
        product.setPrice(BigDecimal.TEN);
        product.setDescription("It is a windows laptop.");
        product.setImage("Laptop img");
        productList.add(product);

        Product product1 = new Product();

        product1.setId(UUID.randomUUID());
        product1.setName("Laptop");
        product1.setPrice(BigDecimal.TEN);
        product1.setDescription("It is a windows laptop.");
        product1.setImage("Laptop img");
        productList.add(product1);

        return new ResponseEntity(productList, HttpStatus.OK);

    }
    @GetMapping("/products/{id}")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable(value = "id")UUID productId) {

        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();

        product1.setId(productId);
        product1.setName("Laptop");
        product1.setPrice(BigDecimal.TEN);
        product1.setDescription("It is a windows laptop.");
        product1.setImage("Laptop img");

        productList.add(product1);

        return new ResponseEntity(productList, HttpStatus.OK);
    }
}
