package com.technogise.interns.liskovsubstitutionprinciple;

import java.util.ArrayList;
import java.util.List;

public class BankingAppService {

    public void depositToAllAccount(Money money, Customer customer){
        for (Account account: customer.getAccountList()) {
            account.deposit(money);
        }
    }

    public void withDrawFromAllAccount(Money money, Customer customer){
        BankingWithdrawalService bankingWithdrawalService= new BankingWithdrawalService();
        List<WithdrawalAccount> withdrawalAccountList = new ArrayList<>();
        for (Account account: customer.getAccountList()) {
            if(account instanceof WithdrawalAccount)
            {
                withdrawalAccountList.add((WithdrawalAccount) account);
            }
        }
        bankingWithdrawalService.setWithdrawalAccountList(withdrawalAccountList);
        bankingWithdrawalService.withDrawFromAllAccount(money);
    }
}