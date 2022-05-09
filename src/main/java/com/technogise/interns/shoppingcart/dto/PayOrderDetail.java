package com.technogise.interns.shoppingcart.dto;

import com.technogise.interns.shoppingcart.enums.PaymentType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class PayOrderDetail {
    private PaymentType paymentType;
}
