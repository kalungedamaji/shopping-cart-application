package com.technogise.interns.oops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    public void testGetName()
    {
        String expectedName = "Dove";
        Product product = new Product();
        product.setName(expectedName);
        String actualName = product.getName();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    public void testGetPrice()
    {
        BigDecimal expectedPrice = BigDecimal.valueOf(39.99);
        Product product = new Product();
        product.setPrice(expectedPrice);
        BigDecimal actualPrice = product.getPrice();
        Assertions.assertEquals(expectedPrice, actualPrice);
    }

}