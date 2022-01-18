package com.technogise.interns.openclosedprinciple;

public abstract class Payment {
    PaymentType paymentType;
    public abstract void pay(Money money) ;

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType= paymentType;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }
}
