package modzo.compare.command.core.domain.item.commands.create.item

import groovy.transform.PackageScope
import modzo.compare.command.core.domain.item.Items
import modzo.compare.command.core.domain.validators.DomainException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static liquibase.util.StringUtils.isEmpty
import static modzo.compare.command.core.tools.Text.clean

@Component
@PackageScope
class CreateItemValidator {

    @Autowired
    private Items items

    void validate(CreateItem createItem) {
        if (isEmpty(clean(createItem.name))) {
            throw new DomainException('ITEM_NAME_IS_EMPTY', "Item name `$createItem.name` cannot be empty")
        }
        if (isEmpty(createItem.identifier)) {
            throw new DomainException('ITEM_IDENTIFIER_NOT_PRESENT', "Item `$createItem.name` identifier not present")
        }
        items.findByName(createItem.name).ifPresent {
            item -> throw new DomainException('ITEM_ALREADY_EXISTS', "Item with name `$createItem.name` already exists")
        }
    }
}
