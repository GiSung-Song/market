package study.market.item.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.market.item.ItemStatus;
import study.market.item.ItemType;

/**
 * 아이템 테이블
 * id : 기본 키
 * itemType : 아이템 타입(카테고리)
 * itemName : 아이템 이름
 * price : 가격
 * stock : 재고량
 * itemStatus : 상품 상태
 */

@Getter
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(nullable = false, unique = true)
    private String itemName;

    private int price;

    private int stock;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @Builder
    public Item(ItemType itemType, String itemName, int price, int stock, ItemStatus itemStatus) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
        this.itemStatus = itemStatus;
    }

    public void editItem(ItemStatus itemStatus, int price, int stock) {
        this.itemStatus = itemStatus;
        this.price = price;
        this.stock = stock;
    }
}
