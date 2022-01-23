package com.technogise.interns.oops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {



    @Test
    public void testGetAndSetNumberOfProducts(){
        final int EXPECTED_NO_OF_PRODUCTS = 5;
        final int NO_OF_PRODUCTS = 5;
        Product soapProduct = new Product();
        ShoppingCart shoppingCart = new ShoppingCart();
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
        int No_OF_PRODUCTS= 2;
        BigDecimal unitPrice= BigDecimal.valueOf(39.99);
        Product soapProduct = new Product();
        ShoppingCart shoppingCart = new ShoppingCart();
        BigDecimal EXPECTED_TOTAL_PRICE = BigDecimal.valueOf(79.98);

        shoppingCart.addProducts(soapProduct, No_OF_PRODUCTS);
        soapProduct.setPrice(unitPrice);
        BigDecimal actualTotalPrice= shoppingCart.getTotalPriceBeforeTax();
        assertEquals(EXPECTED_TOTAL_PRICE, actualTotalPrice);
    }
    @Test
    public void testAddProductWhenCartIsNotEmpty(){
        int EXPECTED_NUMBER_OF_PRODUCTS = 8;
        int NUMBER_OF_PRODUCTS_ADDED_FIRST = 5;
        int NUMBER_OF_PRODUCTS_ADDED_LATER = 3;

        ShoppingCart shoppingCart = new ShoppingCart();
        Product product = new Product();
        product.setName("Dove");
        BigDecimal unitPriceOfDoveSoap = BigDecimal.valueOf(39.99);
        product.setPrice(unitPriceOfDoveSoap);

        shoppingCart.addProducts(product, NUMBER_OF_PRODUCTS_ADDED_FIRST);
        shoppingCart.addProducts(product, NUMBER_OF_PRODUCTS_ADDED_LATER);
        int ACTUAL_NUMBER_OF_PRODUCTS = shoppingCart.getNumberOfProducts();
        assertEquals(EXPECTED_NUMBER_OF_PRODUCTS,ACTUAL_NUMBER_OF_PRODUCTS);
    }


    @Test
    public void testCalculateTotalPriceOfShoppingCartBeforeTax(){
        BigDecimal EXPECTED_TOTAL_PRICE_OF_SHOPPING_CART = BigDecimal.valueOf(279.96);
        int NO_OF_DOVE_SOAPS = 2;
        int NO_OF_AXE_DEO = 2;
        BigDecimal unitPriceOfDoveSoap = BigDecimal.valueOf(39.99);
        BigDecimal unitPriceOfAxeDeo = BigDecimal.valueOf(99.99);
        Product doveSoap = new Product();
        Product axeDeo = new Product();
        doveSoap.setName("Dove");
        doveSoap.setPrice(unitPriceOfDoveSoap);
        axeDeo.setName("Axe");
        axeDeo.setPrice(unitPriceOfAxeDeo);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProducts(doveSoap, NO_OF_DOVE_SOAPS);
        shoppingCart.addProducts(axeDeo, NO_OF_AXE_DEO);

        BigDecimal ACTUAL_TOTAL_PRICE_OF_SHOPPING_CART = shoppingCart.getTotalPriceBeforeTax();
        assertEquals(EXPECTED_TOTAL_PRICE_OF_SHOPPING_CART,ACTUAL_TOTAL_PRICE_OF_SHOPPING_CART);

    }

    @Test
    public void testTotalSalesTaxInShoppingCart()
    {
        int TWO_DIGIT_PRECISION = 2;
        final BigDecimal EXPECTED_TOTAL_SALES_TAX = BigDecimal.valueOf(35.00).setScale(TWO_DIGIT_PRECISION);
        Product doveSoap = new Product();
        Product axeDeo = new Product();
        ShoppingCart shoppingCart = new ShoppingCart();

        BigDecimal doveUnitPrice = BigDecimal.valueOf(39.99);
        BigDecimal axeUnitPrice = BigDecimal.valueOf(99.99);
        int noOfDoveProduct = 2;
        doveSoap.setName("Dove");
        doveSoap.setPrice(doveUnitPrice);
        shoppingCart.addProducts(doveSoap, noOfDoveProduct);
        int noOfAxeProduct = 2;
        axeDeo.setName("Axe");
        axeDeo.setPrice(axeUnitPrice);
        shoppingCart.addProducts(axeDeo, noOfAxeProduct);

        BigDecimal totalSalesTax = shoppingCart.getTotalSalesTax();
        assertEquals(EXPECTED_TOTAL_SALES_TAX, totalSalesTax);
    }

    @Test
    public void testAddMultipleProduct() {
        int EXPECTED_AXE_DEO = 3;
        int EXPECTED_DOVE_SOAPS = 2;
        BigDecimal unitPriceDeo = BigDecimal.valueOf(99.99);
        BigDecimal unitPriceSoaps = BigDecimal.valueOf(39.99);
        int noOfDoveSoaps= 2;
        int noOfAxeDeo = 3;

        Product axeDeo = new Product();
        axeDeo.setName("Axe Deo");
        axeDeo.setPrice(unitPriceDeo);
        Product doveSoaps = new Product();
        axeDeo.setName("Dove Soaps");
        axeDeo.setPrice(unitPriceSoaps);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProducts(doveSoaps , noOfDoveSoaps);
        shoppingCart.addProducts(axeDeo , noOfAxeDeo);

        int actualDoveSoaps = shoppingCart.getQuantityOfProduct(doveSoaps);
        int actualAxeDeo = shoppingCart.getQuantityOfProduct(axeDeo);
        assertEquals(EXPECTED_AXE_DEO, actualAxeDeo);
        assertEquals(EXPECTED_DOVE_SOAPS, actualDoveSoaps);
    }
    @Test
    public void testCalculateFinalTotalPrice(){
        BigDecimal EXPECTED_TOTAL_PRICE_OF_SHOPPING_CART = BigDecimal.valueOf(314.96);
        int NO_OF_DOVE_SOAPS = 2;
        int NO_OF_AXE_DEO = 2;
        BigDecimal unitPriceOfDoveSoap = BigDecimal.valueOf(39.99);
        BigDecimal unitPriceOfAxeDeo = BigDecimal.valueOf(99.99);
        Product doveSoap = new Product();
        Product axeDeo = new Product();
        doveSoap.setName("Dove");
        doveSoap.setPrice(unitPriceOfDoveSoap);
        axeDeo.setName("Axe");
        axeDeo.setPrice(unitPriceOfAxeDeo);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProducts(doveSoap, NO_OF_DOVE_SOAPS);
        shoppingCart.addProducts(axeDeo, NO_OF_AXE_DEO);

        BigDecimal ACTUAL_TOTAL_PRICE_OF_SHOPPING_CART = shoppingCart.getTotalPrice();
        assertEquals(EXPECTED_TOTAL_PRICE_OF_SHOPPING_CART,ACTUAL_TOTAL_PRICE_OF_SHOPPING_CART);

    }
}
