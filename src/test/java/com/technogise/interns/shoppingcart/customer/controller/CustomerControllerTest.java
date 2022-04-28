package com.technogise.interns.shoppingcart.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technogise.interns.shoppingcart.cart.CartController;
import com.technogise.interns.shoppingcart.customer.CustomerController;
import com.technogise.interns.shoppingcart.orders.order.OrderController;
import com.technogise.interns.shoppingcart.representation.HttpMethods;
import com.technogise.interns.shoppingcart.customer.hateosLinksProvider.CustomerLinks;
import com.technogise.interns.shoppingcart.customer.service.CustomerService;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import com.technogise.interns.shoppingcart.store.controller.ProductController;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value= CustomerController.class)

public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerLinks customerLinks;

    @Test
    public void shouldCreateCustomer() throws Exception {

        Customer newCustomer = new Customer();
        newCustomer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        newCustomer.setFirstName("Pranay");
        newCustomer.setLastName("Jain");
        newCustomer.setPhoneNumber("9999999999");
        newCustomer.setAddress("Indore");
        newCustomer.setEmailId("abc@xyz.com");
        newCustomer.setPassword("123abcd@");

        EntityModel<Customer> resource = EntityModel.of(newCustomer);
        resource.add(linkTo(methodOn(CustomerController.class).getCustomer(newCustomer.getId())).withSelfRel());
        resource.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"));
        resource.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("store"));

        Mockito.when(customerService.createCustomer(any(Customer.class))).thenReturn(newCustomer);
        doReturn(resource).when(customerLinks).getHateosLinks(newCustomer,HttpMethods.POST);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:9000/customers")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomer))
                .contentType(MediaType.APPLICATION_JSON);

        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        expectedCustomer.setFirstName("Pranay");
        expectedCustomer.setLastName("Jain");
        expectedCustomer.setPhoneNumber("9999999999");
        expectedCustomer.setAddress("Indore");
        expectedCustomer.setEmailId("abc@xyz.com");
        expectedCustomer.setPassword("123abcd@");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",is(expectedCustomer.getId().toString())))
                .andExpect(jsonPath("$.address",is(expectedCustomer.getAddress())))
                .andExpect(jsonPath("$.firstName",is(expectedCustomer.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(expectedCustomer.getLastName())))
                .andExpect(jsonPath("$.phoneNumber",is(expectedCustomer.getPhoneNumber())))
                .andExpect(jsonPath("$.emailId", is(expectedCustomer.getEmailId())))
                .andExpect(jsonPath("$.password", is(expectedCustomer.getPassword())))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[1].rel", is("all-customers")))
                .andExpect(jsonPath("$.links[1].href", is("/customers")))
                .andExpect(jsonPath("$.links[2].rel", is("store")))
                .andExpect(jsonPath("$.links[2].href", is("/products")));

    }
    @Test
    public void shouldReturnAllCustomers()throws Exception{
        List<EntityModel> entityModelList = new ArrayList<>();

        Customer customerOne = new Customer();
        customerOne.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        customerOne.setFirstName("Pranay");
        customerOne.setLastName("Jain");
        customerOne.setPhoneNumber("9999999999");
        customerOne.setAddress("Indore");
        customerOne.setEmailId("abc@xyz.com");
        customerOne.setPassword("123abcd@");

        Customer customerTwo = new Customer();
        customerTwo.setId(UUID.fromString("104c1a45-4d81-4769-8ad2-83437ddd15ce"));
        customerTwo.setFirstName("Parth");
        customerTwo.setLastName("Deshmukh");
        customerTwo.setPhoneNumber("9999999990");
        customerTwo.setAddress("India");
        customerTwo.setEmailId("abc@yxz.com");
        customerTwo.setPassword("123qaz");

        List<Customer> newCustomerList = new ArrayList<>();
        newCustomerList.add(customerOne);
        newCustomerList.add(customerTwo);

        EntityModel<Customer> entityModelOne = EntityModel.of(customerOne);
        entityModelOne.add(linkTo(methodOn(CustomerController.class).getCustomer(customerOne.getId())).withSelfRel());
        EntityModel<Customer> entityModelTwo = EntityModel.of(customerTwo);
        entityModelTwo.add(linkTo(methodOn(CustomerController.class).getCustomer(customerTwo.getId())).withSelfRel());

        entityModelList.add(entityModelOne);
        entityModelList.add(entityModelTwo);

        CollectionModel<EntityModel> collectionModel = CollectionModel.of(entityModelList);
        collectionModel.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());
        collectionModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("product-store"));

        Mockito.when(customerService.getAllCustomer()).thenReturn(newCustomerList);
        doReturn(collectionModel).when(customerLinks).getHateosLinks(newCustomerList,HttpMethods.GET);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:9000/customers").accept(MediaType.APPLICATION_JSON);


        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.links[0].rel",is("self")))
                .andExpect(jsonPath("$.links[0].href", is("/customers")))
                .andExpect(jsonPath("$.links[1].rel",is("product-store")))
                .andExpect(jsonPath("$.links[1].href", is("/products")))
                .andExpect(jsonPath("$.content[0].id",is("676ea10c-537b-4861-b27b-f3b8cbc0dc36")))
                .andExpect(jsonPath("$.content[0].firstName",is("Pranay")))
                .andExpect(jsonPath("$.content[0].lastName",is("Jain")))
                .andExpect(jsonPath("$.content[0].phoneNumber",is("9999999999")))
                .andExpect(jsonPath("$.content[0].address",is("Indore")))
                .andExpect(jsonPath("$.content[0].emailId",is("abc@xyz.com")))
                .andExpect(jsonPath("$.content[0].password",is("123abcd@")))
                .andExpect(jsonPath("$.content[0].links[0].rel",is("self")))
                .andExpect(jsonPath("$.content[0].links[0].href", is("/customers/676ea10c-537b-4861-b27b-f3b8cbc0dc36")))
                .andExpect(jsonPath("$.content[1].id",is("104c1a45-4d81-4769-8ad2-83437ddd15ce")))
                .andExpect(jsonPath("$.content[1].firstName",is("Parth")))
                .andExpect(jsonPath("$.content[1].lastName",is("Deshmukh")))
                .andExpect(jsonPath("$.content[1].phoneNumber",is("9999999990")))
                .andExpect(jsonPath("$.content[1].address",is("India")))
                .andExpect(jsonPath("$.content[1].emailId",is("abc@yxz.com")))
                .andExpect(jsonPath("$.content[1].password",is("123qaz")))
                .andExpect(jsonPath("$.content[1].links[0].rel",is("self")))
                .andExpect(jsonPath("$.content[1].links[0].href", is("/customers/104c1a45-4d81-4769-8ad2-83437ddd15ce")));


    }
    @Test
    public void shouldReturnCustomerWithRequiredId() throws Exception {
        Customer newCustomer = new Customer();
        newCustomer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        newCustomer.setFirstName("Pranay");
        newCustomer.setLastName("Jain");
        newCustomer.setPhoneNumber("9999999999");
        newCustomer.setAddress("Indore");
        newCustomer.setEmailId("abc@xyz.com");
        newCustomer.setPassword("123abcd@");
        EntityModel<Customer> resource = EntityModel.of(newCustomer);
        resource.add(linkTo(methodOn(OrderController.class).getAllOrders(newCustomer.getId())).withRel("Orders"));
        resource.add(linkTo(methodOn(CartController.class).getAllCartItems(newCustomer.getId())).withRel("cart-items"));
        resource.add(linkTo(methodOn(CustomerController.class).getCustomer(newCustomer.getId())).withSelfRel());

        Mockito.when(customerService.getCustomerById(any(UUID.class))).thenReturn(newCustomer);
        doReturn(resource).when(customerLinks).getHateosLinks(newCustomer,HttpMethods.GET_WITH_ID);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:9000/customers/676ea10c-537b-4861-b27b-f3b8cbc0dc36")
                .accept(MediaType.APPLICATION_JSON);

        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        expectedCustomer.setFirstName("Pranay");
        expectedCustomer.setLastName("Jain");
        expectedCustomer.setPhoneNumber("9999999999");
        expectedCustomer.setAddress("Indore");
        expectedCustomer.setEmailId("abc@xyz.com");
        expectedCustomer.setPassword("123abcd@");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(expectedCustomer.getId().toString())))
                .andExpect(jsonPath("$.address",is(expectedCustomer.getAddress())))
                .andExpect(jsonPath("$.firstName",is(expectedCustomer.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(expectedCustomer.getLastName())))
                .andExpect(jsonPath("$.phoneNumber",is(expectedCustomer.getPhoneNumber())))
                .andExpect(jsonPath("$.emailId", is(expectedCustomer.getEmailId())))
                .andExpect(jsonPath("password", is(expectedCustomer.getPassword())));

    }

    @Test
    public void shouldDeleteCartItem() throws Exception{

        Customer newCustomer = new Customer();
        newCustomer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        newCustomer.setFirstName("Pranay");
        newCustomer.setLastName("Jain");
        newCustomer.setPhoneNumber("9999999999");
        newCustomer.setAddress("Indore");
        newCustomer.setEmailId("abc@xyz.com");
        newCustomer.setPassword("123abcd@");

        Mockito.doNothing().when(customerService).deleteCustomer(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("http://localhost:9000/customers/676ea10c-537b-4861-b27b-f3b8cbc0dc36")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
    @Test
    public void shouldReplaceCustomer() throws Exception {

        Customer newCustomerDetail = new Customer();
        newCustomerDetail.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        newCustomerDetail.setFirstName("Parth");
        newCustomerDetail.setLastName("Deshmukh");
        newCustomerDetail.setPhoneNumber("9999990000");
        newCustomerDetail.setAddress("India");
        newCustomerDetail.setEmailId("abc@123.com");
        newCustomerDetail.setPassword("123@qaz");

        EntityModel<Customer> resource = EntityModel.of(newCustomerDetail);
        resource.add(linkTo(methodOn(CustomerController.class).getCustomer(newCustomerDetail.getId())).withSelfRel());
        resource.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"));

        Mockito.when(customerService.replaceCustomer(newCustomerDetail, UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"))).thenReturn(newCustomerDetail);
        doReturn(resource).when(customerLinks).getHateosLinks(newCustomerDetail,HttpMethods.PUT);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("http://localhost:9000/customers/676ea10c-537b-4861-b27b-f3b8cbc0dc36")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomerDetail))
                .contentType(MediaType.APPLICATION_JSON);

        Customer replacedCustomer = new Customer();
        replacedCustomer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        replacedCustomer.setFirstName("Parth");
        replacedCustomer.setLastName("Deshmukh");
        replacedCustomer.setPhoneNumber("9999990000");
        replacedCustomer.setAddress("India");
        replacedCustomer.setEmailId("abc@123.com");
        replacedCustomer.setPassword("123@qaz");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(replacedCustomer.getId().toString())))
                .andExpect(jsonPath("$.address",is(replacedCustomer.getAddress())))
                .andExpect(jsonPath("$.firstName",is(replacedCustomer.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(replacedCustomer.getLastName())))
                .andExpect(jsonPath("$.phoneNumber",is(replacedCustomer.getPhoneNumber())))
                .andExpect(jsonPath("$.emailId", is(replacedCustomer.getEmailId())))
                .andExpect(jsonPath("$.password", is(replacedCustomer.getPassword())));


    }

    @Test
    public void shouldReturnNotFoundErrorWhenCustomerIsNotPresent() throws Exception {


        UUID customerId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");

        Mockito.when(customerService.getCustomerById(any(UUID.class))).thenThrow(new EntityNotFoundException(Customer.class, "id", customerId.toString()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98678")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("Customer was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());

    }

    @Test
    public void shouldReturnUnprocessableEntityWhenThereIsConstraintVoilation() throws Exception {

        Customer newCustomer = new Customer();
        newCustomer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        newCustomer.setFirstName("Shrishti");
        newCustomer.setLastName("Jain");
        newCustomer.setPhoneNumber("7004960921");
        newCustomer.setAddress("India");
        newCustomer.setEmailId("shris.des@technogise.com");
        newCustomer.setPassword("abc@xyz");

        Mockito.when(customerService.createCustomer(any(Customer.class))).thenThrow(new ConstraintViolationException("could not execute statement", new SQLException("ERROR: duplicate key value violates unique constraint \"customer_phone_number_key\"\n  Detail: Key (phone_number)=(7004960921) already exists.") , "customer_phone_number_key"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:9000/customers")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomer))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status", is("UNPROCESSABLE_ENTITY")))
                .andExpect(jsonPath("$.timestamp", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))))
                .andExpect(jsonPath("$.message", is("could not execute statement")))
                .andExpect(jsonPath("$.debugMessage[0]", is("ERROR: duplicate key value violates unique constraint \"customer_phone_number_key\"\n  Detail: Key (phone_number)=(7004960921) already exists.")));
    }
    @Test
    public void shouldReturnNotFoundErrorWhenCustomerIsNotPresentForDeletion() throws Exception {

        UUID customerId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");

        Mockito.doThrow(new EntityNotFoundException(Customer.class, "id", customerId.toString())).when(customerService).deleteCustomer(customerId);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98678")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("Customer was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());

    }
    @Test
    public void shouldReturnNotFoundErrorWhenCustomerIsNotPresentForUpdation() throws Exception {

        Customer newCustomerDetail = new Customer();
        newCustomerDetail.setId(UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678"));
        newCustomerDetail.setFirstName("Parth");
        newCustomerDetail.setLastName("Deshmukh");
        newCustomerDetail.setPhoneNumber("9999990000");
        newCustomerDetail.setAddress("India");
        newCustomerDetail.setEmailId("abc@123.com");
        newCustomerDetail.setPassword("123@qaz");

        UUID customerId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");

        Mockito.when(customerService.replaceCustomer(any(Customer.class),any(UUID.class))).thenThrow(new EntityNotFoundException(Customer.class, "id", customerId.toString()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98678")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomerDetail))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("Customer was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());
    }
}