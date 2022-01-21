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
        BigDecimal unitPrice= BigDecimal.valueOf(39.99);
        Product soapProduct = new Product();
        BigDecimal EXPECTED_TOTALPRICE = unitPrice.multiply(BigDecimal.valueOf(No_OF_PRODUCTS)).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);
        shoppingCart.addProducts(soapProduct, No_OF_PRODUCTS);
        soapProduct.setPrice(unitPrice);
        BigDecimal actualTotalPrice= shoppingCart.calculateTotalPriceWithoutTaxes();
        assertEquals(EXPECTED_TOTALPRICE, actualTotalPrice);
    }
    @Test
    public void testIncreaseProductQuantity(){
        final int EXPECTED_NUMBER_OF_PRODUCTS = 8;
        final int NO_OF_PRODUCTS_IN_CART_IN_FIRST_ADD = 5;

        Product soapDoveProduct = new Product();
        soapDoveProduct.setName("Dove");
        soapDoveProduct.setPrice(BigDecimal.valueOf(39.99));
        shoppingCart.addProducts(soapDoveProduct, NO_OF_PRODUCTS_IN_CART_IN_FIRST_ADD);

        final int NO_OF_PRODUCTS_IN_CART_IN_SECOND_ADD = 3;
        shoppingCart.addProducts(soapDoveProduct, NO_OF_PRODUCTS_IN_CART_IN_SECOND_ADD);

        int actualNumberOfProducts = shoppingCart.getNumberOfProducts();
        Assertions.assertEquals(EXPECTED_NUMBER_OF_PRODUCTS, actualNumberOfProducts);

    }
    @Test
    public void testCalculateUpdatedTotalPrice()
    {
        final int No_OF_PRODUCTS= 8;
        final BigDecimal EXPECTED_UPDATED_TOTALPRICE = BigDecimal.valueOf(319.92);
        BigDecimal unitPrice= BigDecimal.valueOf(39.99);
        Product soapProduct = new Product();
        shoppingCart.addProducts(soapProduct, No_OF_PRODUCTS);
        soapProduct.setPrice(unitPrice);
        BigDecimal actualTotalPrice= shoppingCart.calculateTotalPriceWithoutTaxes();
        assertEquals(EXPECTED_UPDATED_TOTALPRICE, actualTotalPrice);
    }

    @Test
    public void testAddMultipleProducts(){
        final int EXPECTED_NO_OF_DOVE_SOAP = 2;
        final int NO_OF_DOVE_SOAP=2;

        Product soapProduct=new Product();
        soapProduct.setName("Dove");
        soapProduct.setPrice(BigDecimal.valueOf(39.99));
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        int actualNoOfDoveSoap= shoppingCart.getProductQuantity(soapProduct);

        final int EXPECTED_NO_OF_AXE_DEO = 3;
        final int NO_OF_AXE_DEO=3;

        Product deoProduct = new Product();
        deoProduct.setName("Deo");
        deoProduct.setPrice(BigDecimal.valueOf(99.99));
        shoppingCart.addProducts(deoProduct,NO_OF_AXE_DEO);

        int actualNoOfAxeDeo= shoppingCart.getProductQuantity(deoProduct);

        boolean testCaseSucceed= ((EXPECTED_NO_OF_AXE_DEO==actualNoOfAxeDeo)&&(EXPECTED_NO_OF_DOVE_SOAP==actualNoOfDoveSoap));
        assertTrue(testCaseSucceed);

    }
    @Test
    public void testCalculateTotalPriceOfCartIncludingTaxes() {
        final BigDecimal EXPECTED_TOTAL_PRICE_OF_CART_INCLUDING_TAXES = BigDecimal.valueOf(314.96);
        final int NO_OF_DOVE_SOAP=2;

        Product soapProduct=new Product();
        soapProduct.setName("Dove");
        soapProduct.setPrice(BigDecimal.valueOf(39.99));
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        final int NO_OF_AXE_DEO=2;

        Product deoProduct = new Product();
        deoProduct.setName("Deo");
        deoProduct.setPrice(BigDecimal.valueOf(99.99));
        shoppingCart.addProducts(deoProduct,NO_OF_AXE_DEO);

        BigDecimal actualTotalPriceOfCartIncludingTaxes = shoppingCart.CalculateTotalPriceOfCartIncludingTaxes();
        assertEquals(EXPECTED_TOTAL_PRICE_OF_CART_INCLUDING_TAXES,actualTotalPriceOfCartIncludingTaxes);
    }

    @Test
    public void testCalculateTotalPriceOfMultipleProductWithoutTax(){
        int TWO_DIGIT_PRECISION = 2;
        BigDecimal doveUnitPrice = BigDecimal.valueOf(39.99);
        BigDecimal deoUnitPrice = BigDecimal.valueOf(99.99);
        final int NO_OF_DOVE_SOAP=2;
        final int NO_OF_AXE_DEO=2;
        BigDecimal EXPECTED_TOTAL_PRICE_WITHOUT_TAX = BigDecimal.valueOf(279.96);

        Product soapProduct = new Product();
        soapProduct.setName("Dove");
        soapProduct.setPrice(doveUnitPrice);
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        Product deoProduct = new Product();
        deoProduct.setName("Deo");
        deoProduct.setPrice(deoUnitPrice);
        shoppingCart.addProducts(deoProduct,NO_OF_AXE_DEO);

        BigDecimal actualTotalPriceWithoutTax=shoppingCart.calculateTotalPriceWithoutTaxes();
        assertEquals(EXPECTED_TOTAL_PRICE_WITHOUT_TAX,actualTotalPriceWithoutTax);

    }
    @Test
    public void testCalculateSalesTax(){
        int TWO_DIGIT_PRECISION = 2;
        final BigDecimal EXPECTED_TOTAL_SALES_TAX = BigDecimal.valueOf(35.00).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);

        final int NO_OF_DOVE_SOAP=2;

        Product soapProduct=new Product();
        soapProduct.setName("Dove");
        soapProduct.setPrice(BigDecimal.valueOf(39.99));
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        final int NO_OF_AXE_DEO=2;

        Product deoProduct=new Product();
        deoProduct.setName("Deo");
        deoProduct.setPrice(BigDecimal.valueOf(99.99));
        shoppingCart.addProducts(deoProduct,NO_OF_AXE_DEO);

        BigDecimal actualTotalSalesTax=shoppingCart.calculateTotalSalesTax();
        assertEquals(EXPECTED_TOTAL_SALES_TAX,actualTotalSalesTax);

    }
}

