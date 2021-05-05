package modzo.compare.query.core.events.parsers

import modzo.compare.query.core.domain.events.services.EventDeserializer
import modzo.compare.query.core.domain.events.ItemCreated
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.query.core.domain.item.commands.item.CreateItem
import modzo.compare.query.core.domain.item.commands.item.CreateItemHandler
import org.springframework.stereotype.Component

@Component
class ItemCreatedEventParser implements EventParser {

    private final CreateItemHandler createItemHandler
    private final EventDeserializer eventDeserializer

    ItemCreatedEventParser(CreateItemHandler createItemHandler, EventDeserializer eventDeserializer) {
        this.createItemHandler = createItemHandler
        this.eventDeserializer = eventDeserializer
    }

    @Override
    String type() {
        return ItemCreated.NAME
    }

    @Override
    void parse(EventService.RetrievedEvent event) {
        ItemCreated itemCreated = eventDeserializer.deserialize(event, ItemCreated)
        createItemHandler.handle(
                new CreateItem(
                        uniqueId: itemCreated.uniqueId,
                        name: itemCreated.name,
                        description: itemCreated.description
                )
        )
    }
}
