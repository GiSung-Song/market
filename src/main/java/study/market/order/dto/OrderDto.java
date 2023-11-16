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

    private Long id;
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();
    private String address;
    private String detailAddress;
    private String zipCode;
    private OrderStatus orderStatus;

}
