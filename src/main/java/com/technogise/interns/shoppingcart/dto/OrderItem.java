package com.technogise.interns.shoppingcart.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Setter
@Getter
public class OrderItem {
    private UUID id;
    private String name;
    private String image;
    private String description;
}
