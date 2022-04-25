package com.technogise.interns.shoppingcart.orders.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name="Orders")
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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private List<OrdersOrderItemEntity> orderItems;
}