package study.market.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.market.item.ItemType;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDto {

    private Long itemId;
    private Long orderItemId;
    private String itemName;
    private int price;
    private int stock;
    private int itemTotalPrice;
    private int count;
    private ItemType itemType;

    @Builder
    public OrderItemDto(Long itemId, Long orderItemId, String itemName, int price, int stock, int itemTotalPrice, int count, ItemType itemType) {
        this.itemId = itemId;
        this.orderItemId = orderItemId;
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
        this.itemTotalPrice = itemTotalPrice;
        this.count = count;
        this.itemType = itemType;
    }
}
