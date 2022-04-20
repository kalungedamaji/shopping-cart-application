package com.technogise.interns.shoppingcart.customer.hateosLinksProvider;

import com.technogise.interns.shoppingcart.customer.CustomerController;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.store.ProductController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest
public class CustomerHateosLinksProviderTest {
    @Autowired
    private CustomerHateosLinksProvider customerHateosLinksProvider;
    @MockBean
    private CustomerLinks customerLinks;

    @Test
    public void shouldReturnEntityModelOfCustomerAfterAddingLinks(){
        Customer customer = new Customer();
        customer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        customer.setFirstName("Pranay");
        customer.setLastName("Jain");
        customer.setPhoneNumber("9999999999");
        customer.setAddress("Indore");
        customer.setEmailId("abc@xyz.com");
        customer.setPassword("123abcd@");

        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        expectedCustomer.setFirstName("Pranay");
        expectedCustomer.setLastName("Jain");
        expectedCustomer.setPhoneNumber("9999999999");
        expectedCustomer.setAddress("Indore");
        expectedCustomer.setEmailId("abc@xyz.com");
        expectedCustomer.setPassword("123abcd@");
        EntityModel<Customer> expectedCustomerEntityModel = EntityModel.of(expectedCustomer);
        expectedCustomerEntityModel.add(linkTo(methodOn(CustomerController.class).getCustomer(expectedCustomer.getId())).withSelfRel());
        expectedCustomerEntityModel.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"));
        expectedCustomerEntityModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("store"));

        Mockito.when(customerLinks.linkToSelf(customer)).thenReturn(linkTo(methodOn(CustomerController.class).getCustomer(customer.getId())));
        Mockito.when(customerLinks.linkToAllCustomers()).thenReturn(linkTo(methodOn(CustomerController.class).getAllCustomers()));
        Mockito.when(customerLinks.linkToStore()).thenReturn(linkTo(methodOn(ProductController.class).getAllProducts()));

        EntityModel<Customer> actualEntityModelOfCustomer = customerHateosLinksProvider.getForPost(customer);

        assertThat(actualEntityModelOfCustomer, is(expectedCustomerEntityModel));
    }
    @Test
    public void shouldReturnEntityModelOfUpdatedCustomerAfterAddingLinks(){
        Customer replacedCustomer = new Customer();
        replacedCustomer.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        replacedCustomer.setFirstName("Pranay");
        replacedCustomer.setLastName("Jain");
        replacedCustomer.setPhoneNumber("9999999999");
        replacedCustomer.setAddress("Indore");
        replacedCustomer.setEmailId("abc@xyz.com");
        replacedCustomer.setPassword("123abcd@");

        Customer expectedCustomerAfterReplacement = new Customer();
        expectedCustomerAfterReplacement.setId(UUID.fromString("676ea10c-537b-4861-b27b-f3b8cbc0dc36"));
        expectedCustomerAfterReplacement.setFirstName("Pranay");
        expectedCustomerAfterReplacement.setLastName("Jain");
        expectedCustomerAfterReplacement.setPhoneNumber("9999999999");
        expectedCustomerAfterReplacement.setAddress("Indore");
        expectedCustomerAfterReplacement.setEmailId("abc@xyz.com");
        expectedCustomerAfterReplacement.setPassword("123abcd@");
        EntityModel<Customer> expectedCustomerEntityModel = EntityModel.of(expectedCustomerAfterReplacement);
        expectedCustomerEntityModel.add(linkTo(methodOn(CustomerController.class).getCustomer(expectedCustomerAfterReplacement.getId())).withSelfRel());
        expectedCustomerEntityModel.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"));

        Mockito.when(customerLinks.linkToSelf(replacedCustomer)).thenReturn(linkTo(methodOn(CustomerController.class).getCustomer(replacedCustomer.getId())));
        Mockito.when(customerLinks.linkToAllCustomers()).thenReturn(linkTo(methodOn(CustomerController.class).getAllCustomers()));

        EntityModel<Customer> actualEntityModelOfReplacedCustomer = customerHateosLinksProvider.getForPut(replacedCustomer);

        assertThat(actualEntityModelOfReplacedCustomer, is(expectedCustomerEntityModel));

    }
}
