package modzo.compare.query.core.events.parsers

import modzo.compare.query.core.domain.events.ItemPropertyAdded
import modzo.compare.query.core.domain.events.services.EventDeserializer
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.query.core.domain.item.commands.property.AddItemProperty
import modzo.compare.query.core.domain.item.commands.property.AddItemPropertyHandler
import org.springframework.stereotype.Component

@Component
class ItemPropertyAddedEventParser implements EventParser {

    private final EventDeserializer eventDeserializer
    private final AddItemPropertyHandler addItemPropertyHandler

    ItemPropertyAddedEventParser(EventDeserializer eventDeserializer,
                                 AddItemPropertyHandler addItemPropertyHandler) {
        this.eventDeserializer = eventDeserializer
        this.addItemPropertyHandler = addItemPropertyHandler
    }

    @Override
    String type() {
        return ItemPropertyAdded.NAME
    }

    @Override
    void parse(EventService.RetrievedEvent event) {
        ItemPropertyAdded addItemProperty = eventDeserializer.deserialize(event, ItemPropertyAdded)
        addItemPropertyHandler.handle(
                new AddItemProperty(
                        uniqueId: addItemProperty.uniqueId,
                        itemUniqueId: addItemProperty.itemUniqueId,
                        propertyDescriptorUniqueId: addItemProperty.propertyDescriptorUniqueId,
                        value: addItemProperty.value
                )
        )
    }
}
