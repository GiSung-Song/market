package study.market.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.market.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
