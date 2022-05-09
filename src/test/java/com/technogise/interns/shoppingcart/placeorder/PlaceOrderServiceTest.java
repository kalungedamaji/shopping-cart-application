package com.technogise.interns.shoppingcart.placeorder;

import com.technogise.interns.shoppingcart.cart.service.CartService;
import com.technogise.interns.shoppingcart.customer.service.CustomerService;
import com.technogise.interns.shoppingcart.dto.CartItem;
import com.technogise.interns.shoppingcart.dto.Customer;
import com.technogise.interns.shoppingcart.dto.Order;
import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import com.technogise.interns.shoppingcart.enums.OrderStatus;
import com.technogise.interns.shoppingcart.enums.PaymentStatus;
import com.technogise.interns.shoppingcart.enums.PaymentType;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import com.technogise.interns.shoppingcart.orders.order.service.OrderService;
import com.technogise.interns.shoppingcart.placeorder.convertor.ListConvertor;
import com.technogise.interns.shoppingcart.placeorder.service.PlaceOrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@WebMvcTest(value= PlaceOrderService.class)

public class PlaceOrderServiceTest {
    @Autowired
    private PlaceOrderService placeOrderService;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private ListConvertor listConvertor;
    @MockBean
    private CartService cartService;
    @MockBean
    private OrderService orderService;

    @Test
    public void shouldPlaceOrder(){

        Customer customer = new Customer();
        customer.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        customer.setFirstName("pranay");
        customer.setLastName("jain");
        customer.setAddress("indore");
        customer.setPhoneNumber("9999999999");
        customer.setEmailId("sdfg@dsfg.com");
        customer.setPassword("^asd12");

        List<CartItem> cartItemList = new ArrayList<>();
        CartItem newCartItem = new CartItem();
        newCartItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        newCartItem.setQuantity(5);
        newCartItem.setPrice(BigDecimal.valueOf(10.00));
        newCartItem.setImage("image");
        newCartItem.setName("Dove");
        cartItemList.add(newCartItem);

        Order orderDetail = new Order();
        List<OrdersOrderItem> orderItemList = new ArrayList<>();
        OrdersOrderItem orderItem = new OrdersOrderItem();
        orderItem.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        orderItem.setQuantity(5);
        orderItem.setPrice(BigDecimal.valueOf(10.00));
        orderItem.setImage("image");
        orderItem.setName("Dove");
        orderItemList.add(orderItem);
        orderDetail.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"));
        orderDetail.setOrderStatus(OrderStatus.COMPLETED);
        orderDetail.setOrderPaymentStatus(PaymentStatus.COMPLETED);
        orderDetail.setOrderPaymentType(PaymentType.COD);
        orderDetail.setTimestamp(Instant.now());
        orderDetail.setOrderItems(orderItemList);

        Mockito.when(customerService.getCustomerById(customer.getId())).thenReturn(customer);
        Mockito.when(cartService.getAllCartItems(customer.getId())).thenReturn(cartItemList);
        Mockito.when(listConvertor.cartItemListToOrderItemListConvertor(cartItemList)).thenReturn(orderItemList);
        Mockito.when(orderService.createOrder(any(Order.class))).thenReturn(orderDetail);
        Mockito.doNothing().when(cartService).deleteAllCartItems(customer.getId());

        Order placedOrder = placeOrderService.placeOrder(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0060"),PaymentType.COD);

        Order expectedOrder = new Order();
        expectedOrder.setOrderItems(orderItemList);
        expectedOrder.setOrderStatus(OrderStatus.COMPLETED);
        expectedOrder.setOrderPaymentStatus(PaymentStatus.COMPLETED);
        expectedOrder.setOrderPaymentType(PaymentType.COD);

        Assertions.assertNotNull(placedOrder);
        verify(cartService,Mockito.times(1)).getAllCartItems(customer.getId());
        assertThat(placedOrder.getOrderStatus(),is(expectedOrder.getOrderStatus()));
        assertThat(placedOrder.getOrderPaymentStatus(),is(expectedOrder.getOrderPaymentStatus()));
        assertThat(placedOrder.getOrderPaymentType(),is(expectedOrder.getOrderPaymentType()));
        assertThat(placedOrder.getOrderItems(),is(expectedOrder.getOrderItems()));
        verify(cartService,Mockito.times(1)).deleteAllCartItems(customer.getId());
    }

    @Test
    public void shouldReturnCustomerNotFoundExceptionWhenCustomerIdIsInvalidAndOrderIsPlaced(){
        UUID customerId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");
        PaymentType paymentType = PaymentType.UPI;
        Mockito.when(customerService.getCustomerById(customerId)).thenThrow(new EntityNotFoundException(Customer.class, "id", customerId.toString()));

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> placeOrderService.placeOrder(customerId, paymentType), "EntityNotFoundException was expected");
        assertThat(thrown.getMessage(), is("Customer was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}"));

    }
    @Test
    public void shouldReturnCartItemNotFoundExceptionWhenCartItemIsNotPresentForGivenCustomerAndOrderIsPlaced(){
        UUID customerId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");
        PaymentType paymentType = PaymentType.UPI;
        Mockito.when(customerService.getCustomerById(customerId)).thenThrow(new EntityNotFoundException(CartItem.class, "customerId", customerId.toString()));

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> placeOrderService.placeOrder(customerId, paymentType), "EntityNotFoundException was expected");
        assertThat(thrown.getMessage(), is("CartItem was not found for parameters {customerId=43668cf2-6ce4-4238-b32e-dfadafb98678}"));

    }
}
