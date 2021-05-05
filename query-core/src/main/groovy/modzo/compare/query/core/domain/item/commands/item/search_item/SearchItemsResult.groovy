package modzo.compare.query.core.domain.item.commands.item.search_item

import modzo.compare.query.core.domain.item.Item
import org.springframework.data.domain.Page

class SearchItemsResult {
    Page<Item> items

    SearchItemsResult(Page<Item> items) {
        this.items = items
    }
}
