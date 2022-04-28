package com.technogise.interns.shoppingcart.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technogise.interns.shoppingcart.CartApplication;
import com.technogise.interns.shoppingcart.dto.Customer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "file:src/test/resources/dbtest/customer/customer-test.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "file:src/test/resources/dbtest/customer/cleanup-customer.sql")
@SpringBootTest(classes = CartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class CustomerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9000;
    }
    @Test
    public void testGetCustomers(){
        RestAssured.get("/customers").then()
                .statusCode(200)
                .assertThat().body("links[0].rel",is("self"))
                .assertThat().body("links[0].href", is("http://localhost:9000/customers"))
                .assertThat().body("links[1].rel",is("product-store"))
                .assertThat().body("links[1].href", is("http://localhost:9000/products"))
                .assertThat().body("content[0].id",is(notNullValue()))
                .assertThat().body("content[0].firstName",is("Pranay"))
                .assertThat().body("content[0].lastName",is("Jain"))
                .assertThat().body("content[0].phoneNumber",is("0999999999"))
                .assertThat().body("content[0].address",is("Indore"))
                .assertThat().body("content[0].emailId",is("abc@123.com"))
                .assertThat().body("content[0].password",is("qaz123"))
                .assertThat().body("content[1].id",is(notNullValue()))
                .assertThat().body("content[1].firstName",is("Parth"))
                .assertThat().body("content[1].lastName",is("Deshmukh"))
                .assertThat().body("content[1].phoneNumber",is("9999999909"))
                .assertThat().body("content[1].address",is("India"))
                .assertThat().body("content[1].emailId",is("abc@1234.com"))
                .assertThat().body("content[1].password",is("abc123"));
    }
    @Test
    public void testAddCustomer() throws JsonProcessingException {

        Customer customer = new Customer();
        customer.setFirstName("Pranay");
        customer.setLastName("Jain");
        customer.setPhoneNumber("0099999999");
        customer.setAddress("Indore");
        customer.setEmailId("abcd@123.com");
        customer.setPassword("qaz123");

        RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and()
                .body(objectMapper.writeValueAsString(customer))
                .when()
                .post("/customers")
                .then()
                .assertThat().statusCode(201)
                .assertThat().body("address",equalTo("Indore"))
                .assertThat().body("firstName",equalTo("Pranay"))
                .assertThat().body("lastName",equalTo("Jain"))
                .assertThat().body("phoneNumber",equalTo("0099999999"))
                .assertThat().body("emailId", equalTo("abcd@123.com"))
                .assertThat().body("password", equalTo("qaz123"))
                .assertThat().body("links[0].rel", is("self"))
                .assertThat().body("links[1].rel", is("all-customers"))
                .assertThat().body("links[1].href", is("http://localhost:9000/customers"))
                .assertThat().body("links[2].rel", is("store"))
                .assertThat().body("links[2].href", is("http://localhost:9000/products"));
    }
    @Test
    public void testUpdateCustomer() throws JsonProcessingException {

        Customer customer = new Customer();
        customer.setFirstName("Pranay");
        customer.setLastName("Jain");
        customer.setPhoneNumber("0099999999");
        customer.setAddress("Indore");
        customer.setEmailId("abcd@123.com");
        customer.setPassword("qaz123");

        RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and()
                .body(objectMapper.writeValueAsString(customer))
                .when()
                .put("/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("address",equalTo("Indore"))
                .assertThat().body("firstName",equalTo("Pranay"))
                .assertThat().body("lastName",equalTo("Jain"))
                .assertThat().body("phoneNumber",equalTo("0099999999"))
                .assertThat().body("emailId", equalTo("abcd@123.com"))
                .assertThat().body("password", equalTo("qaz123"));
    }
    @Test
    public void testDeleteCustomer()  {
        RestAssured.delete("/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c").then()
                .statusCode(200);
    }
}
