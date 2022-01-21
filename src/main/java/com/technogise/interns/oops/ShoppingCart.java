package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private int numberOfProducts = 0;
    private Product product;
    ShoppingCartPriceCalculator shoppingCartPriceCalculator=new ShoppingCartPriceCalculator();
    private static final int TWO_DIGIT_PRECISION =2;
    private HashMap<Product, Integer> cart = new HashMap<>();


    public void increaseProductQuantity(final Product product, final int numberOfProducts){
        if(getCart().containsKey(product)) {
            int productQuantity= getCart().get(product) + numberOfProducts;
            getCart().put(product,productQuantity);
        } else {
            getCart().put(product,numberOfProducts);
        }
    }

    public void addProducts(final Product product, final int numberOfProducts) {
        setProduct(product);
        int productCount=getNumberOfProducts()+numberOfProducts;
        increaseProductQuantity(product,numberOfProducts);
        setNumberOfProducts(productCount);
    }

    public BigDecimal cartTotalPriceWithoutTax(){
        return shoppingCartPriceCalculator.calculateTotalPriceWithoutTax(cart);
    }

    public BigDecimal cartTotalSalesTax(){
        return shoppingCartPriceCalculator.calculateTotalSalesTax(cart);
    }

    public BigDecimal cartTotalPriceIncludingTax(){
        return shoppingCartPriceCalculator.calculateTotalPriceOfCartIncludingTaxes(cart);
    }

    public int getProductQuantity(final Product product){
        return getCart().get(product);
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public Product getProduct() {
        return product;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCart(HashMap<Product, Integer> cart) {
        this.cart = cart;
    }

    public HashMap<Product, Integer> getCart() {
        return cart;
    }


}
