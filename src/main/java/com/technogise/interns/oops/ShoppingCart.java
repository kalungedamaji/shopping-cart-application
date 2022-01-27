package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.*;

public class ShoppingCart {
    final private ShoppingCartPriceCalculator shoppingCartPriceCalculator = new ShoppingCartPriceCalculator();
    final private Set<CartItem> cartItemsList = new HashSet<>();

    public void addProducts(final CartItem cartItem, final int numberOfProducts) {
        cartItem.setNoOfProducts(numberOfProducts);
        cartItemsList.add(cartItem);
    }

    public BigDecimal calculateTotalPriceWithoutTaxes(){
        return getShoppingCartPriceCalculator().calculateTotalPriceWithoutTaxes(getCartItems());
    }

    public BigDecimal calculateTotalSalesTax(){
        return getShoppingCartPriceCalculator().calculateTotalSalesTax(getCartItems());
    }

    public BigDecimal calculateTotalPriceOfCartIncludingTaxes(){
        return getShoppingCartPriceCalculator().calculateTotalPriceOfCartIncludingTaxes(getCartItems());
    }

    private ShoppingCartPriceCalculator getShoppingCartPriceCalculator() {
        return shoppingCartPriceCalculator;
    }

    public void setSalesTaxMultiplier(BigDecimal salesTaxMultiplier) {
        getShoppingCartPriceCalculator().setSalesTaxMultiplier(salesTaxMultiplier);
    }

    public Set<CartItem> getCartItems() {
        return cartItemsList;
    }

    public void optProductAsGift(CartItem cartItem) {
        if(cartItem.getGiftAbility() == true) {
            cartItem.optCartItemAsGift();
        }
        else {
            throw new RuntimeException("Product is not giftable !!");
        }

    }
}
