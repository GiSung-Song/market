package study.market.delievery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.market.order.entity.Order;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Delivery {

    @Id @Column(name = "delivery_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long driverId;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    private void setDriver(Long driverId) {
        this.driverId = driverId;
    }

    private void addOrder(Order order) {
        this.order = order;
        order.addDelivery(this);
    }

    public void cancelDelivery(Order order) {
        order.cancelDelivery();
    }

    public static Delivery startDelivery(Long driverId, Order order) {
        Delivery delivery = new Delivery();
        delivery.setDriver(driverId);
        delivery.addOrder(order);

        return delivery;
    }

}
