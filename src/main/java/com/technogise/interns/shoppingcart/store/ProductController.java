package com.technogise.interns.shoppingcart.store;
import com.technogise.interns.shoppingcart.dto.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ProductController {
    final List<Product> productList = new ArrayList<>();
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id")UUID productId) {
        Product product1 = findById(productId);
        return new ResponseEntity<>(product1, HttpStatus.OK);
    }
    @PostMapping(path = "/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct) {
        newProduct.setId(UUID.randomUUID());
        productList.add(newProduct);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> replaceCustomer(@RequestBody Product newProduct, @PathVariable(value = "id")UUID productId)
    {
        Product product = findById(productId);
        if (product != null)
        {
            product.setId(productId);
            product.setName(newProduct.getName());
            product.setImage(newProduct.getImage());
            product.setPrice(newProduct.getPrice());
            product.setDescription(newProduct.getDescription());
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable(value = "id") UUID productID) {
        Product product = findById(productID);
        if (product != null) {
            productList.remove(product);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public Product findById(UUID productID){
        for(Product product : productList )
        {
            if (productID.equals(product.getId()))
            {return product;}
        }
        return null;
    }
}
