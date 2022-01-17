package com.technogise.interns.liskovsubstitutionprinciple;

public class SavingAccount {
    public void deposit(Money  money){
        System.out.println(" Saving Deposited to the Account");
    }
    public void withdraw(Money  money){
        System.out.println("Saving withdrawn From Account ");
    }
}
