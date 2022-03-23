package com.technogise.interns.shoppingcart.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

import java.util.UUID;

@Getter
@Setter
public class Orders {
    private UUID id;
//    private LocalDate date;
//    private LocalTime time;
    private Instant timestamp;
    private String orderPaymentType;
    private String orderPaymentStatus;
}
