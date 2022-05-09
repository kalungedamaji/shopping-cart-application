package com.technogise.interns.shoppingcart.placeorder.service;

import com.technogise.interns.shoppingcart.customer.service.CustomerService;
import com.technogise.interns.shoppingcart.dto.*;
import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
import com.technogise.interns.shoppingcart.placeorder.convertor.ListConvertor;
import com.technogise.interns.shoppingcart.cart.service.CartService;
import com.technogise.interns.shoppingcart.enums.OrderStatus;
import com.technogise.interns.shoppingcart.enums.PaymentStatus;
import com.technogise.interns.shoppingcart.enums.PaymentType;
import com.technogise.interns.shoppingcart.orders.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlaceOrderService {
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ListConvertor listConvertor;
    @Autowired
    private OrderService orderService;

    public Order prepareOrderDetails(PaymentType paymentType, List<CartItem> cartItemList){
        Order orderDetails = new Order();
        orderDetails.setOrderPaymentType(paymentType);
        orderDetails.setOrderPaymentStatus(PaymentStatus.COMPLETED);
        orderDetails.setOrderStatus(OrderStatus.COMPLETED);
        List<OrdersOrderItem> orderItemList= listConvertor.cartItemListToOrderItemListConvertor(cartItemList);
        orderDetails.setOrderItems(orderItemList);
        return orderDetails;
    }
    @Transactional
    public Order placeOrder(UUID customerId, PaymentType paymentType) {
        Optional<Customer> customer = Optional.ofNullable(customerService.getCustomerById(customerId));

        if(!customer.isPresent()){
            throw new EntityNotFoundException(Customer.class, "id", customerId.toString());
        }
        else{
            List<CartItem> cartItemList=  cartService.getAllCartItems(customerId);
            if(cartItemList.isEmpty()){
                throw new EntityNotFoundException(CartItem.class, "customerId",customerId.toString());
            }
            else{
                Order orderDetails = prepareOrderDetails(paymentType, cartItemList);

                Order order = orderService.createOrder(orderDetails);
                cartService.deleteAllCartItems(customerId);
                return order;
            }
        }
    }
}
