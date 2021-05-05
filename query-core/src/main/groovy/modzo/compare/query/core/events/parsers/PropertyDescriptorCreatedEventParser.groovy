package modzo.compare.query.core.events.parsers

import modzo.compare.query.core.domain.events.PropertyDescriptorCreated
import modzo.compare.query.core.domain.events.services.EventDeserializer
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.query.core.domain.item.commands.property_descriptor.CreatePropertyDescriptor
import modzo.compare.query.core.domain.item.commands.property_descriptor.CreatePropertyDescriptorHandler
import org.springframework.stereotype.Component

@Component
class PropertyDescriptorCreatedEventParser implements EventParser {
    private final EventDeserializer eventDeserializer
    private final CreatePropertyDescriptorHandler createPropertyDescriptorHandler

    PropertyDescriptorCreatedEventParser(EventDeserializer eventDeserializer,
                                         CreatePropertyDescriptorHandler createPropertyDescriptorHandler) {
        this.eventDeserializer = eventDeserializer
        this.createPropertyDescriptorHandler = createPropertyDescriptorHandler
    }

    @Override
    String type() {
        return PropertyDescriptorCreated.NAME
    }

    @Override
    void parse(EventService.RetrievedEvent event) {
        PropertyDescriptorCreated propertyDescriptorCreated = eventDeserializer.deserialize(event, PropertyDescriptorCreated)
        createPropertyDescriptorHandler.handle(
                new CreatePropertyDescriptor(
                        uniqueId: propertyDescriptorCreated.uniqueId,
                        sequence: propertyDescriptorCreated.sequence,
                        name: propertyDescriptorCreated.name,
                        details: propertyDescriptorCreated.details
                )
        )
    }
}
