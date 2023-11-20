package study.market.cart.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.market.cart.dto.CartItemDto;
import study.market.cart.entity.Cart;
import study.market.cart.entity.CartItem;
import study.market.cart.repository.CartRepository;
import study.market.item.ItemStatus;
import study.market.item.ItemType;
import study.market.item.entity.Item;
import study.market.item.repository.ItemRepository;
import study.market.member.MemberStatus;
import study.market.member.Role;
import study.market.member.entity.Member;
import study.market.member.repository.MemberRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepository;


    @Test
    void addCart() {

        saveMember();
        saveItem();

        Member member = memberRepository.findByEmail("test123@test.com");
        Item item = itemRepository.findByItemName("음료수");

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setItemName(item.getItemName());
        cartItemDto.setId(item.getId());
        cartItemDto.setCount(10);

        CartItemDto cartItemDto2 = new CartItemDto();
        cartItemDto2.setItemName(item.getItemName());
        cartItemDto2.setId(item.getId());
        cartItemDto2.setCount(20);

        Long savedId = cartService.addCartItem(cartItemDto, member.getEmail());
        cartService.addCartItem(cartItemDto2, member.getEmail());
        Cart cart = cartRepository.findById(savedId).get();

        Assertions.assertThat(savedId).isEqualTo(cart.getId());
        Assertions.assertThat(cart.getCartItemList().get(0).getItem().getItemName()).isEqualTo("음료수");
        Assertions.assertThat(cart.getCartItemList().get(0).getCount()).isEqualTo(30);

    }

    @Test
    void deleteCartItem() {

        saveMember();
        saveItem();

        Member member = memberRepository.findByEmail("test123@test.com");
        Item drink = itemRepository.findByItemName("음료수");
        Item snack = itemRepository.findByItemName("과자");

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setItemName(drink.getItemName());
        cartItemDto.setId(drink.getId());
        cartItemDto.setCount(10);

        CartItemDto cartItemDto2 = new CartItemDto();
        cartItemDto2.setItemName(snack.getItemName());
        cartItemDto2.setId(snack.getId());
        cartItemDto2.setCount(20);


        Long savedId = cartService.addCartItem(cartItemDto, member.getEmail());
        cartService.addCartItem(cartItemDto2, member.getEmail());

        Cart cart = cartRepository.findById(savedId).get();

        Assertions.assertThat(cart.getCartItemList().size()).isEqualTo(2);

        //삭제
        cartService.deleteCartItem(cartItemDto, member.getEmail());

        Cart cart2 = cartRepository.findById(savedId).get();
        Assertions.assertThat(cart2.getCartItemList().size()).isEqualTo(1);
    }

    @Test
    void clearAllCartItem() {
        saveMember();
        saveItem();

        Member member = memberRepository.findByEmail("test123@test.com");
        Item drink = itemRepository.findByItemName("음료수");
        Item snack = itemRepository.findByItemName("과자");

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setItemName(drink.getItemName());
        cartItemDto.setId(drink.getId());
        cartItemDto.setCount(10);

        CartItemDto cartItemDto2 = new CartItemDto();
        cartItemDto2.setItemName(snack.getItemName());
        cartItemDto2.setId(snack.getId());
        cartItemDto2.setCount(20);

        Long savedId = cartService.addCartItem(cartItemDto, member.getEmail());
        cartService.addCartItem(cartItemDto2, member.getEmail());

        Cart cart = cartRepository.findById(savedId).get();

        Assertions.assertThat(cart.getCartItemList().size()).isEqualTo(2);

        //모든 장바구니 아이템 삭제
        cartService.clearCart(member.getEmail());

        Cart cart2 = cartRepository.findById(savedId).get();
        Assertions.assertThat(cart2.getCartItemList().size()).isEqualTo(0);
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

}