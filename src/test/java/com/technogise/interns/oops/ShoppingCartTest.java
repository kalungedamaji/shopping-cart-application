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
    public void testCalculateTotalPrice()
    {
        int TWO_DIGIT_PRECISION = 2, No_OF_PRODUCTS= 5;
        BigDecimal unitPrice= BigDecimal.valueOf(39.9);
        Product soapProduct = new Product();
        BigDecimal EXPECTED_TOTALPRICE = unitPrice.multiply(BigDecimal.valueOf(No_OF_PRODUCTS)).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);
        shoppingCart.addProducts(soapProduct, No_OF_PRODUCTS);
        soapProduct.setPrice(unitPrice);
        BigDecimal actualTotalPrice= shoppingCart.calculateTotalPrice();
        assertEquals(EXPECTED_TOTALPRICE, actualTotalPrice);
    }

    @Test
    public void testGetUpdatedNumberOfProducts(){
        final int EXPECTED_NO_OF_PRODUCTS = 8;
        final int INITIAL_NO_OF_PRODUCTS=5;

        Product soapProduct = new Product();
        soapProduct.setName("Nivea");
        soapProduct.setPrice(BigDecimal.valueOf(20.99));
        shoppingCart.addProducts(soapProduct, INITIAL_NO_OF_PRODUCTS);

        final int NO_OF_PRODUCTS = 3;
        shoppingCart.addProducts(soapProduct, NO_OF_PRODUCTS);

        int actualNoOfProducts=shoppingCart.getNumberOfProducts();
        assertEquals(EXPECTED_NO_OF_PRODUCTS,actualNoOfProducts);

        int TWO_DIGIT_PRECISION = 2;
        BigDecimal unitPrice= BigDecimal.valueOf(20.99);
        BigDecimal EXPECTED_TOTALPRICE = unitPrice.multiply(BigDecimal.valueOf(EXPECTED_NO_OF_PRODUCTS)).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);

        BigDecimal actualTotalPrice= shoppingCart.calculateTotalPrice();
        assertEquals(EXPECTED_TOTALPRICE, actualTotalPrice);
    }



    /*
    @Test
    public void testCalculateUpdatedTotalPrice()
    {
        int TWO_DIGIT_PRECISION = 2, INITIAL_NO_OF_PRODUCTS= 5,TOTAL_NO_OF_PRODUCTS=8;
        BigDecimal unitPrice= BigDecimal.valueOf(39.9);
        BigDecimal EXPECTED_TOTALPRICE = unitPrice.multiply(BigDecimal.valueOf(TOTAL_NO_OF_PRODUCTS)).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);

        Product soapProduct = new Product();
        soapProduct.setName("Nivea");
        soapProduct.setPrice(unitPrice);
        shoppingCart.addProducts(soapProduct, INITIAL_NO_OF_PRODUCTS);

        int NO_OF_PRODUCTS=3;
        shoppingCart.addProducts(soapProduct,NO_OF_PRODUCTS);

        BigDecimal actualTotalPrice= shoppingCart.calculateTotalPrice();
        assertEquals(EXPECTED_TOTALPRICE, actualTotalPrice);
    }
    */
}
