package modzo.compare.query.core.domain.item.commands.item

import modzo.compare.query.core.domain.item.Item
import modzo.compare.query.core.domain.item.Items
import org.springframework.stereotype.Component

@Component
class CreateItemHandler {

    private final Items items

    private final CreateItemValidator validator

    CreateItemHandler(Items items, CreateItemValidator validator) {
        this.items = items
        this.validator = validator
    }

    void handle(CreateItem createItem) {
        validator.validate(createItem)

        Item item = new Item().with {
            uniqueId = createItem.uniqueId
            name = createItem.name
            description = createItem.description
            order = items.count() + 1
            it
        }

        items.save(item)
    }
}
