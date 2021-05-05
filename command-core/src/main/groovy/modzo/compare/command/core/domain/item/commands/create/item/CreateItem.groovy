package modzo.compare.command.core.domain.item.commands.create.item

import modzo.compare.query.core.domain.events.ItemCreated

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic

class CreateItem {

    String uniqueId = randomAlphabetic(40)

    String name

    String description

    String identifier

    ItemCreated asEvent() {
        return new ItemCreated(uniqueId, identifier, name, description)
    }
}
