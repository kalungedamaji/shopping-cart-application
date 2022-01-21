package com.technogise.interns.dependencyinversion;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CustomerDao implements Dao{

    public Optional<Customer> findById(int id) {
        /**
         *  select * from customer where id=id;
         */
        return Optional.empty();
    }

    public List<Customer> findAll() {
        /**
         *  select * from customer;
         */
        return Collections.EMPTY_LIST;
    }
}
