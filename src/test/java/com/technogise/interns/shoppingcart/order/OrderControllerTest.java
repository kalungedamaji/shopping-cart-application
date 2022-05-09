package com.technogise.interns.shoppingcart.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.dto.Order;
import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import com.technogise.interns.shoppingcart.enums.PaymentStatus;
import com.technogise.interns.shoppingcart.enums.PaymentType;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import com.technogise.interns.shoppingcart.orders.order.OrderController;
import com.technogise.interns.shoppingcart.orders.order.service.OrderService;
import org.hibernate.exception.ConstraintViolationException;
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
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

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
                .andExpect(jsonPath("content").isEmpty());
    }

    @Test
    public void shouldReturnOrderWhenSingleOrderItemIsAdded() throws Exception {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        order.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        order.setTimestamp(Instant.parse("2022-04-07T10:29:35.721Z"));
        order.setOrderPaymentType(PaymentType.UPI);
        order.setOrderPaymentStatus(PaymentStatus.COMPLETED);

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
                .andExpect(jsonPath("content[0].timestamp", is(order.getTimestamp().toString())))
                .andExpect(jsonPath("content[0].orderPaymentType", is(order.getOrderPaymentType().toString())))
                .andExpect(jsonPath("content[0].orderPaymentStatus", is(order.getOrderPaymentStatus().toString())))
                .andExpect(jsonPath("content[0].orderItems[0].name", is(ordersOrderItem.getName())))
                .andExpect(jsonPath("$.content[0].orderItems[0].price").value(ordersOrderItem.getPrice()))
                .andExpect(jsonPath("content[0].orderItems[0]quantity", is(ordersOrderItem.getQuantity())))
                .andExpect(jsonPath("content[0].orderItems[0].image", is(ordersOrderItem.getImage())))
                .andExpect(jsonPath("content[0].orderItems[0].description", is(ordersOrderItem.getDescription())))
                .andExpect(jsonPath("links[0].rel", is("self")))
                .andExpect(jsonPath("links[0].href", is("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98679/orders")))
                .andExpect(jsonPath("links[1].rel", is("product-store")))
                .andExpect(jsonPath("links[1].href", is("http://localhost:9000/products")));
    }

    @Test
    public void shouldCreateOrderAndReturnCreatedOrder() throws Exception {
        Order orderData = new Order();
        orderData.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        orderData.setTimestamp(Instant.parse("2022-04-08T11:31:20.846Z"));
        orderData.setOrderPaymentType(PaymentType.UPI);
        orderData.setOrderPaymentStatus(PaymentStatus.COMPLETED);

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
        expectedOrderData.setOrderPaymentType(PaymentType.UPI);
        expectedOrderData.setOrderPaymentStatus(PaymentStatus.COMPLETED);

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
                        .andExpect(jsonPath("$.orderPaymentStatus").value(expectedOrderData.getOrderPaymentStatus().toString()))
                        .andExpect(jsonPath("$.orderPaymentType").value(expectedOrderData.getOrderPaymentType().toString()))
                        .andExpect(jsonPath("$.orderItems[0].id").isNotEmpty())
                        .andExpect(jsonPath("$.orderItems[0].image").value(expectedOrderItem.getImage()))
                        .andExpect(jsonPath("$.orderItems[0].name").value(expectedOrderItem.getName()))
                        .andExpect(jsonPath("$.orderItems[0].description").value(expectedOrderItem.getDescription()))
                        .andExpect(jsonPath("$.orderItems[0].quantity").value(expectedOrderItem.getQuantity()))
                        .andExpect(jsonPath("$.orderItems[0].price").value(expectedOrderItem.getPrice()))
                        .andExpect(jsonPath("$.links[0].rel").value("all-orders"))
                        .andExpect(jsonPath("$.links[0].href").value("http://localhost:9000/customers/b2ac79f2-c4ed-409d-9eb6-5d9fc1890bc7/orders"))
                        .andExpect(jsonPath("$.links[1].rel").value("self"))
                        .andExpect(jsonPath("$.links[1].href").value("http://localhost:9000/customers/b2ac79f2-c4ed-409d-9eb6-5d9fc1890bc7/orders/a0217f70-7123-45bc-a1b6-f9d392579401"));
    }

    @Test
    public void shouldUpdateOrderWhenOrderIsUpdated() throws Exception {

        Order existingOrder = new Order();
        existingOrder.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        existingOrder.setTimestamp(Instant.parse("2022-04-08T11:31:20.846Z"));
        existingOrder.setOrderPaymentType(PaymentType.UPI);
        existingOrder.setOrderPaymentStatus(PaymentStatus.COMPLETED);

        OrdersOrderItem existingOrderItem = new OrdersOrderItem();
        existingOrderItem.setImage("mug image");
        existingOrderItem.setName("mug");
        existingOrderItem.setDescription("A mug to be sold");
        existingOrderItem.setQuantity(2);
        existingOrderItem.setPrice(BigDecimal.TEN);
        existingOrderItem.setId(UUID.fromString("a0217f70-7123-45bc-a1b3-f9d392579401"));

        Order newOrder = new Order();
        newOrder.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        newOrder.setTimestamp(Instant.parse("2022-04-08T11:31:20.846Z"));
        newOrder.setOrderPaymentType(PaymentType.UPI);
        newOrder.setOrderPaymentStatus(PaymentStatus.COMPLETED);

        List<OrdersOrderItem> ordersOrderItemList = new ArrayList<>();
        OrdersOrderItem newOrderItem = new OrdersOrderItem();
        newOrderItem.setImage("mug image");
        newOrderItem.setName("mug");
        newOrderItem.setDescription("A mug to be sold");
        newOrderItem.setQuantity(2);
        newOrderItem.setPrice(BigDecimal.TEN);
        newOrderItem.setId(UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678"));
        ordersOrderItemList.add(newOrderItem);

        newOrder.setOrderItems(ordersOrderItemList);

        Mockito.when(orderService.replaceOrder(any(Order.class), any(UUID.class))).thenReturn(Optional.of(newOrder));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98679/orders/a0217f70-7123-45bc-a1b6-f9d392579401")
                .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newOrder))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.orderPaymentStatus").value(newOrder.getOrderPaymentStatus().toString()))
                .andExpect(jsonPath("$.orderPaymentType").value(newOrder.getOrderPaymentType().toString()))
                .andExpect(jsonPath("$.orderItems[0].id").isNotEmpty())
                .andExpect(jsonPath("$.orderItems[0].image").value(newOrderItem.getImage()))
                .andExpect(jsonPath("$.orderItems[0].name").value(newOrderItem.getName()))
                .andExpect(jsonPath("$.orderItems[0].description").value(newOrderItem.getDescription()))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(newOrderItem.getQuantity()))
                .andExpect(jsonPath("$.orderItems[0].price").value(newOrderItem.getPrice()))
                .andExpect(jsonPath("$.links[0].rel").value("all-orders"))
                .andExpect(jsonPath("$.links[0].href").value("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98679/orders"))
                .andExpect(jsonPath("$.links[1].rel").value("self"))
                .andExpect(jsonPath("$.links[1].href").value("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98679/orders/a0217f70-7123-45bc-a1b6-f9d392579401"));


    }

    @Test
    public void shouldDeleteOrderWhenOrderIsDeleted() throws Exception {

        Order order = new Order();
        order.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        order.setTimestamp(Instant.parse("2022-04-07T10:29:35.721Z"));
        order.setOrderPaymentType(PaymentType.UPI);
        order.setOrderPaymentStatus(PaymentStatus.COMPLETED);

        OrdersOrderItem ordersOrderItem = getOrdersOrderItem();
        List<OrdersOrderItem> ordersOrderItemList = new ArrayList<>();
        ordersOrderItemList.add(ordersOrderItem);
        order.setOrderItems(ordersOrderItemList);

        Mockito.doNothing().when(orderService).deleteOrder(any(UUID.class));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("http://localhost:9000/customers/b2ac79f2-c4ed-409d-9eb6-5d9fc1890bc7/orders/a0217f70-7123-45bc-a1b6-f9d392579401")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder);

        verify(orderService,Mockito.times(1)).deleteOrder(any(UUID.class));

    }

    @Test
    public void shouldReturnNotFoundErrorWhenCustomerIsNotPresent() throws Exception {

        UUID orderId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");


        Mockito.when(orderService.getOrderById(any(UUID.class))).thenThrow(new EntityNotFoundException(Order.class, "id", orderId.toString()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98679/orders/43668cf2-6ce4-4238-b32e-dfadafb98678")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("Order was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());
    }

    @Test
    public void shouldReturnNotFoundErrorWhenCustomerIsNotPresentForReplacement() throws Exception {

        Order orderData = new Order();
        orderData.setId(UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678"));
        orderData.setTimestamp(Instant.parse("2022-04-08T11:31:20.846Z"));
        orderData.setOrderPaymentType(PaymentType.UPI);
        orderData.setOrderPaymentStatus(PaymentStatus.COMPLETED);

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

        Mockito.when(orderService.replaceOrder(any(Order.class), any(UUID.class))).thenThrow(new EntityNotFoundException(Order.class, "id", orderData.getId().toString()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98679/orders/43668cf2-6ce4-4238-b32e-dfadafb98678")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderData))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("Order was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());
    }

    @Test
    public void shouldReturnNotFoundErrorWhenCustomerIsNotPresentForDeletion() throws Exception {

        Order orderData = new Order();
        orderData.setId(UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678"));
        orderData.setTimestamp(Instant.parse("2022-04-08T11:31:20.846Z"));
        orderData.setOrderPaymentType(PaymentType.UPI);
        orderData.setOrderPaymentStatus(PaymentStatus.COMPLETED);

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

        Mockito.doThrow(new EntityNotFoundException(Order.class, "id", orderData.getId().toString())).when(orderService).deleteOrder(any(UUID.class));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98679/orders/43668cf2-6ce4-4238-b32e-dfadafb98678")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message", is("Order was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());
    }

    @Test
    public void shouldReturnUnprocessableEntityWhenThereIsConstraintViolation() throws Exception {

        Order order = new Order();
        order.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        order.setTimestamp(Instant.parse("2022-04-07T10:29:35.721Z"));
        order.setOrderPaymentType(PaymentType.UPI);
        order.setOrderPaymentStatus(PaymentStatus.COMPLETED);

        OrdersOrderItem ordersOrderItem = getOrdersOrderItem();
        List<OrdersOrderItem> ordersOrderItemList = new ArrayList<>();
        ordersOrderItemList.add(ordersOrderItem);
        order.setOrderItems(ordersOrderItemList);

        Mockito.when(orderService.createOrder(any(Order.class))).thenThrow(new ConstraintViolationException("could not execute statement", new SQLException("ERROR: order date value cannot be null - violates null constraint") , "order_date"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98679/orders")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status", is("UNPROCESSABLE_ENTITY")))
                .andExpect(jsonPath("$.timestamp", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))))
                .andExpect(jsonPath("$.message", is("could not execute statement")))
                .andExpect(jsonPath("$.debugMessage[0]", is("ERROR: order date value cannot be null - violates null constraint")));
    }
}

