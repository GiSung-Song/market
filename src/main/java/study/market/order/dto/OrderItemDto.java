package study.market.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
