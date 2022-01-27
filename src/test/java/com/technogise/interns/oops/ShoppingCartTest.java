package com.technogise.interns.oops;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartTest {

    final private ShoppingCart shoppingCart = new ShoppingCart();

    final private String SOAP_NAME="Dove";
    final private BigDecimal DOVE_SOAP_UNIT_PRICE=BigDecimal.valueOf(39.99);

    final private String DEO_NAME="Axe";
    final private BigDecimal AXE_DEO_UNIT_PRICE=BigDecimal.valueOf(99.99);

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
           final int NO_OF_AXE_DEO=2;

           Product soapProduct=new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);
           ProductToCartItemConvertor productToCartItemConvertor = new ProductToCartItemConvertor();
           CartItem cartItemDove = productToCartItemConvertor.convertProductToCartItem(soapProduct);
           shoppingCart.addProducts(cartItemDove,NO_OF_DOVE_SOAP);
           Product deoProduct = new Product(DEO_NAME,AXE_DEO_UNIT_PRICE);
           CartItem cartItemAxe = productToCartItemConvertor.convertProductToCartItem(deoProduct);
           shoppingCart.addProducts(cartItemAxe,NO_OF_AXE_DEO);

           BigDecimal actualTotalPriceOfCartIncludingTaxes = shoppingCart.calculateTotalPriceOfCartIncludingTaxes();
           assertEquals(EXPECTED_TOTAL_PRICE_OF_CART_INCLUDING_TAXES,actualTotalPriceOfCartIncludingTaxes);
       }

         @Test
         public void testCalculateTotalPriceOfCartWhenMultipleCategoryOfProductsAreAddedWithoutTax(){

             final int NO_OF_DOVE_SOAP=2;
             final int NO_OF_AXE_DEO=2;
             BigDecimal EXPECTED_TOTAL_PRICE_WITHOUT_TAX = BigDecimal.valueOf(279.96);

             Product soapProduct = new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);
             ProductToCartItemConvertor productToCartItemConvertor = new ProductToCartItemConvertor();
             CartItem cartItemDove = productToCartItemConvertor.convertProductToCartItem(soapProduct);
             shoppingCart.addProducts(cartItemDove,NO_OF_DOVE_SOAP);
             Product deoProduct = new Product(DEO_NAME,AXE_DEO_UNIT_PRICE);
             CartItem cartItemAxe = productToCartItemConvertor.convertProductToCartItem(deoProduct);
             shoppingCart.addProducts(cartItemAxe,NO_OF_AXE_DEO);

             BigDecimal actualTotalPriceWithoutTax= shoppingCart.calculateTotalPriceWithoutTaxes();
             assertEquals(EXPECTED_TOTAL_PRICE_WITHOUT_TAX,actualTotalPriceWithoutTax);

         }

         @Test
         public void testSalesTaxWhenDifferentProductsAreAddedToShoppingCart(){
             int TWO_DIGIT_PRECISION = 2;
             BigDecimal salesTaxRate=BigDecimal.valueOf(12.5);
             shoppingCart.setSalesTaxMultiplier(salesTaxRate);
             final int NO_OF_DOVE_SOAP=2;
             final int NO_OF_AXE_DEO=2;
             BigDecimal TOTAL_DOVE_SOAP_SALES_TAX=BigDecimal.valueOf(NO_OF_DOVE_SOAP).multiply(DOVE_SOAP_UNIT_PRICE).
                     multiply(salesTaxRate.divide(BigDecimal.valueOf(100)));
             BigDecimal TOTAL_AXE_DEO_SALES_TAX=BigDecimal.valueOf(NO_OF_AXE_DEO).multiply(AXE_DEO_UNIT_PRICE).
                     multiply(salesTaxRate.divide(BigDecimal.valueOf(100)));
             BigDecimal EXPECTED_TOTAL_SALES_TAX=TOTAL_DOVE_SOAP_SALES_TAX.add(TOTAL_AXE_DEO_SALES_TAX).
                     setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP);

             Product soapProduct=new Product(SOAP_NAME,DOVE_SOAP_UNIT_PRICE);
             ProductToCartItemConvertor productToCartItemConvertor = new ProductToCartItemConvertor();
             CartItem cartItemDove = productToCartItemConvertor.convertProductToCartItem(soapProduct);
             shoppingCart.addProducts(cartItemDove,NO_OF_DOVE_SOAP);
             Product deoProduct=new Product(DEO_NAME,AXE_DEO_UNIT_PRICE);
             CartItem cartItemAxe = productToCartItemConvertor.convertProductToCartItem(deoProduct);
             shoppingCart.addProducts(cartItemAxe,NO_OF_AXE_DEO);

             BigDecimal actualTotalSalesTax=shoppingCart.calculateTotalSalesTax();
             assertEquals(EXPECTED_TOTAL_SALES_TAX,actualTotalSalesTax);
         }
    /*
         @Test
         public void testGiftAbilityOfProduct() {
             boolean EXPECTED_GIFT_ABILITY_OF_PRODUCT = true;
             boolean giftable = true; //private not working
             Product doveSoap = new Product(SOAP_NAME, DOVE_SOAP_UNIT_PRICE, giftable);

             boolean actualGiftAbilityOfProduct = doveSoap.getGiftAbility();

             assertEquals(EXPECTED_GIFT_ABILITY_OF_PRODUCT, actualGiftAbilityOfProduct);
         }
     */
    @Test
    public void testUserOptedProductAsGift() {
        boolean EXPECTED_GIFT_OPTIONALITY = true;                                                 //Review later
        boolean giftable = true;
        final int NO_OF_DOVE_SOAP=1;

        Product doveSoap = new Product(SOAP_NAME, DOVE_SOAP_UNIT_PRICE, giftable);
        ProductToCartItemConvertor productToCartItemConvertor = new ProductToCartItemConvertor();
        CartItem cartItem = productToCartItemConvertor.convertProductToCartItem(doveSoap);
        shoppingCart.addProducts(cartItem, NO_OF_DOVE_SOAP);
        shoppingCart.optProductAsGift(cartItem);

        Set<CartItem> cartItemList = shoppingCart.getCartItems();
        boolean actualGiftOptionality = cartItemList.iterator().next().getIfGiftOpted();
        assertEquals(EXPECTED_GIFT_OPTIONALITY, actualGiftOptionality);
    }

    @Test
    public void testGetAllTheProductFromShoppingCart() {
        String EXPECTED_SOAP_NAME = "Dove";
        BigDecimal EXPECTED_DOVE_SOAP_UNIT_PRICE = BigDecimal.valueOf(39.99);
        final int NO_OF_DOVE_SOAP=1;

        Product doveSoap = new Product(SOAP_NAME, DOVE_SOAP_UNIT_PRICE);
        ProductToCartItemConvertor productToCartItemConvertor = new ProductToCartItemConvertor();
        CartItem cartItem = productToCartItemConvertor.convertProductToCartItem(doveSoap);
        shoppingCart.addProducts(cartItem, NO_OF_DOVE_SOAP);
        Set<CartItem> cartItemList = shoppingCart.getCartItems();
        String actualSoapName = cartItemList.iterator().next().getName();
        BigDecimal actualSoapUnitPrice = cartItemList.iterator().next().getPrice();

        assertEquals(EXPECTED_SOAP_NAME, actualSoapName);
        assertEquals(EXPECTED_DOVE_SOAP_UNIT_PRICE, actualSoapUnitPrice);
    }
}

