package com.technogise.interns.shoppingcart.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ApiModel(description = "Details about the customer")
public class CartItem {
    private UUID id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String image;

}
