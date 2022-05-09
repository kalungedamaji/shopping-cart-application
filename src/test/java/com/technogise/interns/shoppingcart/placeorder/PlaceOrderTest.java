package com.technogise.interns.shoppingcart.placeorder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technogise.interns.shoppingcart.CartApplication;
import com.technogise.interns.shoppingcart.dto.PayOrderDetail;
import com.technogise.interns.shoppingcart.enums.PaymentType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)


@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "file:src/test/resources/dbtest/placeorder/place-order-test.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "file:src/test/resources/dbtest/placeorder/cleanup-place-order.sql")

public class PlaceOrderTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldPlaceOrder() throws JsonProcessingException {
        PayOrderDetail payOrderDetail = new PayOrderDetail();
        payOrderDetail.setPaymentType(PaymentType.UPI);

        RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON).and()
                .body(objectMapper.writeValueAsString(payOrderDetail)).when()
                .post("http://localhost:9000/customers/edb9b593-757e-4bf1-82a2-6d73495f1020/pay").then()
                .assertThat().statusCode(201)
                .assertThat().body("id", notNullValue())
                .assertThat().body("orderPaymentType",equalTo("UPI"))
                .assertThat().body("orderPaymentStatus",equalTo("COMPLETED"))
                .assertThat().body("orderStatus",equalTo("COMPLETED"))
                .assertThat().body("orderItems[0].id",notNullValue())
                .assertThat().body("orderItems[0].name",equalTo("Mug"))
                .assertThat().body("orderItems[0].image",equalTo("image"))
                .assertThat().body("orderItems[0].quantity",equalTo(5))
                .assertThat().body("orderItems[0].price",equalTo(10.0f));

    }

}
