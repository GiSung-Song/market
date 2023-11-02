package study.market.item.dto;

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

}
