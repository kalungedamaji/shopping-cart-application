package com.technogise.interns.shoppingcart.orders.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;


import com.technogise.interns.shoppingcart.enums.OrderStatus;
import com.technogise.interns.shoppingcart.enums.PaymentStatus;
import com.technogise.interns.shoppingcart.enums.PaymentType;
import com.technogise.interns.shoppingcart.orders.orderItems.entity.OrdersOrderItemEntity;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name="Orders")
@TypeDef(
        name = "PAYMENT_STATUS",
        typeClass = PostgreSQLEnumType.class
)
@TypeDef(
        name = "PAYMENT_TYPE",
        typeClass = PostgreSQLEnumType.class
)
@TypeDef(
        name = "ORDER_STATUS",
        typeClass = PostgreSQLEnumType.class
)
public class OrderEntity {
    @Id
    private UUID id;

    @Column(name="order_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Instant timestamp;

    @Column(name="order_payment_type")
    @Enumerated(EnumType.STRING)
    @Type(type = "PAYMENT_TYPE")
    private PaymentType orderPaymentType;


    @Column(name="order_payment_status")
    @Enumerated(EnumType.STRING)
    @Type(type = "PAYMENT_STATUS")
    private PaymentStatus orderPaymentStatus;

    @Column(name="order_status")
    @Enumerated(EnumType.STRING)
    @Type(type = "ORDER_STATUS")
    private OrderStatus orderStatus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private List<OrdersOrderItemEntity> orderItems;
}