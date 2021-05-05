package modzo.compare.command.core.domain.parsed_item.command

import modzo.compare.command.core.domain.parsed_item.ParsedItem
import modzo.compare.command.core.domain.parsed_item.ParsedItems
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreateParsedItemHandler {

    @Autowired
    private ParsedItems parsedItems

    @Autowired
    private CreateParsedItemValidator validator

    @Transactional
    void handle(CreateParsedItem createParsedItem) {
        validator.validate(createParsedItem)

        ParsedItem item = new ParsedItem().with {
            uniqueId = createParsedItem.uniqueId
            itemUniqueId = createParsedItem.itemUniqueId.orElse(null)
            identifier = createParsedItem.identifier
            status = createParsedItem.status

            it
        }

        parsedItems.saveAndFlush(item)
    }
}
