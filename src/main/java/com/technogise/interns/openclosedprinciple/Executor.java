package com.technogise.interns.openclosedprinciple;

import java.math.BigDecimal;

public class Executor {
    public static void main(String[] args) {
        Money money = new Money();

        money.setAmount(BigDecimal.valueOf(50));

        PaymentManager paymentManager = new PaymentManager();

        paymentManager.pay(money,PaymentType.CREDIT_CARD);
    }
}
