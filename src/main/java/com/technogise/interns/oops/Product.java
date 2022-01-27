package com.technogise.interns.oops;

import java.math.BigDecimal;

public class Product {
    private final  String name;
    private final BigDecimal price;
    private boolean giftAbility;



    Product(final String name,final BigDecimal price){
        this.name = name;
        this.price = price;
    }

    Product(final String name,final BigDecimal price, final boolean giftAbility){
        this.name = name;
        this.price = price;
        this.giftAbility = giftAbility;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean getGiftAbility() {
        return giftAbility;
    }

    public String getName() {
        return name;
    }
}
