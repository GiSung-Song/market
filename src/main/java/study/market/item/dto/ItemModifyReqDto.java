package study.market.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.market.item.ItemStatus;
import study.market.item.ItemType;

@Getter
@Setter
@NoArgsConstructor
public class ItemModifyReqDto {

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

}
