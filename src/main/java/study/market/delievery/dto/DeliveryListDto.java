package study.market.delievery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryListDto {

    private Long orderId;
    private LocalDateTime orderTime;
    private String address;
    private String detailAddress;

    @Builder
    public DeliveryListDto(Long orderId, LocalDateTime orderTime, String address, String detailAddress) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.address = address;
        this.detailAddress = detailAddress;
    }
}
