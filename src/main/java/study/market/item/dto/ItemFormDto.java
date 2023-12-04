package study.market.item.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import study.market.etc.annotation.ValidEnum;
import study.market.item.enumType.ItemStatus;
import study.market.item.enumType.ItemType;

@Getter
@Setter
@NoArgsConstructor
public class ItemFormDto {

    private Long id;

    @ValidEnum(enumClass = ItemType.class, message = "아이템 카테고리를 선택해주세요.")
    private ItemType itemType;

    @ValidEnum(enumClass = ItemStatus.class, message = "아이템 재고 상황을 선택해주세요.")
    private ItemStatus itemStatus;

    @NotBlank(message = "상품명은 필수 입력 값 입니다.")
    private String itemName;

    @DecimalMin(value = "1", message = "가격은 1원 이상이어야 합니다.")
    private int price;

    @DecimalMin(value = "0", message = "재고는 0 이상이어야 합니다.")
    private int stock;

    @Builder
    public ItemFormDto(Long id, ItemType itemType, ItemStatus itemStatus, String itemName, int price, int stock) {
        this.id = id;
        this.itemType = itemType;
        this.itemStatus = itemStatus;
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
    }

}
