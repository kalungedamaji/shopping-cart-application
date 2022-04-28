package com.technogise.interns.shoppingcart.customer.repository;

import com.technogise.interns.shoppingcart.customer.entity.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void shouldBeEmptyIfNoCustomersArePresent(){
        List<CustomerEntity> customers = customerRepository.findAll();
        assertThat(customers).isEmpty();
    }

    @Test
    public void shouldSaveACustomer() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        customerEntity.setFirstName("Pranay");
        customerEntity.setLastName("Jain");
        customerEntity.setPhoneNumber("9999999999");
        customerEntity.setAddress("Indore");
        customerEntity.setEmailId("abc@123.com");
        customerEntity.setPassword("qaz123");

        CustomerEntity savedCustomer = customerRepository.save(customerEntity);

        assertThat(savedCustomer).hasFieldOrPropertyWithValue("id", UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
    }

    @Test
    public void shouldDeleteCustomerById() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        customerEntity.setFirstName("Pranay");
        customerEntity.setLastName("Jain");
        customerEntity.setPhoneNumber("9999999999");
        customerEntity.setAddress("Indore");
        customerEntity.setEmailId("abc@123.com");
        customerEntity.setPassword("qaz123");

        entityManager.persist(customerEntity);

        customerRepository.deleteById(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));

        assertThat(customerRepository.findById(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"))).isEmpty();
    }

    @Test
    public void shouldFindAllCustomers() {
        CustomerEntity customerEntity1 = new CustomerEntity();
        customerEntity1.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        customerEntity1.setFirstName("Pranay");
        customerEntity1.setLastName("Jain");
        customerEntity1.setPhoneNumber("9999999999");
        customerEntity1.setAddress("Indore");
        customerEntity1.setEmailId("abc@123.com");
        customerEntity1.setPassword("qaz123");
        entityManager.persist(customerEntity1);

        CustomerEntity customerEntity2 = new CustomerEntity();
        customerEntity2.setId(UUID.fromString("3dfed457-5466-4ab5-aae8-7c69a21a82c4"));
        customerEntity2.setFirstName("Parth");
        customerEntity2.setLastName("Deshmukh");
        customerEntity2.setPhoneNumber("9999999990");
        customerEntity2.setAddress("India");
        customerEntity2.setEmailId("abcd@123.com");
        customerEntity2.setPassword("qazw123");
        entityManager.persist(customerEntity2);

        List<CustomerEntity> customers = customerRepository.findAll();

        assertThat(customers).hasSize(2).contains(customerEntity1,customerEntity2);
    }

    @Test
    public void findCustomerById() {
        CustomerEntity customerEntity1 = new CustomerEntity();
        customerEntity1.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));
        customerEntity1.setFirstName("Pranay");
        customerEntity1.setLastName("Jain");
        customerEntity1.setPhoneNumber("9999999999");
        customerEntity1.setAddress("Indore");
        customerEntity1.setEmailId("abc@123.com");
        customerEntity1.setPassword("qaz123");
        entityManager.persist(customerEntity1);

        CustomerEntity customerEntity2 = new CustomerEntity();
        customerEntity2.setId(UUID.fromString("3dfed457-5466-4ab5-aae8-7c69a21a82c4"));
        customerEntity2.setFirstName("Parth");
        customerEntity2.setLastName("Deshmukh");
        customerEntity2.setPhoneNumber("9999999990");
        customerEntity2.setAddress("India");
        customerEntity2.setEmailId("abcd@123.com");
        customerEntity2.setPassword("qazw123");
        entityManager.persist(customerEntity2);

        Optional<CustomerEntity> getCustomer = customerRepository.findById(customerEntity1.getId());
        Optional<CustomerEntity> optionalCustomerEntity = Optional.of(customerEntity1);
        assertThat(getCustomer).isEqualTo(optionalCustomerEntity);
    }
}
