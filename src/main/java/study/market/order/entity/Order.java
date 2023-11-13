package study.market.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.market.member.entity.Member;
import study.market.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * id : 주문 고유 id
 * member : 회원 번호(Many To One : member_id)
 * address : 주소
 * detailAddress : 상세주소
 * zipCode : 우편번호
 * totalPrice : 총 금액
 * orderItem : 주문한 상품목록
 * orderStatus : 주문상태
 * orderTime : 주문시각
 * startDeliveryTime : 배송시작시각
 * finishDeliveryTime : 배송완료시각
 */

@Getter
@NoArgsConstructor
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false, length = 5)
    private String zipCode;

    @OneToMany(mappedBy = "order_id")
    private List<OrderItem> orderItem;

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderTime;

    private LocalDateTime startDeliveryTime;

    private LocalDateTime finishDeliveryTime;
}
