package com.technogise.interns.liskovsubstitutionprinciple;

import java.util.ArrayList;
import java.util.List;

public class Executor {
    public static void main(String[] args) {
        BankingAppService bankService=new BankingAppService();
        List<Account> accountList = new ArrayList<>();
        accountList.add(new CurrentAccount());
        accountList.add(new SavingAccount());
        accountList.add(new SalaryAccount());
        accountList.add(new FixedTermDeposit());

        Customer  customerA = new Customer(accountList);
        bankService.withDrawFromAllAccount(new Money(),customerA);

        Customer  customerB=new Customer(accountList);
        bankService.depositToAllAccount(new Money(),customerB);
    }
}
