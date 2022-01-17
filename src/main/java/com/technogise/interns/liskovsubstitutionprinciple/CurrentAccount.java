package com.technogise.interns.liskovsubstitutionprinciple;

public class CurrentAccount {

    public void deposit(Money  money){
    System.out.println("Deposited to the Account");
    }
    public void withdraw(Money  money){
        System.out.println("withdrawn From Account ");
    }
}
