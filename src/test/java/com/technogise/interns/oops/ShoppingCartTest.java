package com.technogise.interns.oops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    private ShoppingCart shoppingCart = new ShoppingCart();


    @Test
    public void testGetAndSetNumberOfProducts(){
        final int EXPECTED_NO_OF_PRODUCTS = 5;
        final int NO_OF_PRODUCTS = 5;
        Product soapProduct = new Product();
        soapProduct.setName("Nivea");
        soapProduct.setPrice(BigDecimal.valueOf(20.99));

        shoppingCart.addProducts(soapProduct, NO_OF_PRODUCTS);

        int actualNoOfProducts = shoppingCart.getNumberOfProducts();
        Assertions.assertEquals(EXPECTED_NO_OF_PRODUCTS,actualNoOfProducts);

        Product actualProduct = shoppingCart.getProduct();

        Product expectedProduct = new Product();
        expectedProduct.setName("Nivea");
        expectedProduct.setPrice(BigDecimal.valueOf(20.99));
        Assertions.assertEquals(expectedProduct, actualProduct);

    }
    @Test
    public void testCalculateTotalPrice(){
        final int NO_OF_PRODUCTS = 5;
        final BigDecimal EXPECTED_TOTAL_CART_VALUE = BigDecimal.valueOf(199.95);
        Product soapProduct = new Product();
        soapProduct.setName("Dove");
        soapProduct.setPrice(BigDecimal.valueOf(39.99));
        shoppingCart.addProducts(soapProduct, NO_OF_PRODUCTS);

        BigDecimal actualTotalPrice = shoppingCart.calculateTotalPrice();

        Assertions.assertEquals(EXPECTED_TOTAL_CART_VALUE,actualTotalPrice);
    }

}
