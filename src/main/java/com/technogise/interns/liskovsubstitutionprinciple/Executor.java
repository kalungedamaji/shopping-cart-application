package com.technogise.interns.liskovsubstitutionprinciple;

public class Executor {
    public static void main(String[] args) {
        BankingAppWithdrawalService  bankService=new  BankingAppWithdrawalService();
        Customer  customerA=new Customer(new CurrentAccount(), new SavingAccount());
        bankService.withDrawFromAllAccount(new Money(),customerA);
        Customer  customerB=new Customer(new CurrentAccount(), new SavingAccount());
        bankService.depositToAllAccount(new Money(),customerB);
    }
}
