package com.technogise.interns.oops;

import java.math.BigDecimal;

public class ShoppingCartItem {

    private int quantity = 0;
    private Boolean userChoiceForGift=false;
    private String name;
    private BigDecimal price;

    ShoppingCartItem(final int quantity,final String name, final BigDecimal price){
        this.quantity=quantity;
        this.name=name;
        this.price=price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Boolean getUserChoiceForGift() {
        return userChoiceForGift;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUserChoiceForGift(Boolean userChoiceForGift) {
        this.userChoiceForGift = userChoiceForGift;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
}
