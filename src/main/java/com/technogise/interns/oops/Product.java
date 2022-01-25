package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final String name;
    private final BigDecimal price;
    private Boolean isGift;

    Product(final String name,final BigDecimal price){
        this.name=name;
        this.price=price;
    }

    public void setGift(Boolean gift) {
        isGift = gift;
    }

    public Boolean getGift() {
        return isGift;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
