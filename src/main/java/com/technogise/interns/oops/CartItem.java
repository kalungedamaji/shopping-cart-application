package com.technogise.interns.oops;

import java.math.BigDecimal;

public class CartItem {
    private int quantity;
    private boolean giftOpted;
    private BigDecimal price;
    private String name;


    CartItem(String name, BigDecimal price, int quantity, boolean giftOpted) {
        this.quantity = quantity;
        this.giftOpted = giftOpted;
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
}
