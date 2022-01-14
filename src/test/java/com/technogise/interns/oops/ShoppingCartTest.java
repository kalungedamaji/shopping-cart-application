package com.technogise.interns.oops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartTest {
    ShoppingCart shoppingCart=new ShoppingCart();
    Product product=new Product();

    @Test
    public void testSetGetNumberOfProduct(){
        int EXPECTED_NUMEBER_OF_PRODUCTS=5,INPUT_NUMBER_OF_PRODUCTS=5;
        shoppingCart.setNumberOfProducts(INPUT_NUMBER_OF_PRODUCTS);
        int actualNumberOfProducts=shoppingCart.getNumberOfProducts();
        assertEquals(EXPECTED_NUMEBER_OF_PRODUCTS,actualNumberOfProducts);
    }
    @Test
    public void testSetProductGetProduct(){
        Product EXPECTED_PRODUCT=product;
        shoppingCart.setProduct(product);
        Product actualProduct=shoppingCart.getProduct();
        assertEquals(EXPECTED_PRODUCT,actualProduct);
    }
    @Test
    public void testAddProducts(){
        int EXPECTED_PRODUCTS=5;
        shoppingCart.addProducts(product,5);
        int actualProduct=shoppingCart.getNumberOfProducts();
        assertEquals(EXPECTED_PRODUCTS,actualProduct);
    }
    @Test
    public void testCalculateTotalPrice(){
        BigDecimal EXPECTED_TOTAL_PRICE=BigDecimal.valueOf(199.95);
        product.setPrice(BigDecimal.valueOf(39.99));
        product.setName("Dove");
        shoppingCart.setProduct(product);
        shoppingCart.setNumberOfProducts(5);
        BigDecimal actualTotalPrice=shoppingCart.calculateTotalPrice();
        assertEquals(EXPECTED_TOTAL_PRICE,actualTotalPrice);
    }






}
