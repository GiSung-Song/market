package study.market.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.market.order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
