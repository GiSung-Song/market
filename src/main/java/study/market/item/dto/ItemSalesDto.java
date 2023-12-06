package study.market.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.market.item.enumType.ItemType;

@Getter
@Setter
@NoArgsConstructor
public class ItemSalesDto {

    private ItemType itemType;
    private String itemName;
    private int price;
    private Long salesCount;
    private Long sales;
    private int stock;

    @Builder
    public ItemSalesDto(ItemType itemType, String itemName, int price, Long salesCount, Long sales, int stock) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.price = price;
        this.salesCount = salesCount;
        this.sales = sales;
        this.stock = stock;
    }
}
