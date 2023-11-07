package study.market.item.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemImgDto {

    private Long id;

    private String imgName; //이미지 명

    private String oriImgName; //원본 이미지 명

    private String imgUrl; //img 저장 경로
}
