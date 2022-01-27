package com.technogise.interns.oops;

import java.math.BigDecimal;

public class Product {
  private final String name;
  private final BigDecimal price;
  private Boolean isGift;

  Product(final String name, final BigDecimal price) {
    this.name = name;
    this.price = price;
  }

  public Boolean getGift() {
    return isGift;
  }

  public void setGift(Boolean gift) {

    isGift = gift;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }
}
