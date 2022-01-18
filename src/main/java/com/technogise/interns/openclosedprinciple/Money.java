package com.technogise.interns.openclosedprinciple;

import java.math.BigDecimal;

public class Money {
    private BigDecimal amount;

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
