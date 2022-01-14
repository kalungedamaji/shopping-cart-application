package com.technogise.interns.shoppingcart;

import com.technogise.interns.oops.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ProductTest {

    private Product product = new Product();
    @Test
    public void testSetProductName() {
        String expectedName = "Vishal";
        product.setName(expectedName);
        String actualName = product.getName();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    public void testSetProductPrice() {
        BigDecimal expectedPrice = new BigDecimal(41.32);
        product.setPrice(expectedPrice);
        BigDecimal actualPrice = product.getPrice();
        Assertions.assertEquals(expectedPrice, actualPrice);
    }


}
