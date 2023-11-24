package study.market.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.market.order.OrderStatus;

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

}
