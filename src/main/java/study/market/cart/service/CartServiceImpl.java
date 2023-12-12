package study.market.cart.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.market.cart.dto.CartItemDto;
import study.market.cart.entity.Cart;
import study.market.cart.entity.CartItem;
import study.market.cart.repository.CartRepository;
import study.market.etc.config.CustomException;
import study.market.etc.enumType.ErrorCode;
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

        Member member = findMember(email);

        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        Cart cart = cartRepository.findByMemberId(member.getId());
        CartItem cartItem = CartItem.createCartItem(item, cartItemDto.getCount());

        //장바구니가 없을 시
        if (cart == null) {
            cart = Cart.createCart(member);
            cart.addCartItem(cartItem);
        } else {
            //장바구니가 있을 때
            Optional<CartItem> existCartItem = cart.getCartItemList().stream()
                    .filter(inCartItem -> inCartItem.getItem().getId() == item.getId())
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

        Member member = findMember(email);

        Cart cart = cartRepository.findByMemberId(member.getId());

        //장바구니가 없을 시
        if (cart == null) {
            throw new CustomException(ErrorCode.CART_NOT_FOUND);
        } else {
            //장바구니에 해당 상품이 없을 때 오류처리
            CartItem cartItem = cart.getCartItemList().stream()
                    .filter(inCartItem -> inCartItem.getItem().getId() == cartItemDto.getItemId())
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ErrorCode.CART_BAD_REQUEST));

            // 장바구니에 해당상품이 있을 시 삭제
            cart.removeCartItem(cartItem);
        }

    }

    @Transactional
    @Override
    public void clearCart(String email) {

        Member member = findMember(email);
        Cart cart = cartRepository.findByMemberId(member.getId());

        if (cart == null) {
            cart = Cart.createCart(member);
        } else {
            cart.removeAllCartItem();
        }

    }

    @Transactional(readOnly = true)
    @Override
    public List<CartItemDto> getCartItemList(String email) {

        Member member = findMember(email);

        //회원 장바구니 검색
        Cart cart = cartRepository.findByMemberId(member.getId());

        if (cart == null) {
            cart = Cart.createCart(member);
        }

        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        List<CartItem> cartItemList = cart.getCartItemList();

        for (CartItem cartItem : cartItemList) {
            CartItemDto cartItemDto = new CartItemDto();

            cartItemDto.setCartItemId(cartItem.getId());
            cartItemDto.setItemId(cartItem.getItem().getId());
            cartItemDto.setItemName(cartItem.getItem().getItemName());
            cartItemDto.setPrice(cartItem.getItem().getPrice());
            cartItemDto.setStock(cartItem.getItem().getStock());
            cartItemDto.setItemTotalPrice(cartItem.getItemTotalPrice());
            cartItemDto.setCount(cartItem.getCount());

            cartItemDtoList.add(cartItemDto);
        }

        return cartItemDtoList;
    }

    @Transactional
    @Override
    public void editCount(CartItemDto cartItemDto, String email) {

        Member member = findMember(email);

        Cart cart = cartRepository.findByMemberId(member.getId());

        if (cart == null) {
            cart = Cart.createCart(member);
        }

        CartItem cartItem = cart.getCartItemList().stream()
                .filter(cartItm -> cartItm.getItem().getId() == cartItemDto.getItemId())
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.CART_BAD_REQUEST));

        cartItem.editCount(cartItemDto.getCount());
    }

    private Member findMember(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return member;
    }
}
