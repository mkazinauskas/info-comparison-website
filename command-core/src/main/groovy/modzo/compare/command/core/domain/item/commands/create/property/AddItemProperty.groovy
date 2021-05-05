package modzo.compare.command.core.domain.item.commands.create.property

import modzo.compare.query.core.domain.events.ItemPropertyAdded

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

class AddItemProperty {
    String uniqueId = randomAlphanumeric(40)

    String itemUniqueId

    String propertyDescriptorUniqueId

    String value

    ItemPropertyAdded asEvent() {
        return new ItemPropertyAdded(
                uniqueId, itemUniqueId, propertyDescriptorUniqueId, value
        )
    }
}
