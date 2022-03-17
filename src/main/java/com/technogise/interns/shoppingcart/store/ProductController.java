package com.technogise.interns.shoppingcart.store;

import com.technogise.interns.shoppingcart.dto.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@RestController
public class ProductController {

    @GetMapping(value="/products" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = new ArrayList();

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Laptop");
        product.setPrice(BigDecimal.TEN);
        product.setDescription("Your perfect pack for everyday use");
        product.setImage("laptop img");
        productList.add(product);

        Product product1 = new Product();
        product.setId(UUID.randomUUID());
        product1.setName("Laptop");
        product1.setPrice(BigDecimal.TEN);
        product1.setDescription("Your perfect pack for everyday use");
        product1.setImage("laptop img");
        productList.add(product1);

        return new ResponseEntity(productList, HttpStatus.OK);
    }

    @GetMapping(value="/products/{id}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") String id){
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Laptop");
        product.setPrice(BigDecimal.TEN);
        product.setDescription("Your perfect pack for everyday use");
        product.setImage("laptop img");
        return new ResponseEntity(product, HttpStatus.OK);
    }
}
