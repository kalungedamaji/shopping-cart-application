package com.technogise.interns.oops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product = new Product();

    @Test
    public void testSetNameGetName()
    {
        final String EXPECTED_NAME = "Dove";
        final String INPUT_NAME = "Dove";

        product.setName(INPUT_NAME);
        String actualName = product.getName();
        assertEquals(EXPECTED_NAME, actualName);

    }

    @Test
    public void testSetPriceGetPrice()
    {
        final BigDecimal EXPECTED_PRICE = BigDecimal.valueOf(39.99);
        final BigDecimal INPUT_PRICE = BigDecimal.valueOf(39.99);
        product.setPrice(INPUT_PRICE);
        BigDecimal actualPrice = product.getPrice();
        assertEquals(EXPECTED_PRICE, actualPrice);
    }
}


