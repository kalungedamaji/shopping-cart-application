package com.technogise.interns.openclosedprinciple;

public abstract class Payment {
    private  PaymentType paymentType;

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public abstract void pay(Money money);


}
