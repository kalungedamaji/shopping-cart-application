package com.technogise.interns.oops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    private ShoppingCart shoppingCart = new ShoppingCart();
    public BigDecimal unitPrice= BigDecimal.valueOf(39.99);


    @Test
    public void testGetAndSetNumberOfProducts(){
        final int EXPECTED_NO_OF_PRODUCTS = 5;
        final int NO_OF_PRODUCTS = 5;

        Product soapDoveProduct = new Product();
        soapDoveProduct.setName("Dove");
        soapDoveProduct.setPrice(unitPrice);
        shoppingCart.addProducts(soapDoveProduct, NO_OF_PRODUCTS);

        int actualNoOfProducts = shoppingCart.getNumberOfProducts();
        Assertions.assertEquals(EXPECTED_NO_OF_PRODUCTS,actualNoOfProducts);

        Product actualProduct = shoppingCart.getProduct();
        Product expectedProduct = new Product();
        expectedProduct.setName("Dove");
        expectedProduct.setPrice(BigDecimal.valueOf(39.99));
        Assertions.assertEquals(expectedProduct, actualProduct);

    }

    @Test
    public void testCalculateTotalPrice()
    {
        int TWO_DIGIT_PRECISION = 2, NO_OF_PRODUCTS= 5;
        Product soapProduct = new Product();
        BigDecimal EXPECTED_TOTAL_PRICE = unitPrice.multiply(BigDecimal.valueOf(NO_OF_PRODUCTS)).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);
        shoppingCart.addProducts(soapProduct, NO_OF_PRODUCTS);
        soapProduct.setPrice(unitPrice);
        BigDecimal actualTotalPrice= shoppingCart.calculateTotalPrice();
        assertEquals(EXPECTED_TOTAL_PRICE, actualTotalPrice);
    }

    @Test
    public void testAddThreeDoveSoapinShoppingCartHavingFiveDoveGivesEightDoveSoap()
    {
        final int EXPECTED_NO_OF_PRODUCTS = 8;

        int INITIAL_PRODUCT = 5, ADDED_PRODUCT = 3;
        Product soapDoveProduct = new Product();
        soapDoveProduct.setName("Dove");
        soapDoveProduct.setPrice(unitPrice);

        shoppingCart.addProducts(soapDoveProduct, INITIAL_PRODUCT);
        shoppingCart.addProducts(soapDoveProduct, ADDED_PRODUCT);

        int actualNoOfProducts = shoppingCart.getNumberOfProducts();

        assertEquals(EXPECTED_NO_OF_PRODUCTS, actualNoOfProducts);

    }

    @Test
    public void testAddThreeDoveSoapinShoppingCartHavingFiveDoveGivesTotalPriceOfEightDoveSoap()
    {
        final BigDecimal EXPECTED_TOTAL_PRICE = BigDecimal.valueOf(319.92);

        int INITIAL_PRODUCT = 5, ADDED_PRODUCT = 3;
        Product soapDoveProduct = new Product();
        soapDoveProduct.setName("Dove");
        soapDoveProduct.setPrice(unitPrice);

        shoppingCart.addProducts(soapDoveProduct, INITIAL_PRODUCT);
        shoppingCart.addProducts(soapDoveProduct, ADDED_PRODUCT);

        BigDecimal actualTotalPrice = shoppingCart.calculateTotalPrice();

        assertEquals(EXPECTED_TOTAL_PRICE, actualTotalPrice);

    }

}
