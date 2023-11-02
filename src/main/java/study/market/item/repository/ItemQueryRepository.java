package study.market.item.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import study.market.item.dto.ItemSearchCondition;
import study.market.item.entity.Item;
import study.market.item.entity.QItem;

import java.util.List;

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
                        item.itemStatus.eq(condition.getItemStatus()),
                        item.itemType.eq(condition.getItemType()),
                        likeItemName(condition.getItemName()))
                .fetch();
    }

    private BooleanExpression likeItemName(String itemName) {
        if (StringUtils.hasText(itemName)) {
            return item.itemName.like("%" + itemName + "%");
        }

        return null;
    }
}
