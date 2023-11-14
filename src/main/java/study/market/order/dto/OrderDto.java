package study.market.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    private String address;
    private String detailAddress;
    private String zipCode;

}
