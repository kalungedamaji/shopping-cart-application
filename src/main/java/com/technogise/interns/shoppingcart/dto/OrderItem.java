package com.technogise.interns.shoppingcart.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@ApiModel(description = "Details about the customer")
public class OrderItem {
    private UUID id;
    private String name;
    private int quantity;
    private BigDecimal price;
    private String image;
}
