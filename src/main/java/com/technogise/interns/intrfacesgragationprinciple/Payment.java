package com.technogise.interns.intrfacesgragationprinciple;

import java.util.List;

public interface Payment {
    void initiatePayments();
    Object status();
    List<Object> getPayments();
    void initiateLoanSettlement();
    void initiateRePayment();
}
