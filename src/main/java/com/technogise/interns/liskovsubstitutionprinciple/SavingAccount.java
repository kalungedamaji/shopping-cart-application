package com.technogise.interns.liskovsubstitutionprinciple;

public class SavingAccount {
    public void deposit(Money  money){
        System.out.println("  Deposited to the Saving Account");
    }
    public void withdraw(Money  money){
        System.out.println(" withdrawn From Saving Account ");
    }
}
