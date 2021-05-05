package modzo.compare.query.core.domain.item.commands.property

import modzo.compare.command.core.domain.item.ItemProperties
import modzo.compare.command.core.domain.item.Items
import modzo.compare.command.core.domain.item.PropertyDescriptors
import modzo.compare.command.core.domain.validators.DomainException
import modzo.compare.query.core.domain.item.ItemProperties
import modzo.compare.query.core.domain.item.Items
import modzo.compare.query.core.domain.item.PropertyDescriptors
import modzo.compare.query.core.domain.validators.DomainException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AddItemPropertyValidator {

    private ItemProperties properties

    private Items items

    private PropertyDescriptors propertyDescriptors

    AddItemPropertyValidator(ItemProperties properties, Items items, PropertyDescriptors propertyDescriptors) {
        this.properties = properties
        this.items = items
        this.propertyDescriptors = propertyDescriptors
    }

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

    }
}
