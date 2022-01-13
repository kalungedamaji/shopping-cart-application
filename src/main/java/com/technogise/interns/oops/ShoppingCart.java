package com.technogise.interns.oops;

import java.math.BigDecimal;

public class ShoppingCart {
    int numberOfProducts = 0;
    Product product;

    public void addProducts(Product product, int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
        this.product = product;
    }

    public BigDecimal calculateTotalPrice() {
        return (product.price.multiply(BigDecimal.valueOf(numberOfProducts)).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
    }
}
