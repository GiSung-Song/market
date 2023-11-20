package study.market.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public Long orderItem(OrderDto orderDto, String email) {

        Member member = findMember(email);

        List<OrderItem> orderItems = new ArrayList<>();

        //주문 상품 생성
        for (OrderItemDto orderItemDto : orderDto.getOrderItemDtoList()) {
            Item item = itemRepository.findById(orderItemDto.getId()).orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item, orderItemDto.getCount());

            orderItems.add(orderItem);
        }

        //주문 생성
        Order order = Order.createOrder(member, orderDto.getAddress(), orderDto.getDetailAddress(), orderDto.getZipCode(), orderItems);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    @Override
    public void orderCancel(Long orderId, String email) {
        Member member = findMember(email);

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        if (order.getMember().getId() != (member.getId())) {
            throw new IllegalStateException("회원이 주문한 상품이 아닙니다.");
        }

        //주문 취소
        order.cancelOrder();
    }

    @Override
    public Page<OrderDto> getOrderList(String email, Pageable pageable) {
        Member member = findMember(email);

        Page<Order> orderList = orderRepository.findByMemberId(member.getId(), pageable);
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (Order order : orderList) {

            //주문Dto 배달 지역 및 배달 상태 설정
            OrderDto orderDto = new OrderDto();

            orderDto.setAddress(order.getAddress());
            orderDto.setDetailAddress(order.getDetailAddress());
            orderDto.setZipCode(order.getZipCode());
            orderDto.setOrderStatus(order.getOrderStatus());

            //주문 내역 중 주문에 대한 주문 아이템 설정
            List<OrderItemDto> orderItemDtoList = new ArrayList<>();
            List<OrderItem> orderItems = order.getOrderItems();

            for (OrderItem orderItem : orderItems) {
                //주문 아이템 설정
                OrderItemDto orderItemDto = toDtoOrderItem(orderItem);
                orderItemDtoList.add(orderItemDto);
            }

            orderDto.setOrderItemDtoList(orderItemDtoList);
            orderDtoList.add(orderDto);
        }

        return new PageImpl<>(orderDtoList, pageable, orderList.getTotalElements());
    }

    private Member findMember(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new EntityNotFoundException("회원을 찾을 수 없습니다.");
        }

        return member;
    }

    private OrderItemDto toDtoOrderItem(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.setId(orderItem.getId());
        orderItemDto.setItemName(orderItem.getItem().getItemName());
        orderItemDto.setStock(orderItem.getItem().getStock());
        orderItemDto.setPrice(orderItem.getItem().getPrice());
        orderItemDto.setTotalPrice(orderItem.getItemTotalPrice());
        orderItemDto.setCount(orderItem.getCount());

        return orderItemDto;
    }
}
