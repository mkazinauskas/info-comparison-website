package modzo.compare.query.core.api.items

import modzo.compare.query.core.domain.item.Item
import org.springframework.stereotype.Component

@Component
class ItemBeanMapper {

    ItemBean map(Item item) {
        new ItemBean(
                uniqueId: item.uniqueId,
                name: item.name,
                description: item.description
        )
    }
}
