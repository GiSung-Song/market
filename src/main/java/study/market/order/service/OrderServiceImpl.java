package study.market.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.market.cart.entity.Cart;
import study.market.cart.entity.CartItem;
import study.market.cart.repository.CartRepository;
import study.market.item.entity.Item;
import study.market.item.repository.ItemRepository;
import study.market.member.entity.Member;
import study.market.member.enumType.Role;
import study.market.member.repository.MemberRepository;
import study.market.order.dto.OrderDto;
import study.market.order.dto.OrderItemDto;
import study.market.order.entity.Order;
import study.market.order.entity.OrderItem;
import study.market.order.enumType.OrderStatus;
import study.market.order.repository.OrderQueryRepository;
import study.market.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderQueryRepository orderQueryRepository;

    @Transactional
    @Override
    public Long orderItem(OrderDto orderDto, String email) {

        Member member = findMember(email);

        List<OrderItem> orderItems = new ArrayList<>();

        //주문 상품 생성
        for (OrderItemDto orderItemDto : orderDto.getOrderItemDtoList()) {
            Item item = itemRepository.findById(orderItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
            OrderItem orderItem = OrderItem.createOrderItem(item, orderItemDto.getCount());

            orderItems.add(orderItem);
        }

        //주문 생성
        Order order = Order.createOrder(
                member, orderDto.getAddress(), orderDto.getDetailAddress(), orderDto.getZipCode(), orderItems, orderDto.getTotalPrice(), orderDto.getMessage());

        //주문 저장
        orderRepository.save(order);

        //장바구니 비우기
        member.getCart().removeAllCartItem();

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

    @Transactional(readOnly = true)
    @Override
    //주문 내역 가져오기
    public Page<OrderDto> getOrderHistoryList(String email, Pageable pageable) {
        Member member = findMember(email);

        Page<Order> orderList = orderQueryRepository.findAllOrder(member.getId(), pageable);

        Page<OrderDto> orderDtoPage = orderList.map(m -> OrderDto.builder()
                .id(m.getId())
                .phoneNumber(m.getMember().getPhoneNumber())
                .address(m.getAddress())
                .detailAddress(m.getDetailAddress())
                .orderStatus(m.getOrderStatus())
                .totalPrice(m.getTotalPrice())
                .orderTime(m.getOrderTime())
                .build());

        return orderDtoPage;
    }

    @Transactional(readOnly = true)
    @Override
    //주문 정보 가져오기
    public OrderDto getOrderInfo(String email) {
        Member member = findMember(email);

        OrderDto orderDto = new OrderDto();
        orderDto.setPhoneNumber(member.getPhoneNumber()); //핸드폰 번호
        orderDto.setZipCode(member.getZipCode()); //우편번호
        orderDto.setAddress(member.getAddress()); //주소
        orderDto.setDetailAddress(member.getDetailAddress()); //상세주소

        Cart memberCart = cartRepository.findByMemberId(member.getId());
        List<CartItem> cartItemList = memberCart.getCartItemList();
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();

        for (CartItem cartItem : cartItemList) {

            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setItemId(cartItem.getItem().getId()); //상품Id
            orderItemDto.setItemName(cartItem.getItem().getItemName()); //상품명
            orderItemDto.setStock(cartItem.getItem().getStock()); //상품 재고
            orderItemDto.setPrice(cartItem.getItem().getPrice()); //상품 개당 가격
            orderItemDto.setItemTotalPrice(cartItem.getItemTotalPrice()); //상품 총 가격 (count * price)

            //장바구니에 담은 개수가 재고보다 클 경우 상품 재고 수 만큼 count 수정
            if (cartItem.getCount() > cartItem.getItem().getStock()) {
                orderItemDto.setCount(cartItem.getItem().getStock());
            } else {
                //재고보다 담은 상품의 개수가 적을 경우 담은 수 만큼 세팅
                orderItemDto.setCount(cartItem.getCount());
            }

            orderItemDtoList.add(orderItemDto);
        }

        int totalPrice = orderItemDtoList.stream()
                .mapToInt(orderItem -> orderItem.getItemTotalPrice())
                .sum();

        orderDto.setOrderItemDtoList(orderItemDtoList);
        orderDto.setTotalPrice(totalPrice);

        return orderDto;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDto getOrderDetail(Long orderId, String email) {

        Member member = findMember(email);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        Long orderMemberId = order.getMember().getId();

        if (orderMemberId != member.getId() && member.getRole() != Role.DRIVER) {
            return null;
        }

        List<OrderItemDto> orderItemDtoList = order.getOrderItems().stream().map(m -> OrderItemDto.builder()
                .itemId(m.getId())
                .orderItemId(m.getItem().getId())
                .itemName(m.getItem().getItemName())
                .price(m.getItem().getPrice())
                .itemTotalPrice(m.getItemTotalPrice())
                .count(m.getCount())
                .itemType(m.getItem().getItemType())
                .build()).collect(Collectors.toList());

        OrderDto orderDto = OrderDto.builder()
                .id(order.getId())
                .orderItemDtoList(orderItemDtoList)
                .phoneNumber(order.getMember().getPhoneNumber())
                .address(order.getAddress())
                .detailAddress(order.getDetailAddress())
                .orderStatus(order.getOrderStatus())
                .message(order.getMessage())
                .totalPrice(order.getTotalPrice())
                .orderTime(order.getOrderTime())
                .orderCancelTime(order.getOrderCancelTime())
                .startDeliveryTime(order.getStartDeliveryTime())
                .finishDeliveryTime(order.getFinishDeliveryTime())
                .build();

        //배달 진행중 혹은 배달완료가 아니면
        if (order.getDelivery() != null) {
            Long driverId = order.getDelivery().getDriverId();
            Member driver = memberRepository.findById(driverId).orElseThrow(EntityNotFoundException::new);

            orderDto.setDriverName(driver.getName());
            orderDto.setDriverPhoneNumber(driver.getPhoneNumber());

            if (driver.getRole() == Role.DRIVER) {
                orderDto.setDriver(true);
            }
        } else {
            orderDto.setDriver(false);
        }

        return orderDto;
    }

    private Member findMember(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new EntityNotFoundException("회원을 찾을 수 없습니다.");
        }

        return member;
    }


}
