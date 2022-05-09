package com.technogise.interns.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class OrdersOrderItem {
    @JsonProperty(access = JsonProperty.Access. READ_ONLY)
    private UUID id;
    private String name;
    private String image;
    private String description;
    private int quantity;
    private BigDecimal price;
}
