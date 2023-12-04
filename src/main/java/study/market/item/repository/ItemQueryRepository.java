package study.market.item.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import study.market.item.enumType.ItemStatus;
import study.market.item.enumType.ItemType;
import study.market.item.dto.ItemSearchCondition;
import study.market.item.entity.Item;

import java.util.List;
import java.util.function.Supplier;

import static study.market.item.entity.QItem.item;

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
