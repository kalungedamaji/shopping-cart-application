package com.technogise.interns.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.technogise.interns.shoppingcart.enums.PaymentStatus;
import com.technogise.interns.shoppingcart.enums.PaymentType;
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
    private PaymentType orderPaymentType;
    private PaymentStatus orderPaymentStatus;
    private List<OrderItem> orderItems;
}

