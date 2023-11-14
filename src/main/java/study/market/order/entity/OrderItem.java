package study.market.order.entity;

import jakarta.persistence.*;
import lombok.Builder;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    private int count;

    private int totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    //order orderItem 연관관계 설정
    public void addOrder(Order order) {
        this.order = order;
    }

    public void addItem(Item item) {
        this.item = item;
    }

    private void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    private void setCount(int count) {
        this.count = count;
    }

    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.addItem(item);
        orderItem.setCount(count);
        orderItem.setTotalPrice(count * item.getPrice());

        item.removeStock(count);

        return orderItem;
    }
}
