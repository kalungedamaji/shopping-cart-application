package com.technogise.interns.shoppingcart.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.technogise.interns.shoppingcart.dto.Order;
import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import com.technogise.interns.shoppingcart.orders.order.OrderController;
import com.technogise.interns.shoppingcart.orders.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value= OrderController.class)
public class OrderControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void shouldCreateOrderAndReturnCreatedOrder() throws Exception {
        Order orderData = new Order();
        orderData.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        orderData.setTimestamp(Instant.parse("2022-04-08T11:31:20.846Z"));
        orderData.setOrderPaymentType("Cash");
        orderData.setOrderPaymentStatus("Done");

        OrdersOrderItem orderItem = new OrdersOrderItem();
        orderItem.setImage("mug image");
        orderItem.setName("mug");
        orderItem.setDescription("A mug to be sold");
        orderItem.setQuantity(2);
        orderItem.setPrice(BigDecimal.TEN);
        orderItem.setId(UUID.fromString("a0217f70-7123-45bc-a1b3-f9d392579401"));

        List<OrdersOrderItem> ordersOrderItemList = new ArrayList<>();
        ordersOrderItemList.add(orderItem);
        orderData.setOrderItems(ordersOrderItemList);

        Mockito.when(orderService.createOrder(Mockito.any(Order.class))).thenReturn(orderData);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("http://localhost:9000/customers/b2ac79f2-c4ed-409d-9eb6-5d9fc1890bc7/orders")
                .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderData))
                .contentType(MediaType.APPLICATION_JSON);

        Order expectedOrderData = new Order();
        expectedOrderData.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        expectedOrderData.setTimestamp(Instant.parse("2022-04-08T11:31:20.846Z"));
        expectedOrderData.setOrderPaymentType("Cash");
        expectedOrderData.setOrderPaymentStatus("Done");

        OrdersOrderItem expectedOrderItem = new OrdersOrderItem();
        expectedOrderItem.setImage("mug image");
        expectedOrderItem.setName("mug");
        expectedOrderItem.setDescription("A mug to be sold");
        expectedOrderItem.setQuantity(2);
        expectedOrderItem.setPrice(BigDecimal.TEN);
        expectedOrderItem.setId(UUID.fromString("a0217f70-7123-45bc-a1b3-f9d392579401"));

        List<OrdersOrderItem> expectedOrdersOrderItemList = new ArrayList<>();
        expectedOrdersOrderItemList.add(expectedOrderItem);
        expectedOrderData.setOrderItems(expectedOrdersOrderItemList);

        mockMvc.perform(requestBuilder)
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").isNotEmpty())
                        .andExpect(jsonPath("$.timestamp").isNotEmpty())
                        .andExpect(jsonPath("$.orderPaymentStatus").value(expectedOrderData.getOrderPaymentStatus()))
                        .andExpect(jsonPath("$.orderPaymentType").value(expectedOrderData.getOrderPaymentType()))
                        .andExpect(jsonPath("$.orderItems[0].id").isNotEmpty())
                        .andExpect(jsonPath("$.orderItems[0].image").value(expectedOrderItem.getImage()))
                        .andExpect(jsonPath("$.orderItems[0].name").value(expectedOrderItem.getName()))
                        .andExpect(jsonPath("$.orderItems[0].description").value(expectedOrderItem.getDescription()))
                        .andExpect(jsonPath("$.orderItems[0].quantity").value(expectedOrderItem.getQuantity()))
                        .andExpect(jsonPath("$.orderItems[0].price").value(expectedOrderItem.getPrice()));
    }
}


//                String mockOrderJson = "{\"orderPaymentType\":\"Cash\",\"orderPaymentStatus\":\"Done\"}";
//        orderData.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
//        orderData.setTimestamp(Instant.parse("2022-04-08T11:31:20.846Z"));


//        String expectedOrder ="{\"id\":\"a0217f70-7123-45bc-a1b6-f9d392579401\",\"timestamp\":\"2022-04-08T11:31:20.846Z\",\"orderPaymentType\":\"Cash\",\"orderPaymentStatus\":\"Done\",\"orderItems\":[{\"id\":\"a0217f70-7123-45bc-a1b3-f9d392579401\",\"name\":\"mug\",\"image\":\"mug image\",\"description\":\"A mug to be sold\",\"quantity\":2,\"price\":10}],\"links\":[{\"rel\":\"all-orders\",\"href\":\"http://localhost:9000/customers/{customerId}/orders\"},{\"rel\":\"self\",\"href\":\"http://localhost:9000/customers/{customerId}/orders/a0217f70-7123-45bc-a1b6-f9d392579401\"}]}";
//        MockHttpServletResponse response = result.getResponse();
//        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//        JSONAssert.assertEquals(expectedOrder, result.getResponse()
//                .getContentAsString(), false);