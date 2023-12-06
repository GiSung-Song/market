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
    private ItemStatus itemStatus;
    private String itemType;

    private String sortGbn; //구분자 / 1 : 매출 높은 순서 , 2 : 매출 낮은 순서, 3 : 판매량 높은 순서, 4 : 판매량 낮은 순서

}