package study.market.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemDto {

    private Long id;
    private String itemName;
    private int price;
    private int stock;
    private int itemTotalPrice;
    private int count;
}
