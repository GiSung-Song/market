package study.market.delievery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.market.delievery.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Delivery findByOrderId(Long orderId);
}
