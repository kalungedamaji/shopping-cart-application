package com.technogise.interns.oops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    Product product = new Product();
    ShoppingCart shoppingCart = new ShoppingCart();
    int NO_OF_PRODUCTS = 5;

    @Test
    public void testGetNumberOfProducts(){
        int EXPECTED_NO_OF_PRODUCTS = 5;
        Product  product = new Product();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProducts(product, NO_OF_PRODUCTS);
        int actualNoOfProducts = shoppingCart.getNumberOfProducts();
        Assertions.assertEquals(EXPECTED_NO_OF_PRODUCTS,actualNoOfProducts);
    }
    @Test
    public void testCalculateTotalPrice(){
        int TWO_DIGIT_PRECISION = 2;
        BigDecimal unitPrice = BigDecimal.valueOf(39.99);
        BigDecimal EXPECTED_TOTAL_PRICE = BigDecimal.valueOf(199.95);
        shoppingCart.addProducts(product, NO_OF_PRODUCTS);
        product.setPrice(unitPrice);
        BigDecimal actualTotalPrice = shoppingCart.getProduct().getPrice().multiply(BigDecimal.valueOf(shoppingCart.getNumberOfProducts())).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP) ;
        Assertions.assertEquals(EXPECTED_TOTAL_PRICE,actualTotalPrice);
    }

}
