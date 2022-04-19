package com.technogise.interns.shoppingcart.orders.order.repository;

        import com.technogise.interns.shoppingcart.orders.order.entity.OrderEntity;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

        import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}

