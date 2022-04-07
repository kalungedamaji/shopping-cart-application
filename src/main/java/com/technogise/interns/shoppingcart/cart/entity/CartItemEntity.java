package com.technogise.interns.shoppingcart.cart.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity(name="CartItem")
public class CartItemEntity {

    @Id
    private UUID id;
    private String name;
    private BigDecimal price;
    private int  quantity;
    private String image;




}
