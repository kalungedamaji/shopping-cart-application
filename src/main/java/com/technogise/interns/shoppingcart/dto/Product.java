package com.technogise.interns.shoppingcart.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ApiModel(description = "Details about the product")
public class Product {
    @ApiModelProperty(notes = "The unique id of the product is in UUID format and will be auto-generated.")
    private UUID id;
    @ApiModelProperty(notes = "The name of the product")
    private String name;
    @ApiModelProperty(notes = "The price of the product")
    private BigDecimal price;
    @ApiModelProperty(notes = "The image of the product")
    private String image;
    @ApiModelProperty(notes = "The description of the product")
    private String description;
}


