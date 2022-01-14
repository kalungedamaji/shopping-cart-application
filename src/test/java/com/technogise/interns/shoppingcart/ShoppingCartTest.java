package com.technogise.interns.shoppingcart;


import com.technogise.interns.oops.ShoppingCart;
import org.junit.jupiter.api.Test;

public class ShoppingCartTest {

    ShoppingCart cart = new ShoppingCart();

    @Test
    public void testSetNumberOfProduct() {
        int EXPECTED_PRODUCT_COUNT = 5;
        cart.setNumberOfProducts(EXPECTED_PRODUCT_COUNT);
    }


}
