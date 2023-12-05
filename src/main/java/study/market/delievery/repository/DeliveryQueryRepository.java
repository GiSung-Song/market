package study.market.delievery.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import study.market.order.entity.Order;
import study.market.order.enumType.OrderStatus;

import java.util.List;
import java.util.function.Supplier;

import static study.market.order.entity.QOrder.order;

@Repository
public class DeliveryQueryRepository {

    private final JPAQueryFactory query;

    public DeliveryQueryRepository(JPAQueryFactory query) {
        this.query = query;
    }

    public Page<Order> getWaitingOrderList(String searchKeyword, Pageable pageable) {

        List<Order> content = query.select(order)
                .from(order)
                .where(
                        order.orderStatus.eq(OrderStatus.READY_DELIVERY),
                        likeAddress(searchKeyword),
                        likeDetailAddress(searchKeyword))
                .orderBy(order.orderTime.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count =  query.select(order.count())
                .from(order)
                .where(
                        order.orderStatus.eq(OrderStatus.READY_DELIVERY),
                        likeAddress(searchKeyword),
                        likeDetailAddress(searchKeyword))
                .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }

    public Page<Order> getDeliveryList(Long driverId, Pageable pageable) {

        List<Order> content = query.select(order)
                .from(order)
                .where(
                        order.orderStatus.notIn(OrderStatus.CANCEL, OrderStatus.READY_DELIVERY),
                        order.delivery.driverId.eq(driverId))
                .orderBy(order.orderTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(order.count())
                .from(order)
                .where(
                        order.orderStatus.notIn(OrderStatus.CANCEL, OrderStatus.READY_DELIVERY),
                        order.delivery.driverId.eq(driverId))
                .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }

    private BooleanBuilder likeAddress(String searchKeyword) {
        return nullSafeBuilder(() -> order.address.like("%" + searchKeyword + '%'));
    }

    private BooleanBuilder likeDetailAddress(String searchKeyword) {
        return nullSafeBuilder(() -> order.detailAddress.like("%" + searchKeyword + '%'));
    }

    private BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (IllegalArgumentException e) {
            return new BooleanBuilder();
        }
    }
}
