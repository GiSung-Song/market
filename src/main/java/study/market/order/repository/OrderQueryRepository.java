package study.market.order.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import study.market.item.ItemStatus;
import study.market.order.entity.Order;

import java.util.List;
import java.util.function.Supplier;

import static study.market.item.entity.QItem.item;
import static study.market.order.entity.QOrder.order;

@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory query;

    public OrderQueryRepository(JPAQueryFactory query) {
        this.query = query;
    }

    public Page<Order> findAllOrder(Long memberId, Pageable pageable) {

        List<Order> content = query.select(order)
                .from(order)
                .where(
                        memberIdEq(memberId))
                .orderBy(order.orderTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(order.count())
                .from(order)
                .where(
                        memberIdEq(memberId))
                .fetchOne();

        return new PageImpl<>(content, pageable, count);

    }

    private BooleanBuilder memberIdEq(Long memberId) {
        return nullSafeBuilder(() -> order.member.id.eq(memberId));
    }

    private BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (IllegalArgumentException e) {
            return new BooleanBuilder();
        }
    }

}
