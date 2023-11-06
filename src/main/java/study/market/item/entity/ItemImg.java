package study.market.item.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_img")
@Getter
@NoArgsConstructor
public class ItemImg {

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imgName;

    @Column(nullable = false)
    private String oriImgName;

    @Column(nullable = false)
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @Builder
    public void uploadItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
