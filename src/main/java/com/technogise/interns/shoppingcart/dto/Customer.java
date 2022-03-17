package com.technogise.interns.shoppingcart.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class Customer {
    private UUID id;
    private String name;
    private String phone_number;
    private String address;
    private String email;
    private String password;
}
