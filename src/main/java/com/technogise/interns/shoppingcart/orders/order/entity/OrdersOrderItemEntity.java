package com.technogise.interns.shoppingcart.orders.order.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity(name="OrdersOrderItem")
public class OrdersOrderItemEntity {
        @Id
        private UUID id;
        private String name;
        private String image;
        private String description;
        private int quantity;
        private BigDecimal price;
}

/*
@Data
@Entity(name="Order")
public class OrderEntity {
    @Id
    private UUID id;
    @Column(name="order_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Instant timestamp;
    @Column(name="order_payment_type")
    private String orderPaymentType;
    @Column(name="order_payment_status")
    private String orderPaymentStatus;
    @Column(name="order_status")
    private String orderStatus;
    private List<OrdersOrderItem> orderItems;
}
 */
