package com.technogise.interns.shoppingcart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Customer {
    private UUID id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String address;
    private String email;
    private String password;
}
