package study.market.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.market.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);

}
