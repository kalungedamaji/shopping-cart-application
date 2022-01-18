package com.technogise.interns.intrfacesgragationprinciple;

import java.util.List;

public class BankPayment implements  Payment{
    @Override
    public void initiatePayments() {

    }

    @Override
    public Object status() {
        return null;
    }

    @Override
    public List<Object> getPayments() {
        return null;
    }

    @Override
    public void initiateLoanSettlement() {
        throw  new RuntimeException("Operation not Supported");
    }

    @Override
    public void initiateRePayment() {
        throw  new RuntimeException("Operation not Supported");
    }
}
