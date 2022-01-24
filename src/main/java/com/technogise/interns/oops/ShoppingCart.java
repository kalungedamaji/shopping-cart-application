package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.HashMap;


public class ShoppingCart {
    private ShoppingCartPriceCalculator shoppingCartPriceCalculator = new ShoppingCartPriceCalculator();
    private HashMap<Product,Integer> cart = new HashMap<>();

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

    public int getNumberOfProducts() {
        int productCount = 0;
        for (Integer productQuantity:cart.values()){
            productCount += productQuantity;
        }
            return productCount;
    }

    public Product getProduct(String productName) {
        for (Product product : cart.keySet()){
            if (product.getName() == productName)
                return product;
        }
        return null;
    }

    public int getProductQuantity(final Product product){
        return getCart().get(product);
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
