package com.technogise.interns.liskovsubstitutionprinciple;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private List<Account> accountList = new ArrayList<>();

    public Customer (List<Account> accountList){

        this.accountList = accountList;
    }

    public List<Account> getAccountList() {

        return accountList;
    }
}
