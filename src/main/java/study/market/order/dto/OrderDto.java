package study.market.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import study.market.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

    private Long id; //주문번호
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>(); //주문목록
    private String phoneNumber; //핸드폰 번호
    private String address; //주소
    private String detailAddress; //상세주소
    private String zipCode; //우편번호
    private OrderStatus orderStatus; //주문현황
    private String message; //요청사항
    private int totalPrice; //총 가격
    private LocalDateTime orderTime; //주문시각
    private LocalDateTime startDeliveryTime; //배달시작시각
    private LocalDateTime finishDeliveryTime; //배달완료시각

    @Builder
    public OrderDto(Long id, List<OrderItemDto> orderItemDtoList, String phoneNumber,
                    String address, String detailAddress, String zipCode, OrderStatus orderStatus,
                    String message, int totalPrice, LocalDateTime orderTime, LocalDateTime startDeliveryTime, LocalDateTime finishDeliveryTime) {
        this.id = id;
        this.orderItemDtoList = orderItemDtoList;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
        this.orderStatus = orderStatus;
        this.message = message;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
        this.startDeliveryTime = startDeliveryTime;
        this.finishDeliveryTime = finishDeliveryTime;
    }
}
