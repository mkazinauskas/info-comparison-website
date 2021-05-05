package modzo.compare.command.core.domain.parsed_item.command

import groovy.transform.PackageScope
import modzo.compare.command.core.domain.parsed_item.ParsedItems
import modzo.compare.command.core.domain.validators.DomainException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@PackageScope
class CreateParsedItemValidator {

    @Autowired
    private ParsedItems parsedItems

    void validate(CreateParsedItem createParsedItem) {
        if(!createParsedItem.identifier){
            throw new DomainException('PARSED_ITEM_IDENTIFIER_SHOULD_EXIST',
                    "Parsed item identifier should exist")
        }

        if(!createParsedItem.status){
            throw new DomainException('PARSED_ITEM_STATUS_SHOULD_EXIST',
                    "Parsed item status should exist")
        }

        if(!createParsedItem.itemUniqueId){
            throw new DomainException('PARSED_ITEM_UNIQUE_ID_HAS_TO_BE_NOT_NULL',
                    "Parsed item unique id has to be not null")
        }
    }
}
