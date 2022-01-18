package com.technogise.interns.liskovsubstitutionprinciple;


public class FixedTermDeposit extends Account {

    public void deposit(Money money) {
        System.out.println("Deposited to Fixed Term Deposit");
    }


}

