package modzo.compare.command.core.domain.item.commands.create.sub_property_descriptor

import modzo.compare.command.core.domain.item.PropertyDescriptor
import modzo.compare.command.core.domain.item.PropertyDescriptors
import modzo.compare.query.core.domain.events.services.EventPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.transaction.Transactional

@Component
class CreateSubPropertyDescriptorHandler {

    @Autowired
    private CreateSubPropertyDescriptorValidator validator

    @Autowired
    private PropertyDescriptors propertyDescriptors

    @Autowired
    private EventPublisher eventPublisher

    @Transactional
    void handle(CreateSubPropertyDescriptor command) {
        validator.validate(command)

        PropertyDescriptor propertyDescriptor = new PropertyDescriptor().with {
            uniqueId = command.uniqueId
            parentPropertyDescriptorUniqueId = command.parentPropertyDescriptorUniqueId
            sequence = (propertyDescriptors.count() + 1) * 10
            name = command.name
            details = command.details

            it
        }

        PropertyDescriptor savedPropertyDescriptor = propertyDescriptors.saveAndFlush(propertyDescriptor)

        eventPublisher.publish(command.asEvent(savedPropertyDescriptor))
    }
}
