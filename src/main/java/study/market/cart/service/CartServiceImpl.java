package study.market.cart.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.market.cart.dto.CartDto;
import study.market.cart.dto.CartItemDto;
import study.market.cart.entity.Cart;
import study.market.cart.entity.CartItem;
import study.market.cart.repository.CartRepository;
import study.market.item.entity.Item;
import study.market.item.repository.ItemRepository;
import study.market.member.entity.Member;
import study.market.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public Long addCartItem(CartItemDto cartItemDto, String email) {

        Member member = getMember(email);

        Item item = itemRepository.findById(cartItemDto.getId()).orElseThrow(EntityNotFoundException::new);
        Cart cart = cartRepository.findByMemberId(member.getId());
        CartItem cartItem = CartItem.createCartItem(item, cartItemDto.getCount());

        //장바구니가 없을 시
        if (cart == null) {
            cart = Cart.createCart(member);
            cart.addCartItem(cartItem);
        } else {
            //장바구니가 있을 때
            Optional<CartItem> existCartItem = cart.getCartItemList().stream()
                    .filter(inCartItem -> inCartItem.getId() == item.getId())
                    .findFirst();

            // 추가할 아이템이 장바구니에 이미 존재하면 count +
            if (existCartItem.isPresent()) {
                existCartItem.get().addCount(cartItemDto.getCount());
            } else {
                // 추가할 아이템이 장바구니에 없으면 cartItem 추가
                cart.addCartItem(cartItem);
            }
        }

        cartRepository.save(cart);

        return cart.getId();
    }

    @Transactional
    @Override
    public void deleteCartItem(CartItemDto cartItemDto, String email) {

        Member member = getMember(email);

        Item item = itemRepository.findById(cartItemDto.getId()).orElseThrow(EntityNotFoundException::new);
        Cart cart = cartRepository.findByMemberId(member.getId());

        //장바구니가 없을 시
        if (cart == null) {
            throw new IllegalStateException("장바구니에 담긴 상품이 없습니다.");
        } else {
            //장바구니에 해당 상품이 없을 때 오류처리
            CartItem cartItem = cart.getCartItemList().stream()
                    .filter(inCartItem -> inCartItem.getId() == item.getId())
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);

            // 장바구니에 해당상품이 있을 시 삭제
            cart.removeCartItem(cartItem);
        }

    }

    @Transactional
    @Override
    public void clearCart(String email) {

        Member member = getMember(email);
        Cart cart = cartRepository.findByMemberId(member.getId());

        if (cart == null) {
            cart = Cart.createCart(member);
        } else {
            cart.removeAllCartItem();
        }

    }

    private Member getMember(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new EntityNotFoundException("해당 회원을 찾을 수 없습니다.");
        }

        return member;
    }

    @Override
    public Page<CartItemDto> getCartItemList(String email, Pageable pageable) {

        Member member = getMember(email);

        //회원 장바구니 검색
        Cart cart = cartRepository.findByMemberId(member.getId());

        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        List<CartItem> cartItemList = cart.getCartItemList();

        for (CartItem cartItem : cartItemList) {
            CartItemDto cartItemDto = new CartItemDto();

            cartItemDto.setId(cartItem.getId());
            cartItemDto.setItemName(cartItemDto.getItemName());
            cartItemDto.setPrice(cartItem.getItem().getPrice());
            cartItemDto.setStock(cartItem.getItem().getStock());
            cartItemDto.setItemTotalPrice(cartItem.getItemTotalPrice());
            cartItemDto.setCount(cartItem.getCount());

            cartItemDtoList.add(cartItemDto);
        }

        return new PageImpl<>(cartItemDtoList, pageable, cartItemList.size());
    }
}
