package com.technogise.interns.shoppingcart.order.service;

import com.technogise.interns.shoppingcart.dto.Order;
import com.technogise.interns.shoppingcart.dto.OrdersOrderItem;
import com.technogise.interns.shoppingcart.enums.PaymentStatus;
import com.technogise.interns.shoppingcart.enums.PaymentType;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import com.technogise.interns.shoppingcart.orders.order.entity.OrderEntity;
import com.technogise.interns.shoppingcart.orders.orderItems.entity.OrdersOrderItemEntity;
import com.technogise.interns.shoppingcart.orders.order.mapper.OrderMapper;
import com.technogise.interns.shoppingcart.orders.order.repository.OrderRepository;
import com.technogise.interns.shoppingcart.orders.order.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderMapper orderMapper;

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

    public OrdersOrderItemEntity getOrdersOrderItemEntity() {
        OrdersOrderItemEntity ordersOrderItemEntity = new OrdersOrderItemEntity();
        ordersOrderItemEntity.setId(UUID.fromString("cf7f42d3-42d1-4727-97dd-4a086ecc0067"));
        ordersOrderItemEntity.setName("dove");
        ordersOrderItemEntity.setPrice(BigDecimal.valueOf(49.99));
        ordersOrderItemEntity.setQuantity(5);
        ordersOrderItemEntity.setImage("Dove Image");
        ordersOrderItemEntity.setDescription("Its a good soap and used by most actress");

        return ordersOrderItemEntity;
    }

    @Test
    public void shouldReturnAllOrders() {
        List<OrderEntity> orderEntityList = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        orderEntity.setTimestamp(Instant.parse("2022-04-07T10:29:35.721Z"));
        orderEntity.setOrderPaymentType(PaymentType.COD);
        orderEntity.setOrderPaymentStatus(PaymentStatus.COMPLETED);

        OrdersOrderItemEntity ordersOrderItemEntity = getOrdersOrderItemEntity();
        List<OrdersOrderItemEntity> ordersOrderItemEntityList = new ArrayList<>();
        ordersOrderItemEntityList.add(ordersOrderItemEntity);
        orderEntity.setOrderItems(ordersOrderItemEntityList);
        orderEntityList.add(orderEntity);

        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        order.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        order.setTimestamp(Instant.parse("2022-04-07T10:29:35.721Z"));
        order.setOrderPaymentType(PaymentType.COD);
        order.setOrderPaymentStatus(PaymentStatus.COMPLETED);

        OrdersOrderItem ordersOrderItem = getOrdersOrderItem();
        List<OrdersOrderItem> ordersOrderItemList = new ArrayList<>();
        ordersOrderItemList.add(ordersOrderItem);
        order.setOrderItems(ordersOrderItemList);
        orderList.add(order);

        Mockito.when(orderRepository.findAll()).thenReturn(orderEntityList);
        Mockito.when(orderMapper.map(any(OrderEntity.class))).thenReturn(order);

        List<Order> actualOrderList = orderService.getAllOrders();
        assertThat(actualOrderList,is(orderList));

    }

    @Test
    public void shouldCreateOrderWhenNewOrderIsCreated() {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        orderEntity.setTimestamp(Instant.parse("2022-04-07T10:29:35.721Z"));
        orderEntity.setOrderPaymentType(PaymentType.COD);
        orderEntity.setOrderPaymentStatus(PaymentStatus.COMPLETED);

        OrdersOrderItemEntity ordersOrderItemEntity = getOrdersOrderItemEntity();
        List<OrdersOrderItemEntity> ordersOrderItemEntityList = new ArrayList<>();
        ordersOrderItemEntityList.add(ordersOrderItemEntity);
        orderEntity.setOrderItems(ordersOrderItemEntityList);

        Order order = new Order();
        order.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        order.setTimestamp(Instant.parse("2022-04-07T10:29:35.721Z"));
        order.setOrderPaymentType(PaymentType.COD);
        order.setOrderPaymentStatus(PaymentStatus.COMPLETED);

        OrdersOrderItem ordersOrderItem = getOrdersOrderItem();
        List<OrdersOrderItem> ordersOrderItemList = new ArrayList<>();
        ordersOrderItemList.add(ordersOrderItem);
        order.setOrderItems(ordersOrderItemList);

        Mockito.when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        Mockito.when(orderMapper.mapToEntity(any(Order.class))).thenReturn(orderEntity);
        Mockito.when(orderMapper.map(any(OrderEntity.class))).thenReturn(order);

        Order actualOrder = orderService.createOrder(order);
        assertThat(actualOrder,is(order));
    }

    @Test
    public void shouldUpdateOrderWhenOrderIsUpdated() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        orderEntity.setTimestamp(Instant.parse("2022-04-07T10:29:35.721Z"));
        orderEntity.setOrderPaymentType(PaymentType.COD);
        orderEntity.setOrderPaymentStatus(PaymentStatus.COMPLETED);

        OrdersOrderItemEntity ordersOrderItemEntity = getOrdersOrderItemEntity();
        List<OrdersOrderItemEntity> ordersOrderItemEntityList = new ArrayList<>();
        ordersOrderItemEntityList.add(ordersOrderItemEntity);
        orderEntity.setOrderItems(ordersOrderItemEntityList);

        Order order = new Order();
        order.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        order.setTimestamp(Instant.parse("2022-04-07T10:29:35.721Z"));
        order.setOrderPaymentType(PaymentType.COD);
        order.setOrderPaymentStatus(PaymentStatus.COMPLETED);

        OrdersOrderItem ordersOrderItem = getOrdersOrderItem();
        List<OrdersOrderItem> ordersOrderItemList = new ArrayList<>();
        ordersOrderItemList.add(ordersOrderItem);
        order.setOrderItems(ordersOrderItemList);

        Mockito.when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.of(orderEntity));
        Mockito.when(orderMapper.mapToEntity(any(Order.class))).thenReturn(orderEntity);
        Mockito.when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        Mockito.when(orderMapper.map(any(OrderEntity.class))).thenReturn(order);

        Optional<Order> actualUpdatedOrder = orderService.replaceOrder(order, UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));

        assertThat(actualUpdatedOrder, is(Optional.of(order)));
    }

    @Test
    public void shouldDeleteOrderWhenOrderIsDeleted() {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        orderEntity.setTimestamp(Instant.parse("2022-04-07T10:29:35.721Z"));
        orderEntity.setOrderPaymentType(PaymentType.COD);
        orderEntity.setOrderPaymentStatus(PaymentStatus.COMPLETED);

        OrdersOrderItemEntity ordersOrderItemEntity = getOrdersOrderItemEntity();
        List<OrdersOrderItemEntity> ordersOrderItemEntityList = new ArrayList<>();
        ordersOrderItemEntityList.add(ordersOrderItemEntity);
        orderEntity.setOrderItems(ordersOrderItemEntityList);

        Mockito.when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.of(orderEntity));
        Mockito.doNothing().when(orderRepository).deleteById(any(UUID.class));

        orderService.deleteOrder(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));

        verify(orderRepository,Mockito.times(1)).deleteById(any(UUID.class));
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenOrderIsNotPresent(){

        UUID orderId = UUID.fromString("43668cf2-6ce4-4238-b32e-dfadafb98678");
        Mockito.when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () ->
            orderService.getOrderById(orderId)
        , "EntityNotFoundException was expected");

        assertThat(thrown.getMessage(), is("Order was not found for parameters {id=43668cf2-6ce4-4238-b32e-dfadafb98678}"));
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenOrderIsNotPresentForReplacement(){

        Order order = new Order();
        order.setId(UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401"));
        order.setTimestamp(Instant.parse("2022-04-07T10:29:35.721Z"));
        order.setOrderPaymentType(PaymentType.COD);
        order.setOrderPaymentStatus(PaymentStatus.COMPLETED);

        OrdersOrderItem ordersOrderItem = getOrdersOrderItem();
        List<OrdersOrderItem> ordersOrderItemList = new ArrayList<>();
        ordersOrderItemList.add(ordersOrderItem);
        order.setOrderItems(ordersOrderItemList);
        Mockito.when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () ->
            orderService.replaceOrder(order,order.getId())
        , "EntityNotFoundException was expected");

        assertThat(thrown.getMessage(), is("Order was not found for parameters {id=a0217f70-7123-45bc-a1b6-f9d392579401}"));
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenOrderIsNotPresentForDeletion(){

        UUID orderId = UUID.fromString("a0217f70-7123-45bc-a1b6-f9d392579401");

        Mockito.when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () ->
            orderService.deleteOrder(orderId)
        , "EntityNotFoundException was expected");

        assertThat(thrown.getMessage(), is("Order was not found for parameters {id=a0217f70-7123-45bc-a1b6-f9d392579401}"));
    }
}
