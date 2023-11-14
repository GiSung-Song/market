package study.market.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDto {

    private Long id;
    private String itemName;
    private int price;
    private int stock;
    private int count;
}
