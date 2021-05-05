package modzo.compare.command.core.domain.item.commands.create.property

import modzo.compare.command.core.domain.item.ItemProperties
import modzo.compare.command.core.domain.item.Items
import modzo.compare.command.core.domain.item.PropertyDescriptors
import modzo.compare.command.core.domain.validators.DomainException
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.StringUtils.isBlank

@Component
class AddItemPropertyValidator {

    @Autowired
    private ItemProperties properties

    @Autowired
    private Items items

    @Autowired
    private PropertyDescriptors propertyDescriptors

    void validate(AddItemProperty command) {
        if (!command.uniqueId) {
            throw new DomainException('ITEMS_PROPERTY_UNIQUE_ID_HAS_TO_BE_PRESENT', "Item property unique id has to be present")
        }

        if (!items.findByUniqueId(command.itemUniqueId).isPresent()) {
            throw new DomainException('ITEM_WITH_UNIQUE_ID_DOES_NOT_EXIST', "Item with unique id does not exists")
        }

        if (!propertyDescriptors.findByUniqueId(command.propertyDescriptorUniqueId).isPresent()) {
            throw new DomainException('PROPERTY_DESCRIPTOR_HAS_TO_BE_PRESENT', "Property descriptor with such unique id is not present")
        }

        if (isBlank(command.value)) {
            throw new DomainException('ITEM_NAME_HAS_TO_BE_NOT_BLANK', "Item name has to be not blank")
        }

    }
}
