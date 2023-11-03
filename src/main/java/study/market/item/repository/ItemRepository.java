package study.market.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.market.item.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByItemName(String itemName);
}
