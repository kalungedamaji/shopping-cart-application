package com.technogise.interns.shoppingcart.customer.service;
import com.technogise.interns.shoppingcart.customer.entity.CustomerEntity;
import com.technogise.interns.shoppingcart.customer.mapper.CustomerMapper;
import com.technogise.interns.shoppingcart.customer.repository.CustomerRepository;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerMapper customerMapper;

@Test
    public void shouldReturnCustomerAfterItsCreationInRepository(){
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
    customerEntity.setFirstName("pranay");
    customerEntity.setLastName("jain");
    customerEntity.setAddress("indore");
    customerEntity.setPhoneNumber("9999999999");
    customerEntity.setEmailId("sdfg@dsfg.com");
    customerEntity.setPassword("^asd12");

    Customer customer = new Customer();
    customer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
    customer.setFirstName("pranay");
    customer.setLastName("jain");
    customer.setAddress("indore");
    customer.setPhoneNumber("9999999999");
    customer.setEmailId("sdfg@dsfg.com");
    customer.setPassword("^asd12");

    Mockito.when(customerMapper.mapToEntity(customer)).thenReturn(customerEntity);             //.thenAnswer(){}
    Mockito.when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
    Mockito.when(customerMapper.map(customerEntity)).thenReturn(customer);

    Customer actualCustomer = customerService.createCustomer(customer);
    verify(customerRepository,Mockito.times(1)).save(customerEntity);
    assertNotNull(actualCustomer.getId());
    assertThat(actualCustomer.getFirstName(),is(customer.getFirstName()));
    assertThat(actualCustomer.getLastName(),is(customer.getLastName()));
    assertThat(actualCustomer.getPhoneNumber(),is(customer.getPhoneNumber()));
    assertThat(actualCustomer.getEmailId(),is(customer.getEmailId()));
    assertThat(actualCustomer.getPassword(),is(customer.getPassword()));
    assertThat(actualCustomer.getAddress(),is(customer.getAddress()));
    }
@Test
    public void shouldReturnAllCustomersFromRepository(){
    List<CustomerEntity> customerEntityList = new ArrayList<>();

    CustomerEntity customerEntity1 = new CustomerEntity();
    customerEntity1.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
    customerEntity1.setFirstName("pranay");
    customerEntity1.setLastName("jain");
    customerEntity1.setAddress("indore");
    customerEntity1.setPhoneNumber("9999999999");
    customerEntity1.setEmailId("sdfg@dsfg.com");
    customerEntity1.setPassword("^asd12");

    CustomerEntity customerEntity2 = new CustomerEntity();
    customerEntity2.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
    customerEntity2.setFirstName("parth");
    customerEntity2.setLastName("deshmukh");
    customerEntity2.setAddress("india");
    customerEntity2.setPhoneNumber("9999990000");
    customerEntity2.setEmailId("sdfg@abc.com");
    customerEntity2.setPassword("^asd12312");

    customerEntityList.add(customerEntity1);
    customerEntityList.add(customerEntity2);

    Customer customer1 = new Customer();
    List<Customer> expectedCustomerList = new ArrayList<>();
    customer1.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
    customer1.setFirstName("pranay");
    customer1.setLastName("jain");
    customer1.setAddress("indore");
    customer1.setPhoneNumber("9999999999");
    customer1.setEmailId("sdfg@dsfg.com");
    customer1.setPassword("^asd12");

    Customer customer2 = new Customer();
    customer2.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
    customer2.setFirstName("parth");
    customer2.setLastName("deshmukh");
    customer2.setAddress("india");
    customer2.setPhoneNumber("9999990000");
    customer2.setEmailId("sdfg@abc.com");
    customer2.setPassword("^asd12312");

    expectedCustomerList.add(customer1);
    expectedCustomerList.add(customer2);

    Mockito.when(customerRepository.findAll()).thenReturn(customerEntityList);
    Mockito.when(customerMapper.map(customerEntity1)).thenReturn(customer1);
    Mockito.when(customerMapper.map(customerEntity2)).thenReturn(customer2);

    List<Customer> actualCustomerList = customerService.getAllCustomer();

    assertThat(actualCustomerList, is(expectedCustomerList));
 }
 @Test
    public void shouldReturnCustomerFromRepositoryWithRequiredId(){

    CustomerEntity customerEntity = new CustomerEntity();
     customerEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
     customerEntity.setFirstName("pranay");
     customerEntity.setLastName("jain");
     customerEntity.setAddress("indore");
     customerEntity.setPhoneNumber("9999999999");
     customerEntity.setEmailId("sdfg@dsfg.com");
     customerEntity.setPassword("^asd12");

     Customer customer = new Customer();
     customer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
     customer.setFirstName("pranay");
     customer.setLastName("jain");
     customer.setAddress("indore");
     customer.setPhoneNumber("9999999999");
     customer.setEmailId("sdfg@dsfg.com");
     customer.setPassword("^asd12");

     Customer expectedRequiredCustomer = new Customer();
     expectedRequiredCustomer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
     expectedRequiredCustomer.setFirstName("pranay");
     expectedRequiredCustomer.setLastName("jain");
     expectedRequiredCustomer.setAddress("indore");
     expectedRequiredCustomer.setPhoneNumber("9999999999");
     expectedRequiredCustomer.setEmailId("sdfg@dsfg.com");
     expectedRequiredCustomer.setPassword("^asd12");

     Mockito.when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.of(customerEntity));
     Mockito.when(customerMapper.map(any(CustomerEntity.class))).thenReturn(customer);

     Customer actualRequiredCustomer = customerService.getCustomerById(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));

     assertThat(actualRequiredCustomer,is(expectedRequiredCustomer));
 }

    @Test
    public void getCustomerShouldThrowNotFoundExceptionWhenCustomerIsNotPresent(){

        UUID customerId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");

        Mockito.when(customerRepository.findById(any())).thenReturn(Optional.empty());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerById(customerId), "EntityNotFoundException was expected");

        assertThat(thrown.getMessage(), is("Customer was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}"));
    }

    @Test
    public void replaceCustomerShouldThrowNotFoundExceptionWhenCustomerIdIsInvalid(){
        Customer newCustomerDetail = new Customer();
        newCustomerDetail.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        newCustomerDetail.setFirstName("pranay");
        newCustomerDetail.setLastName("jain");
        newCustomerDetail.setAddress("indore");
        newCustomerDetail.setPhoneNumber("9999999999");
        newCustomerDetail.setEmailId("sdfg@dsfg.com");
        newCustomerDetail.setPassword("^asd12");
        UUID customerId = UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36");

        Mockito.when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> customerService.replaceCustomer(newCustomerDetail,customerId),"EntityNotFoundException was expected");
        assertThat(thrown.getMessage(), is("Customer was not found for parameters {id=676ea10c-537b-4861-b27b-f3b8cbc0dc36}"));
    }

    @Test
    public void deleteCustomerShouldThrowNotFoundexceptionWhenCustomerIdIsInvalid(){

        UUID customerId = UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36");

        Mockito.when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> customerService.deleteCustomer(customerId),"EntityNotFoundException was expected");
        assertThat(thrown.getMessage(), is("Customer was not found for parameters {id=676ea10c-537b-4861-b27b-f3b8cbc0dc36}"));
    }

    @Test
    public void shouldDeleteRequiredEntityFromRepository(){
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        customerEntity.setFirstName("pranay");
        customerEntity.setLastName("jain");
        customerEntity.setAddress("indore");
        customerEntity.setPhoneNumber("9999999999");
        customerEntity.setEmailId("sdfg@dsfg.com");
        customerEntity.setPassword("^asd12");

        Mockito.when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.of(customerEntity));
        Mockito.doNothing().when(customerRepository).deleteById(any(UUID.class));

        customerService.deleteCustomer(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc39"));

        verify(customerRepository,Mockito.times(1)).deleteById(any(UUID.class));
    }
    @Test
    public void shouldReturnUpdatedCustomerFromRepository(){

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        customerEntity.setFirstName("pranay");
        customerEntity.setLastName("jain");
        customerEntity.setAddress("indore");
        customerEntity.setPhoneNumber("9999999999");
        customerEntity.setEmailId("sdfg@dsfg.com");
        customerEntity.setPassword("^asd12");

        CustomerEntity newCustomerEntity = new CustomerEntity();
        newCustomerEntity.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        newCustomerEntity.setFirstName("parth");
        newCustomerEntity.setLastName("deshmukh");
        newCustomerEntity.setAddress("india");
        newCustomerEntity.setPhoneNumber("9999990000");
        newCustomerEntity.setEmailId("sdfg@qaz.com");
        newCustomerEntity.setPassword("^qwert!12");

        Customer newCustomerDetail = new Customer();
        newCustomerDetail.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        newCustomerDetail.setFirstName("parth");
        newCustomerDetail.setLastName("deshmukh");
        newCustomerDetail.setAddress("india");
        newCustomerDetail.setPhoneNumber("9999990000");
        newCustomerDetail.setEmailId("sdfg@qaz.com");
        newCustomerDetail.setPassword("^qwert!12");

        Mockito.when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.of(customerEntity));
        Mockito.when(customerRepository.save(any(CustomerEntity.class))).thenReturn(newCustomerEntity);
        Mockito.when(customerMapper.mapToEntity(any(Customer.class))).thenReturn(newCustomerEntity);
        Mockito.when(customerMapper.map(newCustomerEntity)).thenReturn(newCustomerDetail);

        Customer actualReplacedCustomer = customerService.replaceCustomer(newCustomerDetail,UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));

        assertThat(actualReplacedCustomer,is(newCustomerDetail));
    }
}









