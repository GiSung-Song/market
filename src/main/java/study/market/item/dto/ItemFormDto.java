package study.market.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.market.item.ItemStatus;
import study.market.item.ItemType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명을 입력해주세요.")
    private String itemName;

    @NotBlank(message = "상품 카테고리는 필수 입력 값 입니다.")
    private ItemType itemType;

    @NotBlank(message = "가격을 입력해주세요.")
    private int price; //상품 가격

    @NotBlank(message = "상태는 필수 입력 값 입니다.")
    private ItemStatus itemStatus; //상품 판매 상태

    @NotBlank(message = "재고 수량은 필수 입력 값 입니다.")
    private int stock;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    @Builder
    public ItemFormDto(String itemName, ItemType itemType, int price, ItemStatus itemStatus, int stock) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.price = price;
        this.itemStatus = itemStatus;
        this.stock = stock;
    }
}
