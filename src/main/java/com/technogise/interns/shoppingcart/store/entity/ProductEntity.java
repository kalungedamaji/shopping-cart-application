package com.technogise.interns.shoppingcart.store.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity(name="Product")
public class ProductEntity {
    @Id
    private UUID id;
    private String image;
    private String name;
    private String  description;
    private BigDecimal price;

}
