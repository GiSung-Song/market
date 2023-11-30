package study.market.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.market.member.entity.Member;
import study.market.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

@Slf4j
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false, length = 5)
    private String zipCode;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();

    private int totalPrice;

    private String message;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderTime;

    private LocalDateTime startDeliveryTime;

    private LocalDateTime finishDeliveryTime;

    //member  order 연관관계 설정
    public void addMember(Member member) {
        this.member = member;
        member.addOrder(this);
    }

    //order  orderItem 연관관계 설정
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.addOrder(this);
    }

    //주소지 설정
    public void addAddress(String address, String detailAddress, String zipCode) {
        this.address = address;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
    }

    //주문 상태 변경
    public void editOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    //주문 생성
    public static Order createOrder(Member member, String address, String detailAddress, String zipCode, List<OrderItem> orderItems, int totalPrice, String message) {
        Order order = new Order();
        order.addMember(member);
        order.addAddress(address, detailAddress, zipCode);
        order.setTotalPrice(totalPrice);
        order.setMessage(message);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.editOrderStatus(OrderStatus.READY_DELIVERY);
        order.setOrderTime();
        return order;
    }

    public void cancelOrder() {

        if (this.orderStatus == OrderStatus.DELIVERY || this.orderStatus == OrderStatus.FINISH_DELIVERY) {
            throw new IllegalStateException("배송중이거나 배송완료 된 상품은 취소가 불가능합니다.");
        }

        editOrderStatus(OrderStatus.CANCEL);
        setOrderTime();

        for (OrderItem orderItem : orderItems) {
            orderItem.cancelOrderItem();
        }
    }

    private void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private void setOrderTime() {
        this.orderTime = LocalDateTime.now();
    }
}
