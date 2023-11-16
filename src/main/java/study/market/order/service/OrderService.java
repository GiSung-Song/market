package study.market.order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.market.order.dto.OrderDto;

public interface OrderService {

    Long orderItem(OrderDto orderDto, String email);
    void orderCancel(Long orderId, String email);
    Page<OrderDto> getOrderList(String email, Pageable pageable);

}
