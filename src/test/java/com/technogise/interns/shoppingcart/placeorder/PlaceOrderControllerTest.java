package com.technogise.interns.shoppingcart.placeorder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technogise.interns.shoppingcart.dto.*;
import com.technogise.interns.shoppingcart.enums.OrderStatus;
import com.technogise.interns.shoppingcart.enums.PaymentStatus;
import com.technogise.interns.shoppingcart.enums.PaymentType;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import com.technogise.interns.shoppingcart.orders.order.OrderController;
import com.technogise.interns.shoppingcart.placeorder.controller.PlaceOrderController;
import com.technogise.interns.shoppingcart.placeorder.placeorderrepresentation.LinkGenerator;
import com.technogise.interns.shoppingcart.placeorder.service.PlaceOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value= PlaceOrderController.class)

public class PlaceOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PlaceOrderService placeOrderService;
    @MockBean
    private LinkGenerator linkGenerator;
    @Test
    public void shouldPlaceOrder() throws Exception {
        UUID customerId = UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c");

        PayOrderDetail payOrderDetail = new PayOrderDetail();
        payOrderDetail.setPaymentType(PaymentType.UPI);
        Order order = new Order();
        order.setId(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"));
        order.setOrderPaymentType(payOrderDetail.getPaymentType());
        order.setOrderPaymentStatus(PaymentStatus.COMPLETED);
        order.setOrderStatus(OrderStatus.COMPLETED);

        List<OrdersOrderItem> orderItemList = new ArrayList<>();
        OrdersOrderItem orderItem = new OrdersOrderItem();
        orderItem.setId(UUID.fromString("62ecbdf5-4107-4d04-980b-d20323d2cd6c"));
        orderItem.setName("Deo");
        orderItem.setImage("image");
        orderItem.setQuantity(5);
        orderItem.setPrice(BigDecimal.valueOf(10.0));
        orderItem.setDescription("this is Deo");
        orderItemList.add(orderItem);

        order.setOrderItems(orderItemList);

        EntityModel<Order> orderEntityModel = EntityModel.of(order);
        orderEntityModel.add(linkTo(methodOn(OrderController.class).getAllOrders(customerId)).withRel("all-orders"));

        Mockito.when(placeOrderService.placeOrder(customerId,payOrderDetail)).thenReturn(order);
        //Mockito.when(placeOrderLinks.prepareLink(customerId))
                //.thenReturn(linkTo(methodOn(OrderController.class).getAllOrders(customerId)).withRel("all-orders"));
        doReturn(orderEntityModel).when(linkGenerator).addLinks(order,customerId);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("http://localhost:9000/customers/62ecbdf5-4107-4d04-980b-d20323d2cd6c/pay")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payOrderDetail))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.orderPaymentType").value("UPI"))
                .andExpect(jsonPath("$.orderPaymentStatus").value("COMPLETED"))
                .andExpect(jsonPath("$.orderStatus").value("COMPLETED"))
                .andExpect(jsonPath("$.orderItems[0].id").isNotEmpty())
                .andExpect(jsonPath("$.orderItems[0].name").value("Deo"))
                .andExpect(jsonPath("$.orderItems[0].image").value("image"))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(5))
                .andExpect(jsonPath("$.orderItems[0].price").value(10.0f));
    }
    @Test
    public void shouldThrowNotFoundExceptionWhenCustomerIsNotPresent() throws Exception{
        UUID customerId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");
        PayOrderDetail payOrderDetail = new PayOrderDetail();
        payOrderDetail.setPaymentType(PaymentType.UPI);
        Mockito.when(placeOrderService.placeOrder(customerId, payOrderDetail)).thenThrow(new EntityNotFoundException(Customer.class, "id", customerId.toString()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98678/pay")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payOrderDetail))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("Customer was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());
    }
    @Test
    public void shouldThrowNotFoundExceptionWhenCartItemIsNotPresentForGivenCustomer() throws Exception{
        UUID customerId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");
        PayOrderDetail payOrderDetail = new PayOrderDetail();
        payOrderDetail.setPaymentType(PaymentType.UPI);
        Mockito.when(placeOrderService.placeOrder(customerId,payOrderDetail )).thenThrow(new EntityNotFoundException(CartItem.class, "customerId", customerId.toString()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("http://localhost:9000/customers/43668cf2-6ce4-4238-b32e-dfadafb98678/pay")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payOrderDetail))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.timestamp").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))))
                .andExpect(jsonPath("$.message", is("CartItem was not found for parameters {customerId=43668cf2-6ce4-4238-b32e-dfadafb98678}")))
                .andExpect(jsonPath("$.debugMessage").isEmpty());
    }
}
