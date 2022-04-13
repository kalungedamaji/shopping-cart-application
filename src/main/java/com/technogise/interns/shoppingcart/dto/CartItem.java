
package com.technogise.interns.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ApiModel(description = "Details about cartItem")
@EqualsAndHashCode
public class CartItem {
    @JsonProperty(access = JsonProperty.Access. READ_ONLY)
    @ApiModelProperty(notes = "Unique id for the cart item")//, hidden = true)
    private UUID id;
    @ApiModelProperty(notes = "name of the cart item")
    private String name;
    @ApiModelProperty(notes = "price of the cart item")
    private BigDecimal price;
    @ApiModelProperty(notes = "quantity of the cart item")
    private int quantity;
    @ApiModelProperty(notes = "image of the cart item")
    private String image;
}