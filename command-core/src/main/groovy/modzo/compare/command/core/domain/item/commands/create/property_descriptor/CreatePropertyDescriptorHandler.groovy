package modzo.compare.command.core.domain.item.commands.create.property_descriptor

import modzo.compare.query.core.domain.events.services.EventPublisher
import modzo.compare.command.core.domain.item.PropertyDescriptor
import modzo.compare.command.core.domain.item.PropertyDescriptors
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.transaction.Transactional

@Component
class CreatePropertyDescriptorHandler {

    @Autowired
    private CreatePropertyDescriptorValidator validator

    @Autowired
    private PropertyDescriptors propertyDescriptors

    @Autowired
    private EventPublisher eventPublisher

    @Transactional
    void handle(CreatePropertyDescriptor command) {
        validator.validate(command)

        PropertyDescriptor propertyDescriptor = new PropertyDescriptor().with {
            uniqueId = command.uniqueId
            sequence = (propertyDescriptors.count() + 1) * 10
            name = command.name
            details = command.details

            it
        }

        PropertyDescriptor savedPropertyDescriptor = propertyDescriptors.saveAndFlush(propertyDescriptor)

        eventPublisher.publish(command.asEvent(savedPropertyDescriptor))
    }
}
