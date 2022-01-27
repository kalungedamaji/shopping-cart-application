package com.technogise.interns.oops;

import java.math.BigDecimal;
import java.util.Objects;

public class ShoppingCartItem {

  private int quantity;
  private Boolean userChoiceForGift = false;
  private String name;
  private BigDecimal price;

  ShoppingCartItem(final int quantity, final String name, final BigDecimal price) {
    this.quantity = quantity;
    this.name = name;
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ShoppingCartItem that = (ShoppingCartItem) o;
    return quantity == that.quantity
        && Objects.equals(userChoiceForGift, that.userChoiceForGift)
        && Objects.equals(name, that.name)
        && Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(quantity, userChoiceForGift, name, price);
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Boolean getUserChoiceForGift() {
    return userChoiceForGift;
  }

  public void setUserChoiceForGift(Boolean userChoiceForGift) {
    this.userChoiceForGift = userChoiceForGift;
  }
}
