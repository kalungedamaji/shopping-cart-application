package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    final private  String name;
    final private BigDecimal price;


    Product(final String name,final BigDecimal price){
        this.name=name;
        this.price=price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
