package com.technogise.interns.liskovsubstitutionprinciple;

public class SalaryAccount extends WithdrawalAccount {

    public void deposit(Money  money){
        System.out.println("Deposited to the Salary Account ");
    }
    public void withdraw(Money  money){
        System.out.println("withdrawn From  Salary Account ");
    }
}