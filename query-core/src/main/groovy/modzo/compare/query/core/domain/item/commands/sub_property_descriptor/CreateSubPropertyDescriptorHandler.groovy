package modzo.compare.query.core.domain.item.commands.sub_property_descriptor

import modzo.compare.query.core.domain.events.services.EventPublisher
import modzo.compare.query.core.domain.item.PropertyDescriptor
import modzo.compare.query.core.domain.item.PropertyDescriptors
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreateSubPropertyDescriptorHandler {

    private CreateSubPropertyDescriptorValidator validator

    private PropertyDescriptors propertyDescriptors

    CreateSubPropertyDescriptorHandler(CreateSubPropertyDescriptorValidator validator,
                                       PropertyDescriptors propertyDescriptors) {
        this.validator = validator
        this.propertyDescriptors = propertyDescriptors
    }

    void handle(CreateSubPropertyDescriptor command) {
        validator.validate(command)

        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
                uniqueId: command.uniqueId,
                parentPropertyDescriptorUniqueId: command.parentPropertyDescriptorUniqueId,
                sequence: command.sequence,
                name: command.name,
                details: command.details
        )

        propertyDescriptors.save(propertyDescriptor)
    }
}
