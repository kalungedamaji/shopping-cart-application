package com.technogise.interns.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Order {
    private UUID id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Instant timestamp;
    private String orderPaymentType;
    private String orderPaymentStatus;
    private List<OrdersOrderItem> orderItems;
}