package com.technogise.interns.liskovsubstitutionprinciple;

public class CurrentAccount {

    public void deposit(Money  money){
    System.out.println("Deposited to the Current Account ");
    }
    public void withdraw(Money  money){
        System.out.println("withdrawn From  Current Account ");
    }
}
