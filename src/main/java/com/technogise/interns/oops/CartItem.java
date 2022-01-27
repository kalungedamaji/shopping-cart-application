package com.technogise.interns.oops;

import java.math.BigDecimal;

public class CartItem {
    private int quantity;
    private boolean giftOpted;
    private BigDecimal price;
    private String name;


    CartItem(String name, BigDecimal price) {
        this.price = price;
        this.name = name;
    }


    public int getQuantity() {
        return quantity;
    }

    public boolean getIfGiftOpted() {
        return giftOpted;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void optCartItemAsGift() {
        this.giftOpted = true;
    }

    public void setNoOfProducts(int numberOfProducts) {
        this.quantity = numberOfProducts;
    }
}
