package com.technogise.interns.oops;

import java.math.BigDecimal;

public class ShoppingCart {
    private int numberOfProducts = 0;
    private Product product;
    private static final int TWO_DIGIT_PRECISION =2;

    public void addProducts(final Product product, final int numberOfProducts) {
        setProduct(product);
        int productCount= getNumberOfProducts()+numberOfProducts;
        setNumberOfProducts(productCount);
    }

    public BigDecimal calculateTotalPrice() {
        return getProduct().getPrice().multiply(BigDecimal.valueOf(getNumberOfProducts())).setScale(TWO_DIGIT_PRECISION, BigDecimal.ROUND_HALF_UP) ;
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
}
