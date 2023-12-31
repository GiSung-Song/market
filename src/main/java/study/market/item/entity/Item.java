package study.market.item.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import study.market.item.enumType.ItemStatus;
import study.market.item.enumType.ItemType;

/**
 * 아이템 테이블
 * id : 기본 키
 * itemType : 아이템 타입(카테고리)
 * itemName : 아이템 이름
 * price : 가격
 * stock : 재고량
 * itemStatus : 상품 상태
 * accumulateSales : 누적판매량
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

    @ColumnDefault("0")
    private Long salesCount; //누적판매량

    @Builder
    public Item(ItemType itemType, String itemName, int price, int stock, ItemStatus itemStatus) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
        this.itemStatus = itemStatus;
    }

    //상품 수정
    public void editItem(ItemType itemType, ItemStatus itemStatus, int price, int stock) {
        this.itemType = itemType;
        this.itemStatus = itemStatus;
        this.price = price;
        this.stock = stock;
    }

    //상품 주문 시 재고 감소
    public void removeStock(int count) {
        this.salesCount += count;
        this.stock -= count;
    }

    //상품 취소 시 재고 증가
    public void addStock(int count) {
        this.salesCount -= count;
        this.stock += count;
    }

}
