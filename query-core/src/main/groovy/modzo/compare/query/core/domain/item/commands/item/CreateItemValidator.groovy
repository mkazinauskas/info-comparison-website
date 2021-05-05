package modzo.compare.query.core.domain.item.commands.item

import modzo.compare.query.core.domain.item.Items
import modzo.compare.query.core.domain.validators.DomainException
import org.elasticsearch.index.query.QueryParser
import org.springframework.stereotype.Component

@Component
class CreateItemValidator {

    private final Items items

    CreateItemValidator(Items items) {
        this.items = items
    }

    void validate(CreateItem createItem) {
        if (!createItem.uniqueId) {
            throw new DomainException('ITEM_DOES_NOT_HAVE_UNIQUE_ID', 'Item does not have uniqueId')
        }
        items.findByUniqueId(createItem.uniqueId).ifPresent {
            item -> throw new DomainException('ITEM_ALREADY_EXISTS', "Item with uniqueId `$createItem.uniqueId` already exists")
        }
    }
}
