package study.market.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.market.item.entity.Item;

import java.util.List;

/**
 * id : 주문상품 id
 * order : 주문
 * count : 주문 개수
 * totalPrice : 총금액
 * item : 상품
 */

@Getter
@NoArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int count;

    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

}
