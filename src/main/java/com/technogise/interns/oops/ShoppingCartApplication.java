package com.technogise.interns.oops;

import java.math.BigDecimal;

public class ShoppingCartApplication {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        Product soap = new Product("Dove",BigDecimal.valueOf(39.99));
        int numberOfProducts = 5;
        cart.addProducts(soap, numberOfProducts);
        System.out.println("Total price of the cart = $" + cart.calculateTotalPriceOfCartIncludingTaxes());
    }
}
