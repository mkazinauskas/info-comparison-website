package modzo.compare.command.core.domain.item.commands.create.item

import modzo.compare.command.core.domain.item.Item
import modzo.compare.command.core.domain.item.Items
import modzo.compare.command.core.tools.Text
import modzo.compare.query.core.domain.events.services.EventPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreateItemHandler {

    @Autowired
    private Items items

    @Autowired
    private CreateItemValidator validator

    @Autowired
    private EventPublisher eventPublisher

    @Transactional
    void handle(CreateItem createItem) {
        validator.validate(createItem)

        Item item = new Item().with {
            uniqueId = createItem.uniqueId
            identifier = createItem.identifier
            name = Text.clean(createItem.name)
            description = createItem.description

            it
        }

        items.saveAndFlush(item)
        eventPublisher.publish(createItem.asEvent())
    }
}
