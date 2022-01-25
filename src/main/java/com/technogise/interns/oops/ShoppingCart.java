package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.HashMap;

public class ShoppingCart {
    final private ShoppingCartPriceCalculator shoppingCartPriceCalculator = new ShoppingCartPriceCalculator();
    final private HashMap<Product,Integer> cart = new HashMap<>();

    public void addProducts(final Product product, final int numberOfProducts) {
        addProductsMechanism(product,numberOfProducts);
    }

    private void addProductsMechanism(final Product product, final int numberOfProducts){
        if(getCart().containsKey(product)) {
            int productQuantity= getCart().get(product) + numberOfProducts;
            getCart().put(product,productQuantity);
        } else {
            getCart().put(product,numberOfProducts);
        }
    }

    public BigDecimal calculateTotalPriceWithoutTaxes(){
        return getShoppingCartPriceCalculator().calculateTotalPriceWithoutTaxes(getCart());
    }

    public BigDecimal calculateTotalSalesTax(){
        return getShoppingCartPriceCalculator().calculateTotalSalesTax(getCart());
    }

    public BigDecimal calculateTotalPriceOfCartIncludingTaxes(){
        return getShoppingCartPriceCalculator().calculateTotalPriceOfCartIncludingTaxes(getCart());
    }

    private HashMap<Product, Integer> getCart() {
        return cart;
    }

    private ShoppingCartPriceCalculator getShoppingCartPriceCalculator() {
        return shoppingCartPriceCalculator;
    }

    public void setSalesTaxMultiplier(BigDecimal salesTaxMultiplier) {
        getShoppingCartPriceCalculator().setSalesTaxMultiplier(salesTaxMultiplier);
    }
}
