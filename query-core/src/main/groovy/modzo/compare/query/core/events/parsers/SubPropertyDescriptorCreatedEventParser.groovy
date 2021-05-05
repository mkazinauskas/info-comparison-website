package modzo.compare.query.core.events.parsers

import modzo.compare.query.core.domain.events.Event
import modzo.compare.query.core.domain.events.PropertyDescriptorCreated
import modzo.compare.query.core.domain.events.SubPropertyDescriptorCreated
import modzo.compare.query.core.domain.events.services.EventDeserializer
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.query.core.domain.item.commands.property_descriptor.CreatePropertyDescriptor
import modzo.compare.query.core.domain.item.commands.sub_property_descriptor.CreateSubPropertyDescriptor
import modzo.compare.query.core.domain.item.commands.sub_property_descriptor.CreateSubPropertyDescriptorHandler
import org.springframework.stereotype.Component

@Component
class SubPropertyDescriptorCreatedEventParser implements EventParser {


    private final EventDeserializer eventDeserializer
    private final CreateSubPropertyDescriptorHandler createSubPropertyDescriptorHandler

    SubPropertyDescriptorCreatedEventParser(EventDeserializer eventDeserializer,
                                            CreateSubPropertyDescriptorHandler createSubPropertyDescriptorHandler) {
        this.eventDeserializer = eventDeserializer
        this.createSubPropertyDescriptorHandler = createSubPropertyDescriptorHandler
    }

    @Override
    String type() {
        return SubPropertyDescriptorCreated.NAME
    }

    @Override
    void parse(EventService.RetrievedEvent event) {
        SubPropertyDescriptorCreated subPropertyDescriptorCreated = eventDeserializer.deserialize(event, SubPropertyDescriptorCreated)
        createSubPropertyDescriptorHandler.handle(
                new CreateSubPropertyDescriptor(
                        uniqueId: subPropertyDescriptorCreated.uniqueId,
                        parentPropertyDescriptorUniqueId: subPropertyDescriptorCreated.parentPropertyDescriptorUniqueId,
                        sequence: subPropertyDescriptorCreated.sequence,
                        name: subPropertyDescriptorCreated.name,
                        details: subPropertyDescriptorCreated.details
                )
        )

    }
}
