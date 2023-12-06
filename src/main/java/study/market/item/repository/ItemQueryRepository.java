package study.market.item.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import study.market.item.enumType.ItemStatus;
import study.market.item.enumType.ItemType;
import study.market.item.dto.ItemSearchCondition;
import study.market.item.entity.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static study.market.item.entity.QItem.item;

@Slf4j
@Repository
public class ItemQueryRepository {

    private final JPAQueryFactory query;

    public ItemQueryRepository(JPAQueryFactory query) {
        this.query = query;
    }

    public List<Item> findAll(ItemSearchCondition condition) {
        return query.select(item)
                .from(item)
                .where(
                        itemStatusEq(condition.getItemStatus()),
                        itemTypeEq(condition.getItemType()),
                        likeItemName(condition.getItemName()))
                .fetch();
    }

    public Page<Item> findSearchCondition(ItemSearchCondition condition, Pageable pageable) {
        List<Item> content = query.select(item)
                .from(item)
                .where(
                        itemTypeEq(condition.getItemType()),
                        likeItemName(condition.getItemName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(item.count())
                .from(item)
                .where(
                        itemTypeEq(condition.getItemType()),
                        likeItemName(condition.getItemName()))
                .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }

    public Page<Item> findItemSales(ItemSearchCondition condition, Pageable pageable) {

        OrderSpecifier[] orderSpecifiers = itemSort(condition);

        List<Item> content = query.select(item)
                .from(item)
                .where(
                        itemTypeEq(condition.getItemType()),
                        likeItemName(condition.getItemName()))
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(item.count())
                .from(item)
                .where(
                        itemTypeEq(condition.getItemType()),
                        likeItemName(condition.getItemName()))
                .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }

    private OrderSpecifier[] itemSort(ItemSearchCondition condition) {

        log.info("condition.getSortGbn() : {}", condition.getSortGbn());

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if (Objects.isNull(condition)) {
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, item.itemName));
        } else if (condition.getSortGbn().equals("1")) { //매출 높은 순서
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, item.salesCount.multiply(item.price)));
        } else if (condition.getSortGbn().equals("2")) { //매출 낮은 순서
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, item.salesCount.multiply(item.price)));
        } else if (condition.getSortGbn().equals("3")) { //판매량 높은 순서
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, item.salesCount));
        } else if (condition.getSortGbn().equals("4")) { //판매량 낮은 순서
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, item.salesCount));
        } else {
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, item.itemName));
        }

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }


    private BooleanBuilder likeItemName(String itemName) {

        return nullSafeBuilder(() -> item.itemName.like("%" + itemName + '%'));
    }

    private BooleanBuilder itemStatusEq(ItemStatus itemStatus) {
        return nullSafeBuilder(() -> item.itemStatus.eq(itemStatus));
    }

    private BooleanBuilder itemTypeEq(String itemType) {
        if(StringUtils.hasText(itemType)) {
            return nullSafeBuilder(() -> item.itemType.eq(ItemType.valueOf(itemType.toUpperCase())));
        }

        return new BooleanBuilder();

    }

    private BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (IllegalArgumentException e) {
            return new BooleanBuilder();
        }
    }
}
