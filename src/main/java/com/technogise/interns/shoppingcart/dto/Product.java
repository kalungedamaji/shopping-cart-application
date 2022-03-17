package com.technogise.interns.shoppingcart.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Product {

    private UUID id;
    private String name;
    private BigDecimal price;
    private String image;
    private String description;
}
