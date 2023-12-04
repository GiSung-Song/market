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
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int count;

    private int itemTotalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //order orderItem 연관관계 설정
    public void addOrder(Order order) {
        this.order = order;
    }

    //orderItem item 연관관계 설정
    public void addItem(Item item) {
        this.item = item;
    }

    //물건에 대한 총 금액
    private void setTotalPrice(int itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    //물건을 구매할 개수
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

    //물건 취소
    public void cancelOrderItem() {
        //취소한 수 만큼 재고 증가
        this.getItem().addStock(count);
    }
}
