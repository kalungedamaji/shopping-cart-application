package com.technogise.interns.oops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ProductTest {

    @Test
    public void testSetNameDove(){
        String NAME="Dove",EXPECTED_NAME="Dove";
        Product product=new Product();
        product.setName(NAME);
        Assertions.assertEquals(EXPECTED_NAME,NAME);
    }
    @Test
    public void testGetNameDove(){
        String EXPECTED_NAME="Dove";
        Product product=new Product();
        product.setName("Dove");
        String actualName = product.getName();
        Assertions.assertEquals(EXPECTED_NAME,actualName);
    }
    @Test
    public void testSetPrice(){
        double PRICE=39.99,EXPECTED_PRICE=39.99;
        BigDecimal b =new BigDecimal(PRICE);
        BigDecimal d =new BigDecimal(EXPECTED_PRICE);
        Product product=new Product();
        product.setPrice(b);
        Assertions.assertEquals(d,b);
    }
    @Test
    public void testGetPrice(){
        double EXPECTED_PRICE=39.99;
        Product product=new Product();
        BigDecimal d =new BigDecimal(EXPECTED_PRICE);
        BigDecimal b =new BigDecimal(39.99);
        product.setPrice(b);
        BigDecimal actualPrice = product.getPrice();
        Assertions.assertEquals(d,actualPrice);
    }



}
