package com.technogise.interns.oops;

import org.junit.jupiter.api.Test;

import javax.jnlp.BasicService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartTest {

     ShoppingCart shoppingCart = new ShoppingCart();

    private final String SOAP_NAME="Dove";
    private final BigDecimal DOVE_SOAP_UNIT_PRICE=BigDecimal.valueOf(39.99);

    private final String DEO_NAME="Axe";
    private final BigDecimal AXE_DEO_UNIT_PRICE=BigDecimal.valueOf(99.99);

    @Test
    public void testCalculateTotalPriceOfCartIncludingTaxesWhenShoppingCartIsEmptyReturnsZero(){
        final int TWO_DIGIT_PRECISION = 2;

        final BigDecimal EXPECTED_PRICE_OF_CART = BigDecimal.valueOf(0).setScale(TWO_DIGIT_PRECISION, RoundingMode.HALF_UP);

        BigDecimal actualTotalPrice = shoppingCart.calculateTotalPriceOfCartIncludingTaxes();

        assertEquals(EXPECTED_PRICE_OF_CART,actualTotalPrice);
    }

    @Test
    public void testCalculateTotalPriceOfCartIncludingTaxesWhenMultipleCategoryOfProductsAreAdded() {
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
    public void testCalculateTotalPriceOfCartWhenMultipleCategoryOfProductsAreAddedWithoutTax(){

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
    public void testSalesTaxWhenDifferentProductsAreAddedToShoppingCart(){
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
    @Test
    public void testProductIsGiftable(){
        final int NO_OF_DOVE_SOAP = 1;
        Product soapProduct = new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);
        soapProduct.setGift(true);
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);
        Boolean USER_CHOICE = true;

        assertEquals(USER_CHOICE, shoppingCart.itemIsgiftable(soapProduct));
    }
    @Test
    public void testGetAllProductsOfShoppingCart(){
        final int NO_OF_DOVE_SOAP = 2;

        Product soapProduct = new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        List<Product> actualItems = shoppingCart.getAllItemsOfCart();

        Product actualProduct = actualItems.get(0);
        assertEquals(SOAP_NAME, actualProduct.getName());
        assertEquals(DOVE_SOAP_UNIT_PRICE, actualProduct.getPrice());
    }
}

