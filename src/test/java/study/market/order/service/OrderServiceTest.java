package study.market.order.service;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import study.market.item.ItemStatus;
import study.market.item.ItemType;
import study.market.item.entity.Item;
import study.market.item.repository.ItemRepository;
import study.market.member.MemberStatus;
import study.market.member.Role;
import study.market.member.entity.Member;
import study.market.member.repository.MemberRepository;
import study.market.order.OrderStatus;
import study.market.order.dto.OrderDto;
import study.market.order.dto.OrderItemDto;
import study.market.order.entity.Order;
import study.market.order.entity.OrderItem;
import study.market.order.repository.OrderItemRepository;
import study.market.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    void saveItem() {
        Item drink = Item.builder()
                .itemType(ItemType.DRINK)
                .itemStatus(ItemStatus.SELL)
                .itemName("음료수")
                .stock(100)
                .price(1000)
                .build();

        Item snack = Item.builder()
                .itemType(ItemType.SNACK)
                .itemStatus(ItemStatus.SELL)
                .itemName("과자")
                .stock(200)
                .price(2000)
                .build();

        Item goods = Item.builder()
                .itemType(ItemType.GOODS)
                .itemStatus(ItemStatus.SELL)
                .itemName("상품")
                .stock(300)
                .price(3000)
                .build();

        itemRepository.save(drink);
        itemRepository.save(snack);
        itemRepository.save(goods);
    }

    void saveMember() {
        Member member = Member.builder()
                .role(Role.USER)
                .name("아무개")
                .email("test123@test.com")
                .zipCode("12345")
                .address("address")
                .detailAddress("detailAddress")
                .memberStatus(MemberStatus.ACTIVE)
                .phoneNumber("010-1234-1234")
                .password("12345")
                .build();

        memberRepository.save(member);
    }

    @Test
    void order() {
        saveItem();
        saveMember();

        Member member = memberRepository.findByEmail("test123@test.com");

        Item drink = itemRepository.findByItemName("음료수");
        Item snack = itemRepository.findByItemName("과자");
        Item goods = itemRepository.findByItemName("상품");

        OrderDto orderDto = new OrderDto();
        List<OrderItemDto> orderItemDtoList = orderDto.getOrderItemDtoList();

        OrderItemDto drinkDto = new OrderItemDto();
        drinkDto.setItemId(drink.getId());
        drinkDto.setItemName(drink.getItemName());
        drinkDto.setStock(drink.getStock());
        drinkDto.setPrice(drink.getPrice());
        drinkDto.setCount(10);

        orderItemDtoList.add(drinkDto);

        OrderItemDto snackDto = new OrderItemDto();
        snackDto.setItemId(snack.getId());
        snackDto.setItemName(snack.getItemName());
        snackDto.setStock(snack.getStock());
        snackDto.setPrice(snack.getPrice());
        snackDto.setCount(20);

        orderItemDtoList.add(snackDto);

        OrderItemDto goodsDto = new OrderItemDto();
        goodsDto.setItemId(goods.getId());
        goodsDto.setItemName(goods.getItemName());
        goodsDto.setStock(goods.getStock());
        goodsDto.setPrice(goods.getPrice());
        goodsDto.setCount(30);

        orderItemDtoList.add(goodsDto);

        orderDto.setAddress("address");
        orderDto.setZipCode("12345");
        orderDto.setDetailAddress("12345");


        Long savedId = orderService.orderItem(orderDto, member.getEmail());
        Order order = orderRepository.findById(savedId).orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();
        List<Long> ids = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            ids.add(orderItem.getId());
        }

        OrderItem findDrink = orderItemRepository.findById(drinkDto.getItemId()).get();

        Assertions.assertThat(findDrink.getItem().getItemName()).isEqualTo(drinkDto.getItemName());

        Assertions.assertThat(ids).containsOnly(drinkDto.getItemId(), snackDto.getItemId(), goodsDto.getItemId());

    }

    @Test
    void getOrderList() {
        saveItem();
        saveMember();
        orderData();

        Member member = memberRepository.findByEmail("test123@test.com");
        PageRequest pageable = PageRequest.of(0, 10);

        Page<OrderDto> orderList = orderService.getOrderHistoryList(member.getEmail(), pageable);
        Assertions.assertThat(orderList.getTotalElements()).isEqualTo(5);
    }

    @Test
    void cancelOrder() {
        saveItem();
        saveMember();

        Member member = memberRepository.findByEmail("test123@test.com");

        Item drink = itemRepository.findByItemName("음료수");
        Item snack = itemRepository.findByItemName("과자");
        Item goods = itemRepository.findByItemName("상품");

        OrderDto orderDto = new OrderDto();
        List<OrderItemDto> orderItemDtoList = orderDto.getOrderItemDtoList();

        OrderItemDto drinkDto = new OrderItemDto();
        drinkDto.setItemId(drink.getId());
        drinkDto.setItemName(drink.getItemName());
        drinkDto.setStock(drink.getStock());
        drinkDto.setPrice(drink.getPrice());
        drinkDto.setCount(10);

        orderItemDtoList.add(drinkDto);

        OrderItemDto snackDto = new OrderItemDto();
        snackDto.setItemId(snack.getId());
        snackDto.setItemName(snack.getItemName());
        snackDto.setStock(snack.getStock());
        snackDto.setPrice(snack.getPrice());
        snackDto.setCount(20);

        orderItemDtoList.add(snackDto);

        OrderItemDto goodsDto = new OrderItemDto();
        goodsDto.setItemId(goods.getId());
        goodsDto.setItemName(goods.getItemName());
        goodsDto.setStock(goods.getStock());
        goodsDto.setPrice(goods.getPrice());
        goodsDto.setCount(30);

        orderItemDtoList.add(goodsDto);

        orderDto.setAddress("address");
        orderDto.setZipCode("12345");
        orderDto.setDetailAddress("12345");

        Long savedId = orderService.orderItem(orderDto, member.getEmail());
        Order order = orderRepository.findById(savedId).orElseThrow(EntityNotFoundException::new);

        orderService.orderCancel(order.getId(), member.getEmail());

        Order cancelOrder = orderRepository.findById(savedId).orElseThrow(EntityNotFoundException::new);

        Assertions.assertThat(cancelOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    void orderData() {
        Member member = memberRepository.findByEmail("test123@test.com");

        Item drink = itemRepository.findByItemName("음료수");
        Item snack = itemRepository.findByItemName("과자");
        Item goods = itemRepository.findByItemName("상품");

        OrderDto orderDto = new OrderDto();
        List<OrderItemDto> orderItemDtoList = orderDto.getOrderItemDtoList();

        OrderItemDto drinkDto = new OrderItemDto();
        drinkDto.setItemId(drink.getId());
        drinkDto.setItemName(drink.getItemName());
        drinkDto.setStock(drink.getStock());
        drinkDto.setPrice(drink.getPrice());
        drinkDto.setCount(10);

        orderItemDtoList.add(drinkDto);

        OrderItemDto snackDto = new OrderItemDto();
        snackDto.setItemId(snack.getId());
        snackDto.setItemName(snack.getItemName());
        snackDto.setStock(snack.getStock());
        snackDto.setPrice(snack.getPrice());
        snackDto.setCount(20);

        orderItemDtoList.add(snackDto);

        OrderItemDto goodsDto = new OrderItemDto();
        goodsDto.setItemId(goods.getId());
        goodsDto.setItemName(goods.getItemName());
        goodsDto.setStock(goods.getStock());
        goodsDto.setPrice(goods.getPrice());
        goodsDto.setCount(30);

        orderItemDtoList.add(goodsDto);

        orderDto.setAddress("address");
        orderDto.setZipCode("12345");
        orderDto.setDetailAddress("12345");


        orderService.orderItem(orderDto, member.getEmail());
        orderService.orderItem(orderDto, member.getEmail());
        orderService.orderItem(orderDto, member.getEmail());
        orderService.orderItem(orderDto, member.getEmail());
        orderService.orderItem(orderDto, member.getEmail());
    }

}