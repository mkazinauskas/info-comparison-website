package modzo.compare.query.core.domain.item.commands.property_descriptor

import modzo.compare.query.core.domain.item.PropertyDescriptor
import modzo.compare.query.core.domain.item.PropertyDescriptors
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreatePropertyDescriptorHandler {

    @Autowired
    private CreatePropertyDescriptorValidator validator

    @Autowired
    private PropertyDescriptors propertyDescriptors

    void handle(CreatePropertyDescriptor command) {
        validator.validate(command)

        PropertyDescriptor propertyDescriptor = new PropertyDescriptor().with {
            uniqueId = command.uniqueId
            details = command.details
            sequence = command.sequence
            name = command.name
            it
        }

        propertyDescriptors.save(propertyDescriptor)
    }
}
