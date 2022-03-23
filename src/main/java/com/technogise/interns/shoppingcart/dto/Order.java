package com.technogise.interns.shoppingcart.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Order {
    private UUID id;
    private Instant timestamp;
    private String orderPaymentType;
    private String orderPaymentStatus;
    private List<OrderItem> orderItems;
}