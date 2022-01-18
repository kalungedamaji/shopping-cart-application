package com.technogise.interns.intrfacesgragationprinciple;

import java.util.List;

public class LoanPayment implements Payment {
    @Override
    public void initiatePayments() {
        throw  new RuntimeException("Operation not Supported");
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

    }

    @Override
    public void initiateRePayment() {

    }
}
