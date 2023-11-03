package study.market.item.dto;

import lombok.Getter;
import lombok.Setter;
import study.market.item.ItemStatus;
import study.market.item.ItemType;

@Getter
@Setter
public class ItemSearchCondition {

    private String itemName;
    private String orderPrice;
    private ItemStatus itemStatus;
    private ItemType itemType;

}
