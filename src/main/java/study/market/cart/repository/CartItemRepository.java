package study.market.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.market.cart.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
