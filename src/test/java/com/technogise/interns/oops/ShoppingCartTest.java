package com.technogise.interns.oops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    private ShoppingCart shoppingCart = new ShoppingCart();

    final String SOAP_NAME="Dove";
    final BigDecimal DOVE_SOAP_UNIT_PRICE=BigDecimal.valueOf(39.99);

    final String DEO_NAME="Axe";
    final BigDecimal AXE_DEO_UNIT_PRICE=BigDecimal.valueOf(99.99);

    @Test
    public void testGetAndSetNumberOfProducts(){
        final int EXPECTED_NO_OF_PRODUCTS = 5;
        final int NO_OF_PRODUCTS = 5;

        Product soapProduct = new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);

        shoppingCart.addProducts(soapProduct, NO_OF_PRODUCTS);

        int actualNoOfProducts = shoppingCart.getNumberOfProducts();
        Assertions.assertEquals(EXPECTED_NO_OF_PRODUCTS,actualNoOfProducts);

        Product actualProduct = shoppingCart.getProduct(SOAP_NAME);

        Product expectedProduct = new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);

        Assertions.assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void testCalculateTotalPrice()
    {
        int TWO_DIGIT_PRECISION = 2, No_OF_PRODUCTS= 5;

        Product soapProduct = new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);

        BigDecimal EXPECTED_TOTALPRICE = DOVE_SOAP_UNIT_PRICE.multiply(BigDecimal.valueOf(No_OF_PRODUCTS)).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);

        shoppingCart.addProducts(soapProduct, No_OF_PRODUCTS);

        BigDecimal actualTotalPrice= shoppingCart.calculateTotalPriceWithoutTaxes();

        assertEquals(EXPECTED_TOTALPRICE, actualTotalPrice);
    }
    @Test
    public void testIncreaseProductQuantity(){
        final int EXPECTED_NUMBER_OF_PRODUCTS = 8;

        Product soapDoveProduct = new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);

        shoppingCart.addProducts(soapDoveProduct, 5);
        shoppingCart.addProducts(soapDoveProduct, 3);

        int actualNumberOfProducts = shoppingCart.getNumberOfProducts();

        Assertions.assertEquals(EXPECTED_NUMBER_OF_PRODUCTS, actualNumberOfProducts);
    }

    @Test
    public void testCalculateUpdatedTotalPrice()
    {
        final int No_OF_PRODUCTS= 8;
        final BigDecimal EXPECTED_UPDATED_TOTALPRICE = BigDecimal.valueOf(319.92);

        Product soapProduct = new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);
        shoppingCart.addProducts(soapProduct, No_OF_PRODUCTS);

        BigDecimal actualTotalPrice= shoppingCart.calculateTotalPriceWithoutTaxes();
        assertEquals(EXPECTED_UPDATED_TOTALPRICE, actualTotalPrice);
    }

    @Test
    public void testAddMultipleProducts(){
        final int EXPECTED_NO_OF_DOVE_SOAP = 2;
        final int NO_OF_DOVE_SOAP=2;

        Product soapProduct=new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        int actualNoOfDoveSoap= shoppingCart.getProductQuantity(soapProduct);

        final int EXPECTED_NO_OF_AXE_DEO = 3;

        Product deoProduct = new Product(DEO_NAME,AXE_DEO_UNIT_PRICE);
        shoppingCart.addProducts(deoProduct, 3);

        int actualNoOfAxeDeo= shoppingCart.getProductQuantity(deoProduct);

        boolean testCaseSucceed= ((EXPECTED_NO_OF_AXE_DEO==actualNoOfAxeDeo)&&(EXPECTED_NO_OF_DOVE_SOAP==actualNoOfDoveSoap));
        assertTrue(testCaseSucceed);

    }
    @Test
    public void testCalculateTotalPriceOfCartIncludingTaxes() {
        final BigDecimal EXPECTED_TOTAL_PRICE_OF_CART_INCLUDING_TAXES = BigDecimal.valueOf(314.96);
        final int NO_OF_DOVE_SOAP=2;

        Product soapProduct=new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);

        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        final int NO_OF_AXE_DEO=2;

        Product deoProduct = new Product(DEO_NAME,AXE_DEO_UNIT_PRICE);

        shoppingCart.addProducts(deoProduct,NO_OF_AXE_DEO);

        BigDecimal actualTotalPriceOfCartIncludingTaxes = shoppingCart.calculateTotalPriceOfCartIncludingTaxes();
        assertEquals(EXPECTED_TOTAL_PRICE_OF_CART_INCLUDING_TAXES,actualTotalPriceOfCartIncludingTaxes);
    }

    @Test
    public void testCalculateTotalPriceOfMultipleProductWithoutTax(){
        int TWO_DIGIT_PRECISION = 2;

        final int NO_OF_DOVE_SOAP=2;
        final int NO_OF_AXE_DEO=2;
        BigDecimal EXPECTED_TOTAL_PRICE_WITHOUT_TAX = BigDecimal.valueOf(279.96);

        Product soapProduct = new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);

        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        Product deoProduct = new Product(DEO_NAME,AXE_DEO_UNIT_PRICE);

        shoppingCart.addProducts(deoProduct,NO_OF_AXE_DEO);

        BigDecimal actualTotalPriceWithoutTax= shoppingCart.calculateTotalPriceWithoutTaxes();
        assertEquals(EXPECTED_TOTAL_PRICE_WITHOUT_TAX,actualTotalPriceWithoutTax);

    }

    @Test
    public void testCalculateSalesTax(){
        int TWO_DIGIT_PRECISION = 2;
        BigDecimal salesTaxRate=BigDecimal.valueOf(12.5);
        shoppingCart.setSalesTaxMultiplier(salesTaxRate);

        final int NO_OF_DOVE_SOAP=2;

        Product soapProduct=new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);

        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        BigDecimal EXPECTED_TOTAL_DOVE_SOAP_SALES_TAX=BigDecimal.valueOf(NO_OF_DOVE_SOAP).multiply(DOVE_SOAP_UNIT_PRICE).
                multiply(salesTaxRate.divide(BigDecimal.valueOf(100)));

        final int NO_OF_AXE_DEO=2;

        Product deoProduct=new Product(DEO_NAME,AXE_DEO_UNIT_PRICE);

        shoppingCart.addProducts(deoProduct,NO_OF_AXE_DEO);
        BigDecimal EXPECTED_TOTAL_AXE_DEO_SALES_TAX=BigDecimal.valueOf(NO_OF_AXE_DEO).multiply(AXE_DEO_UNIT_PRICE).
                multiply(salesTaxRate.divide(BigDecimal.valueOf(100)));


        BigDecimal EXPECTED_TOTAL_SALES_TAX=EXPECTED_TOTAL_DOVE_SOAP_SALES_TAX.add(EXPECTED_TOTAL_AXE_DEO_SALES_TAX).
                setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);

        BigDecimal actualTotalSalesTax=shoppingCart.calculateTotalSalesTax();
        assertEquals(EXPECTED_TOTAL_SALES_TAX,actualTotalSalesTax);

    }

}

