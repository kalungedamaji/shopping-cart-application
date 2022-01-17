package com.technogise.interns.liskovsubstitutionprinciple;

public class Customer {
    private  CurrentAccount  currentAccount;
    private  SavingAccount  savingAccount;

    public Customer (CurrentAccount  currentAccount, SavingAccount  savingAccount){
        this.currentAccount=currentAccount;
        this.savingAccount=savingAccount;

    }
    public void setCurrentAccount(CurrentAccount currentAccount) {
        this.currentAccount = currentAccount;
    }

    public void setSavingAccount(SavingAccount savingAccount) {
        this.savingAccount = savingAccount;
    }

    public CurrentAccount getCurrentAccount() {
        return currentAccount;
    }

    public SavingAccount getSavingAccount() {
        return savingAccount;
    }
}
