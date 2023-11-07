package study.market.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.market.item.ItemStatus;
import study.market.item.ItemType;

@Getter
@Setter
@NoArgsConstructor
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "카테고리는 필수 입력 값 입니다.")
    private ItemType itemType;

    @NotBlank(message = "상태는 필수 입력 값 입니다.")
    private ItemStatus itemStatus;

    @NotBlank(message = "상품명은 필수 입력 값 입니다.")
    private String itemName;

    @NotBlank(message = "상품 가격은 필수 입력 값 입니다.")
    private int price;

    @NotBlank(message = "재고 수량은 필수 입력 값 입니다.")
    private int stock;

    @Builder
    public ItemFormDto(ItemType itemType, ItemStatus itemStatus, String itemName, int price, int stock) {
        this.itemType = itemType;
        this.itemStatus = itemStatus;
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
    }

}
