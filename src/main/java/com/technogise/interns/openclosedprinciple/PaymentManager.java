package com.technogise.interns.openclosedprinciple;

public class PaymentManager {


    public void pay(Money money, PaymentType paymentType) {
        Payment payment = getPayment(paymentType);
        payment.setPaymentType(paymentType);
       payment.pay(money);
    }
    private Payment getPayment(PaymentType paymentType){
        if (paymentType == PaymentType.CASH) {
            return new CashPayment();

        } else if (paymentType == PaymentType.CREDIT_CARD) {
            return new CreditCardPayment();

        } else if (paymentType == PaymentType.DEBIT_CARD) {
            return new DebitCardPayment();
        }
        else
            return null;
    }
}