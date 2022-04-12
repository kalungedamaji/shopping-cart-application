package com.technogise.interns.shoppingcart.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technogise.interns.shoppingcart.dto.Order;
import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import com.technogise.interns.shoppingcart.orders.order.OrderController;
import com.technogise.interns.shoppingcart.orders.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public OrdersOrderItem getOrdersOrderItem() {
        OrdersOrderItem ordersOrderItem = new OrdersOrderItem();
        ordersOrderItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0067"));
        ordersOrderItem.setName("dove");
        ordersOrderItem.setPrice(BigDecimal.valueOf(49.99));
        ordersOrderItem.setQuantity(5);
        ordersOrderItem.setImage("Dove Image");
        ordersOrderItem.setDescription("Its a good soap and used by most actress");

        return ordersOrderItem;
    }

    @Test
    public void shouldReturnEmptyOrderWhenNoOrderItemIsAdded() throws Exception{
        List<Order> orderList = new ArrayList<>();
        Mockito.when(orderService.getAllOrders()).thenReturn(orderList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98679/orders")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    public void shouldReturnOrderWhenSingleOrderItemIsAdded() throws Exception {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        order.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        order.setTimestamp(Instant.parse("2022-04-07T10:29:35.721Z"));
        order.setOrderPaymentType("cash");
        order.setOrderPaymentStatus("done");

        OrdersOrderItem ordersOrderItem = getOrdersOrderItem();
        List<OrdersOrderItem> ordersOrderItemList = new ArrayList<>();
        ordersOrderItemList.add(ordersOrderItem);
        order.setOrderItems(ordersOrderItemList);
        orderList.add(order);

        Mockito.when(orderService.getAllOrders()).thenReturn(orderList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98679/orders")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].timestamp").value(order.getTimestamp().toString()))
                .andExpect(jsonPath("$.content[0].orderPaymentType").value(order.getOrderPaymentType()))
                .andExpect(jsonPath("$.content[0].orderPaymentStatus").value(order.getOrderPaymentStatus()))
                .andExpect(jsonPath("$.content[0].orderItems[0].name").value(ordersOrderItem.getName()))
                .andExpect(jsonPath("$.content[0].orderItems[0].price").value(ordersOrderItem.getPrice()))
                .andExpect(jsonPath("$.content[0].orderItems[0]quantity").value(ordersOrderItem.getQuantity()))
                .andExpect(jsonPath("$.content[0].orderItems[0].image").value(ordersOrderItem.getImage()))
                .andExpect(jsonPath("$.content[0].orderItems[0].description").value(ordersOrderItem.getDescription()))
                .andExpect(jsonPath("$.links[0].rel").value("self"))
                .andExpect(jsonPath("$.links[0].href").value("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98679/orders"))
                .andExpect(jsonPath("$.links[1].rel").value("product-store"))
                .andExpect(jsonPath("$.links[1].href").value("http://localhost:9000/products"));
    }

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