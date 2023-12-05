package study.market.delievery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.market.order.enumType.OrderStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryListDto {

    private Long orderId;
    private LocalDateTime orderTime;
    private LocalDateTime startDeliveryTime;
    private LocalDateTime finishDeliverTime;
    private OrderStatus orderStatus;
    private String address;
    private String detailAddress;

    @Builder
    public DeliveryListDto(Long orderId, LocalDateTime orderTime, LocalDateTime startDeliveryTime, OrderStatus orderStatus,
                           LocalDateTime finishDeliverTime, String address, String detailAddress) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.startDeliveryTime = startDeliveryTime;
        this.finishDeliverTime = finishDeliverTime;
        this.orderStatus = orderStatus;
        this.address = address;
        this.detailAddress = detailAddress;
    }
}
