package modzo.compare.query.core.domain.parser.command

import modzo.compare.query.core.domain.parser.ParsedEvents
import modzo.compare.query.core.domain.validators.DomainException
import org.springframework.stereotype.Component

@Component
class CreateParsedEventValidator {

    private final ParsedEvents parsedItems

    CreateParsedEventValidator(ParsedEvents parsedItems) {
        this.parsedItems = parsedItems
    }

    void validate(CreateParsedEvent createParsedItem) {
        if (createParsedItem.id <= 0) {
            throw new DomainException('PARSED_ITEM_ID_NO_PRESENT', 'Parsed item id is not present')
        }

        if (parsedItems.findOne(createParsedItem.id) != null) {
            throw new DomainException('PARSED_ITEM_ALREADY_PRESENT',
                    "Parsed item with id = ${createParsedItem.id} is already present"
            )
        }
    }
}
