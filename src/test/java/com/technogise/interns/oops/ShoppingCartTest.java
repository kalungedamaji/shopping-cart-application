package com.technogise.interns.oops;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.MatcherAssert.assertThat;
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
    public void testUserHasAbilityToGiftTheProduct(){
        //given
        final int NO_OF_DOVE_SOAP=1;
        final boolean itemGiftable=true;
        final boolean EXPECTED_USER_CHOICE_FOR_GIFT=true;
        Product soapProduct=new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);
        soapProduct.setGift(itemGiftable);

        //When
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);
        shoppingCart.userOptedItemForGift(soapProduct);

        //then
        //assertEquals(EXPECTED_USER_CHOICE_FOR_GIFT,shoppingCart.getCart());
        assertThat(shoppingCart.getCartItems(),contains(hasProperty("userChoiceForGift",
                is(EXPECTED_USER_CHOICE_FOR_GIFT))));

    }

    @Test
    public void testUserHasAbilityToGiftTheMultipleProduct(){
        //given
        final int NO_OF_DOVE_SOAP=1;
        final boolean doveProductGiftable=true;
        final boolean EXPECTED_USER_CHOICE_FOR_DOVE_PRODUCT_AS_GIFT=true;

        final int NO_OF_AXE_DEO=1;
        final boolean deoProductGiftable=true;
        final boolean EXPECTED_USER_CHOICE_FOR_DEO_PRODUCT_AS_GIFT=true;

        Product soapProduct=new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);
        soapProduct.setGift(doveProductGiftable);

        Product deoProduct=new Product(DEO_NAME,AXE_DEO_UNIT_PRICE);
        deoProduct.setGift(deoProductGiftable);

        //When
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);
        shoppingCart.userOptedItemForGift(soapProduct);

        shoppingCart.addProducts(deoProduct,NO_OF_AXE_DEO);
        shoppingCart.userOptedItemForGift(deoProduct);

        //then
        assertThat(shoppingCart.getCartItems(),hasItems(allOf
                (hasProperty("userChoiceForGift", is(EXPECTED_USER_CHOICE_FOR_DOVE_PRODUCT_AS_GIFT))
                        ,hasProperty("name",is(SOAP_NAME))
                )
        ,allOf(
                        hasProperty("userChoiceForGift",is(EXPECTED_USER_CHOICE_FOR_DEO_PRODUCT_AS_GIFT))
                        ,hasProperty("name",is(DEO_NAME))
                )
        )
        );

    }


    @Test
    public void testGetAllProductsOfShoppingCart(){
        final int NO_OF_DOVE_SOAP = 2;

        Product soapProduct = new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);
        shoppingCart.addProducts(soapProduct,NO_OF_DOVE_SOAP);

        List<ShoppingCartItem> actualItems = shoppingCart.getCartItems();

        assertEquals(SOAP_NAME, actualItems.get(0).getName());
        assertEquals(DOVE_SOAP_UNIT_PRICE, actualItems.get(0).getPrice());
    }
}

