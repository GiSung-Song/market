package study.market.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.market.item.enumType.ItemStatus;

@Getter
@Setter
@NoArgsConstructor
public class ItemSearchCondition {

    private String itemName;
    private String orderPrice;
    private ItemStatus itemStatus;
    private String itemType;

}