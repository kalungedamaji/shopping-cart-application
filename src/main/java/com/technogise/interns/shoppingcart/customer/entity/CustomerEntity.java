package com.technogise.interns.shoppingcart.customer.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity(name="Customer")
public class CustomerEntity {
    @Id
    private UUID id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="phone_number")
    private String phoneNumber;
    private String address;
    @Column(name="email_id")
    private String emailId;
    private String password;
}
