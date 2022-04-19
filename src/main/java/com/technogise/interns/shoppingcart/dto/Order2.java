package com.technogise.interns.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Order2 {
    @JsonProperty(access = JsonProperty.Access. READ_ONLY)
    private UUID id;
    private Instant timestamp;
    private String orderPaymentType;
    private String orderPaymentStatus;
    private List<OrderItem> orderItems;
}

