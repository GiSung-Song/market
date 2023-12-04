package study.market.delievery.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.market.delievery.dto.DeliveryListDto;

public interface DeliveryService {

    Page<DeliveryListDto> getWaitingOrderList(String searchKeyword, Pageable pageable);
    void startDelivery(Long orderId, String email);
    void cancelDelivery(Long orderId, String email);
}
