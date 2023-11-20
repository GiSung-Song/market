package study.market.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.market.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Slf4j
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CartItem> cartItemList = new ArrayList<>();

    private int totalPrice;

    public void addMember(Member member) {
        this.member = member;
        member.addCart(this);
    }

    //상품 아이템 추가
    public void addCartItem(CartItem cartItem) {
        cartItemList.add(cartItem);
        cartItem.addCart(this);
    }

    //장바구니 생성
    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.addMember(member);
        return cart;
    }

    public void removeCartItem(CartItem cartItem) {
        cartItemList.remove(cartItem);
    }

    public void removeAllCartItem() {
        cartItemList.clear();
    }

}
