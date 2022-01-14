package com.technogise.interns.oops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ShoppingCartTest {
    ShoppingCart shoppingCart = new ShoppingCart();
    Product product = new Product();
    BigDecimal unitPrice= BigDecimal.valueOf(39.99);
    int No_OF_PRODUCTS= 5;

    @Test
    public void testCalculateTotalPrice()
    {
        int TWO_DIGIT_PRECISION = 2;
        BigDecimal EXPECTED_TOTALPRICE = BigDecimal.valueOf(5*39.99).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);
        shoppingCart.addProducts(product, No_OF_PRODUCTS);
        product.setPrice(unitPrice);
        BigDecimal actualTotalPrice= shoppingCart.calculateTotalPrice();
        Assertions.assertEquals(EXPECTED_TOTALPRICE, actualTotalPrice);
    }

    @Test
    public void testSetAndGetNumberOfProducts()
    {
        int EXPECTED_NO_OF_PRODUCTS = 5;
        product.setPrice(unitPrice);
        shoppingCart.addProducts(product, No_OF_PRODUCTS);
        int actualNumberOfProducts = shoppingCart.getNumberOfProducts();
        Assertions.assertEquals(EXPECTED_NO_OF_PRODUCTS , actualNumberOfProducts);
    }

    @Test
    public void testSetProductsGetProduct()
    {
        shoppingCart.addProducts(product, No_OF_PRODUCTS);
        Product actualObject = shoppingCart.getProduct();
        Assertions.assertNotNull(actualObject);
    }

}
