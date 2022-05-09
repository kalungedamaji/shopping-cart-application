package com.technogise.interns.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.technogise.interns.shoppingcart.enums.OrderStatus;
import com.technogise.interns.shoppingcart.enums.PaymentStatus;
import com.technogise.interns.shoppingcart.enums.PaymentType;
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
    @JsonProperty(access = JsonProperty.Access. READ_ONLY)
    private Instant timestamp;
    private PaymentType orderPaymentType;
    //private PayOrderDetail payOrderDetail;
    private PaymentStatus orderPaymentStatus;
    private OrderStatus orderStatus;
    private List<OrdersOrderItem> orderItems;
}