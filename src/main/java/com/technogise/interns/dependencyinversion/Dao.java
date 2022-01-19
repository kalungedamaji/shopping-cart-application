package com.technogise.interns.dependencyinversion;

import java.util.List;
import java.util.Optional;

public interface Dao {
    public Optional<Customer> findById(int id);
    public List<Customer> findAll();
}
