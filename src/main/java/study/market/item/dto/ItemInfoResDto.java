package study.market.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.market.item.ItemStatus;
import study.market.item.ItemType;

@Getter
@Setter
@NoArgsConstructor
public class ItemInfoResDto {

    private ItemType itemType;

    private ItemStatus itemStatus;

    private String itemName;

    private int price;

    private int stock;

    @Builder
    public ItemInfoResDto(ItemType itemType, ItemStatus itemStatus, String itemName, int price, int stock) {
        this.itemType = itemType;
        this.itemStatus = itemStatus;
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
    }
}
