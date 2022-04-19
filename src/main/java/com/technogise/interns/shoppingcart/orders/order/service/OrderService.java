package com.technogise.interns.shoppingcart.orders.order.service;

        import com.technogise.interns.shoppingcart.dto.Order;
        import com.technogise.interns.shoppingcart.error.EntityNotFoundException;
        import com.technogise.interns.shoppingcart.orders.order.entity.OrderEntity;
        import com.technogise.interns.shoppingcart.orders.order.mapper.OrderMapper;
        import com.technogise.interns.shoppingcart.orders.order.repository.OrderRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.time.Instant;
        import java.util.List;
        import java.util.Optional;
        import java.util.UUID;
        import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;


    public List<Order> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(orderMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<Order> getOrderById(UUID orderId) {
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderId);
        if(optionalOrderEntity.isPresent()){
            return optionalOrderEntity.map(orderMapper::map);
        }
        else {
            throw new EntityNotFoundException(Order.class, "id", orderId.toString());
        }
    }

    public Order createOrder(Order order) {
        order.setId(UUID.randomUUID());
        order.setTimestamp(Instant.now());
        OrderEntity orderEntity = orderRepository.save(orderMapper.mapToEntity(order));
        return orderMapper.map(orderEntity);
    }

    public Optional<Order> replaceOrder(Order orderDetails, UUID orderId) {
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderId);
        if(optionalOrderEntity.isPresent()) {
            orderDetails.setId(orderId);
            OrderEntity orderEntity = orderRepository.save(orderMapper.mapToEntity(orderDetails));

            return Optional.of(orderMapper.map(orderEntity));
        }
        else{
            throw new EntityNotFoundException(Order.class, "id", orderId.toString());
        }
    }


    public void deleteOrder(UUID orderId) {
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderId);
        if(optionalOrderEntity.isPresent()){
             orderRepository.deleteById(orderId);
        }
        else {
            throw new EntityNotFoundException(Order.class, "id", orderId.toString());
        }
    }
}

