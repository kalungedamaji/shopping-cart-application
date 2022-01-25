package com.technogise.interns.oops;

import java.math.BigDecimal;

public class ShoppingCartItem {

    private int quantity = 0;
    private Boolean userChoice;
    private String name;
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Boolean getUserChoice() {
        return userChoice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUserChoice(Boolean userChoice) {
        this.userChoice = userChoice;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
}
