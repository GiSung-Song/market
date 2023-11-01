package study.market.item.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.market.item.ItemType;

/**
 * 아이템 테이블
 * id : 기본 키
 * itemType : 아이템 타입(카테고리)
 * itemName : 아이템 이름
 * price : 가격
 * stock : 재고량
 */

@Getter
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemType itemType;

    @Column(nullable = false, unique = true)
    private String itemName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stock;

}
