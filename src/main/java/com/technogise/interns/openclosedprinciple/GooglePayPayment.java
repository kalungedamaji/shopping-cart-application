package com.technogise.interns.openclosedprinciple;

public class GooglePayPayment extends Payment{
    public void pay(Money money)
    {
        System.out.println("Payment method is " +getPaymentType()+ " and amount is " +money.getAmount());
    }
}
