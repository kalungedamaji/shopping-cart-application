package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.*;

public class ShoppingCart {
    final private ShoppingCartPriceCalculator shoppingCartPriceCalculator = new ShoppingCartPriceCalculator();
    final private Set<CartItem> cartItemsList = new HashSet<>();

    public void addProducts(final Product product, final int numberOfProducts) {
    //    addProductsMechanism(product,numberOfProducts);
        CartItem cartItem = new CartItem(product.getName(), product.getPrice(),numberOfProducts, true);
        cartItemsList.add(cartItem);
    }

    /*  private void addProductsMechanism(final Product product, final int numberOfProducts){
        if(getCart().containsKey(product)) {
            int productQuantity= getCart().get(product) + numberOfProducts;
            getCart().put(product,productQuantity);
        } else {
            getCart().put(product,numberOfProducts);
        }
    }
*/

    public BigDecimal calculateTotalPriceWithoutTaxes(){
        return getShoppingCartPriceCalculator().calculateTotalPriceWithoutTaxes(getCart());
    }

    public BigDecimal calculateTotalSalesTax(){
        return getShoppingCartPriceCalculator().calculateTotalSalesTax(getCart());
    }

    public BigDecimal calculateTotalPriceOfCartIncludingTaxes(){
        return getShoppingCartPriceCalculator().calculateTotalPriceOfCartIncludingTaxes(getCart());
    }

    public Set<CartItem> getCart() {  //changed from private to public
        return cartItemsList;
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
}
