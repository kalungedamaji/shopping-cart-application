package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final ShoppingCartPriceCalculator shoppingCartPriceCalculator = new ShoppingCartPriceCalculator();
    private List<ShoppingCartItem> cart = new ArrayList<>();

    public void addProducts(final Product product, final int numberOfProducts) {
        addProductsMechanism(product,numberOfProducts);
    }

    private void addProductsMechanism(final Product product, final int numberOfProducts){
        boolean flag = true;
        for (ShoppingCartItem shoppingCartItem : cart){
            if (shoppingCartItem.getName()==product.getName()){
                shoppingCartItem.setQuantity(shoppingCartItem.getQuantity()+numberOfProducts);
                flag = false;
            }
        }
        if (flag){
            ShoppingCartItem shoppingCartItem1 = new ShoppingCartItem();
            shoppingCartItem1.setName(product.getName());
            shoppingCartItem1.setPrice(product.getPrice());
            shoppingCartItem1.setQuantity(numberOfProducts);
            cart.add(shoppingCartItem1);
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

    private List<ShoppingCartItem> getCart() {
        return cart;
    }
    public List<Product> getAllItemsOfCart(){
        List<Product> items= new ArrayList<>();
        for (ShoppingCartItem shoppingCartItem : cart){
            Product product = new Product(shoppingCartItem.getName(), shoppingCartItem.getPrice());
            items.add(product);
        }
        return items;
    }

    private ShoppingCartPriceCalculator getShoppingCartPriceCalculator() {
        return shoppingCartPriceCalculator;
    }

    public void setSalesTaxMultiplier(BigDecimal salesTaxMultiplier) {
        getShoppingCartPriceCalculator().setSalesTaxMultiplier(salesTaxMultiplier);
    }

    public Boolean itemIsgiftable(Product product) {
        if (getAllItemsOfCart().contains(product))
            return true;
        return false;
    }
}
