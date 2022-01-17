package com.technogise.interns.openclosedprinciple;

public class PaymentManager {
    private  PaymentType paymentType;

    public void Pay(Money money){
        if(paymentType == PaymentType.CASH){
// pay with cash
        }else if(paymentType == PaymentType.CREDIT_CARD){
// pay with credit card
        } else if(paymentType == PaymentType.DEBIT_CARD){
// pay with Debit card
    }
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
