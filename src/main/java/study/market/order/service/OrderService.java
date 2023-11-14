package study.market.order.service;

import study.market.order.dto.OrderDto;

public interface OrderService {

    Long orderItem(OrderDto orderDto, String email);

}
