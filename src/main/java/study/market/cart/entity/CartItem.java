package study.market.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.market.item.entity.Item;

@Entity
@Getter
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int count;

    private int itemTotalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    //cart cartItem 연관관계
    public void addCart(Cart cart) {
        this.cart = cart;
    }

    //cartItem item 연관관계
    public void addItem(Item item) {
        this.item = item;
    }

    private void setCount(int count) {
        this.count = count;
    }

    private void setTotalPrice(int itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    public static CartItem createCartItem(Item item, int count) {
        CartItem cartItem = new CartItem();
        cartItem.addItem(item);
        cartItem.setCount(count);
        cartItem.setTotalPrice(count * item.getPrice());

        return cartItem;
    }
}
