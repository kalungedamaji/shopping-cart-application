package com.technogise.interns.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Order {
    @JsonProperty(access = JsonProperty.Access. READ_ONLY)
    private UUID id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Instant timestamp;
    private String orderPaymentType;
    private String orderPaymentStatus;
    private String orderStatus;
    private List<OrdersOrderItem> orderItems;
}