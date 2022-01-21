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
        final String SOAP_NAME="Nivea";
        final BigDecimal NIVEA_SOAP_UNIT_PRICE=BigDecimal.valueOf(20.99);

        Product soapProduct = new Product();
        soapProduct.setName(SOAP_NAME);
        soapProduct.setPrice(NIVEA_SOAP_UNIT_PRICE);
        shoppingCart.addProducts(soapProduct, NO_OF_PRODUCTS);

        int actualNoOfProducts = shoppingCart.getNumberOfProducts();
        Assertions.assertEquals(EXPECTED_NO_OF_PRODUCTS,actualNoOfProducts);

        Product actualProduct = shoppingCart.getProduct();
        Product expectedProduct = new Product();
        expectedProduct.setName(SOAP_NAME);
        expectedProduct.setPrice(NIVEA_SOAP_UNIT_PRICE);
        Assertions.assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void testCalculateTotalPrice()
    {
        int TWO_DIGIT_PRECISION = 2, No_OF_PRODUCTS= 5;
        final String SOAP_NAME="Dove";
        final BigDecimal DOVE_SOAP_UNIT_PRICE=BigDecimal.valueOf(39.99);

        BigDecimal EXPECTED_TOTALPRICE = DOVE_SOAP_UNIT_PRICE.multiply(BigDecimal.valueOf(No_OF_PRODUCTS)).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);

        Product soapProduct = new Product();
        soapProduct.setName(SOAP_NAME);
        soapProduct.setPrice(DOVE_SOAP_UNIT_PRICE);
        shoppingCart.addProducts(soapProduct, No_OF_PRODUCTS);

        BigDecimal actualTotalPrice= shoppingCart.cartTotalPriceWithoutTax();
        assertEquals(EXPECTED_TOTALPRICE, actualTotalPrice);
    }

    @Test
    public void testGetUpdatedNumberOfProducts(){
        final int TWO_DIGIT_PRECISION = 2;
        final int EXPECTED_NO_OF_PRODUCTS = 8;
        final int INITIAL_NO_OF_PRODUCTS=5;

        final String SOAP_NAME="Nivea";
        final BigDecimal NIVEA_SOAP_UNIT_PRICE=BigDecimal.valueOf(20.99);

        Product soapProduct = new Product();
        soapProduct.setName(SOAP_NAME);
        soapProduct.setPrice(NIVEA_SOAP_UNIT_PRICE);
        shoppingCart.addProducts(soapProduct, INITIAL_NO_OF_PRODUCTS);

        final int NO_OF_PRODUCTS = 3;
        shoppingCart.addProducts(soapProduct, NO_OF_PRODUCTS);

        int actualNoOfProducts=shoppingCart.getNumberOfProducts();
        assertEquals(EXPECTED_NO_OF_PRODUCTS,actualNoOfProducts);

        BigDecimal EXPECTED_TOTALPRICE = NIVEA_SOAP_UNIT_PRICE.multiply(BigDecimal.valueOf(EXPECTED_NO_OF_PRODUCTS)).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);

        BigDecimal actualTotalPrice= shoppingCart.cartTotalPriceWithoutTax();
        assertEquals(EXPECTED_TOTALPRICE, actualTotalPrice);
    }

    @Test
    public void testAddMultipleProducts(){
        final int EXPECTED_NO_OF_DOVE_SOAP = 2;
        final int NO_OF_DOVE_SOAP=2;
        final String SOAP_NAME="Dove";
        final BigDecimal DOVE_SOAP_UNIT_PRICE=BigDecimal.valueOf(39.99);

        Product soapProduct=new Product();
        soapProduct.setName(SOAP_NAME);
        soapProduct.setPrice(DOVE_SOAP_UNIT_PRICE);
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        int actualNoOfDoveSoap= shoppingCart.getProductQuantity(soapProduct);

        final int EXPECTED_NO_OF_AXE_DEO = 2;
        final int NO_OF_AXE_DEO=2;
        final String DEO_NAME="Axe";
        final BigDecimal AXE_DEO_UNIT_PRICE=BigDecimal.valueOf(99.99);

        Product deoProduct=new Product();
        deoProduct.setName(DEO_NAME);
        deoProduct.setPrice(AXE_DEO_UNIT_PRICE);
        shoppingCart.addProducts(deoProduct,NO_OF_AXE_DEO);

        int actualNoOfAxeDeo= shoppingCart.getProductQuantity(deoProduct);

        final int EXPECTED_NO_OF_TOTAL_PRODUCT=4;
        int actualNoOfTotalProduct=shoppingCart.getNumberOfProducts();

        boolean testCaseSucceed= ((EXPECTED_NO_OF_TOTAL_PRODUCT==actualNoOfTotalProduct)&&(EXPECTED_NO_OF_AXE_DEO==actualNoOfAxeDeo)&&(EXPECTED_NO_OF_DOVE_SOAP==actualNoOfDoveSoap));
        assertTrue(testCaseSucceed);

    }

    @Test
    public void testCalculateTotalPriceOfMultipleProductWithoutTax(){
        int TWO_DIGIT_PRECISION = 2;

        final int NO_OF_DOVE_SOAP=2;
        final String SOAP_NAME="Dove";
        final BigDecimal DOVE_SOAP_UNIT_PRICE = BigDecimal.valueOf(39.99);

        BigDecimal EXPECTED_TOTAL_PRICE_WITHOUT_TAX = BigDecimal.valueOf(279.96);

        Product soapProduct = new Product();
        soapProduct.setName(SOAP_NAME);
        soapProduct.setPrice(DOVE_SOAP_UNIT_PRICE);
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        final int NO_OF_AXE_DEO=2;
        final String DEO_NAME="AXE";
        final BigDecimal AXE_DEO_UNIT_PRICE = BigDecimal.valueOf(99.99);

        Product deoProduct = new Product();
        deoProduct.setName(DEO_NAME);
        deoProduct.setPrice(AXE_DEO_UNIT_PRICE);
        shoppingCart.addProducts(deoProduct,NO_OF_AXE_DEO);

        BigDecimal actualTotalPriceWithoutTax=shoppingCart.cartTotalPriceWithoutTax();
        assertEquals(EXPECTED_TOTAL_PRICE_WITHOUT_TAX,actualTotalPriceWithoutTax);

    }

    @Test
    public void testCalculateSalesTax(){
        int TWO_DIGIT_PRECISION = 2;
        BigDecimal salesTaxRate=BigDecimal.valueOf(12.5);
        shoppingCart.shoppingCartPriceCalculator.setSalesTaxMultiplier(salesTaxRate);

        final int NO_OF_DOVE_SOAP=2;
        final String SOAP_NAME="Dove";
        final BigDecimal DOVE_SOAP_UNIT_PRICE=BigDecimal.valueOf(39.99);

        Product soapProduct=new Product();
        soapProduct.setName(SOAP_NAME);
        soapProduct.setPrice(DOVE_SOAP_UNIT_PRICE);
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        BigDecimal EXPECTED_TOTAL_DOVE_SOAP_SALES_TAX=BigDecimal.valueOf(NO_OF_DOVE_SOAP).multiply(DOVE_SOAP_UNIT_PRICE).
                multiply(shoppingCart.shoppingCartPriceCalculator.getSalesTaxMultiplier());

        final int NO_OF_AXE_DEO=2;
        final String DEO_NAME="Axe";
        final BigDecimal AXE_DEO_UNIT_PRICE=BigDecimal.valueOf(99.99);


        Product deoProduct=new Product();
        deoProduct.setName(DEO_NAME);
        deoProduct.setPrice(AXE_DEO_UNIT_PRICE);
        shoppingCart.addProducts(deoProduct,NO_OF_AXE_DEO);
        BigDecimal EXPECTED_TOTAL_AXE_DEO_SALES_TAX=BigDecimal.valueOf(NO_OF_AXE_DEO).multiply(AXE_DEO_UNIT_PRICE).
                multiply(shoppingCart.shoppingCartPriceCalculator.getSalesTaxMultiplier());


        BigDecimal EXPECTED_TOTAL_SALES_TAX=EXPECTED_TOTAL_DOVE_SOAP_SALES_TAX.add(EXPECTED_TOTAL_AXE_DEO_SALES_TAX).
                setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);

        BigDecimal actualTotalSalesTax=shoppingCart.cartTotalSalesTax();
        assertEquals(EXPECTED_TOTAL_SALES_TAX,actualTotalSalesTax);

    }

    @Test
    public void testCalculateTotalPriceOfCartIncludingTaxes() {
        final BigDecimal EXPECTED_TOTAL_PRICE_OF_CART_INCLUDING_TAXES = BigDecimal.valueOf(314.96);
        final int NO_OF_DOVE_SOAP=2;
        final String SOAP_NAME="Dove";
        final BigDecimal DOVE_SOAP_UNIT_PRICE=BigDecimal.valueOf(39.99);

        Product soapProduct=new Product();
        soapProduct.setName(SOAP_NAME);
        soapProduct.setPrice(DOVE_SOAP_UNIT_PRICE);
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        final int NO_OF_AXE_DEO=2;
        final String DEO_NAME="Axe";
        final BigDecimal AXE_DEO_UNIT_PRICE=BigDecimal.valueOf(99.99);

        Product deoProduct = new Product();
        deoProduct.setName(DEO_NAME);
        deoProduct.setPrice(AXE_DEO_UNIT_PRICE);
        shoppingCart.addProducts(deoProduct,NO_OF_AXE_DEO);

        BigDecimal actualTotalPriceOfCartIncludingTaxes = shoppingCart.cartTotalPriceIncludingTax();
        assertEquals(EXPECTED_TOTAL_PRICE_OF_CART_INCLUDING_TAXES,actualTotalPriceOfCartIncludingTaxes);
    }

}
