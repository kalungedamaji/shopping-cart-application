package com.technogise.interns.oops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    Product product = new Product();
    ShoppingCart shoppingCart = new ShoppingCart();
    @Test
    public void testGetNameAndSetName(){
        String EXPECTED_NAME = "Dove";
        int numberOfProducts = shoppingCart.getNumberOfProducts();
        shoppingCart.addProducts(product, numberOfProducts);
        String INPUT_NAME = shoppingCart.getProduct().getName() ;
        product.setName(INPUT_NAME);
        String actualName = product.getName();
        assertEquals(EXPECTED_NAME, actualName);
    }
    @Test
    public void testGetPriceAndSetPrice(){
        BigDecimal EXPECTED_PRICE = BigDecimal.valueOf(39.99);
        BigDecimal INPUT_PRICE = BigDecimal.valueOf(39.99);
        product.setPrice(INPUT_PRICE);
        BigDecimal actualPrice = product.getPrice();
        assertEquals(EXPECTED_PRICE,actualPrice);

    }
}
