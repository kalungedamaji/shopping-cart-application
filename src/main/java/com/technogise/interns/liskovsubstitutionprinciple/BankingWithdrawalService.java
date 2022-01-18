package com.technogise.interns.liskovsubstitutionprinciple;

import java.util.ArrayList;
import java.util.List;

public class BankingWithdrawalService {
    private List<WithdrawalAccount> withdrawalAccountList = new ArrayList<>();
    public void withDrawFromAllAccount(Money money){
        for(WithdrawalAccount withdrawalAccount: getWithdrawalAccountList()) {
            withdrawalAccount.withdraw(money);
        }
    }

    public List<WithdrawalAccount> getWithdrawalAccountList() {
        return withdrawalAccountList;
    }

    public void setWithdrawalAccountList(List<WithdrawalAccount> withdrawalAccountList) {
        this.withdrawalAccountList = withdrawalAccountList;
    }
}
