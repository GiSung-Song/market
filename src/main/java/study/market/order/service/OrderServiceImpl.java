package study.market.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.market.item.entity.Item;
import study.market.item.repository.ItemRepository;
import study.market.member.entity.Member;
import study.market.member.repository.MemberRepository;
import study.market.order.dto.OrderDto;
import study.market.order.dto.OrderItemDto;
import study.market.order.entity.Order;
import study.market.order.entity.OrderItem;
import study.market.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public Long orderItem(OrderDto orderDto, String email) {

        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDto orderItemDto : orderDto.getOrderItemDtoList()) {
            Item item = itemRepository.findById(orderItemDto.getId()).orElseThrow(EntityNotFoundException::new);
            OrderItem orderItem = OrderItem.createOrderItem(item, orderItemDto.getCount());

            orderItems.add(orderItem);
        }

        Order order = Order.createOrder(member, orderDto.getAddress(), orderDto.getDetailAddress(), orderDto.getZipCode(), orderItems);
        orderRepository.save(order);

        return order.getId();
    }
}
