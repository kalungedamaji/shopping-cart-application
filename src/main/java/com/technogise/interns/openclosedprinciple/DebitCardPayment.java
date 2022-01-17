package com.technogise.interns.openclosedprinciple;

public class DebitCardPayment extends Payment{

    public void pay(Money money){
        System.out.println("your payment method is " + getPaymentType().name() +" and amount is "+ money.getAmount());

    }

}
