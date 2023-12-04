package study.market.delievery.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.market.delievery.dto.DeliveryListDto;
import study.market.delievery.entity.Delivery;
import study.market.delievery.repository.DeliveryQueryRepository;
import study.market.delievery.repository.DeliveryRepository;
import study.market.member.entity.Member;
import study.market.member.enumType.Role;
import study.market.member.repository.MemberRepository;
import study.market.order.entity.Order;
import study.market.order.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryQueryRepository deliveryQueryRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<DeliveryListDto> getWaitingOrderList(String searchKeyword, Pageable pageable) {

        Page<Order> waitingOrderList = deliveryQueryRepository.getWaitingOrderList(searchKeyword, pageable);
        Page<DeliveryListDto> deliveryPage = waitingOrderList.map(m -> DeliveryListDto.builder()
                        .orderId(m.getId())
                        .orderTime(m.getOrderTime())
                        .address(m.getAddress())
                        .detailAddress(m.getDetailAddress())
                        .build());

        return deliveryPage;
    }

    @Transactional
    @Override
    public void startDelivery(Long orderId, String email) {

        Member driver = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        Delivery delivery = Delivery.startDelivery(driver.getId(), order);
        deliveryRepository.save(delivery);
    }

    @Transactional
    @Override
    public void cancelDelivery(Long orderId, String email) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        Long deliveryId = order.getDelivery().getId();
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(EntityNotFoundException::new);

        delivery.cancelDelivery(order);
        deliveryRepository.delete(delivery);
    }
}
