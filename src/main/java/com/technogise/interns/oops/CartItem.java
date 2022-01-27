package com.technogise.interns.oops;

import java.math.BigDecimal;

public class CartItem {
    private int quantity;
    private boolean giftOpted, giftAbility;
    private BigDecimal price;
    private String name;


    CartItem(String name, BigDecimal price, boolean giftAbility) {
        this.price = price;
        this.name = name;
        this.giftAbility = giftAbility;
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
        this.quantity += numberOfProducts;
    }

    public boolean getGiftAbility() {
        return giftAbility;
    }
}
