package com.technogise.interns.liskovsubstitutionprinciple;

public class BankingAppWithdrawalService {



 public void depositToAllAccount(Money money, Customer customer){
   customer.getCurrentAccount().deposit(money);
   customer.getSavingAccount().deposit(money);
 }
}
