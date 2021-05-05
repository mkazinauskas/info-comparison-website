package modzo.compare.command.core.domain.item.commands.create.property_descriptor

import groovy.transform.CompileStatic
import modzo.compare.command.core.domain.item.PropertyDescriptor
import modzo.compare.query.core.domain.events.PropertyDescriptorCreated

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

@CompileStatic
class CreatePropertyDescriptor {
    final String uniqueId = randomAlphanumeric(40)

    String name

    String details

    PropertyDescriptorCreated asEvent(PropertyDescriptor propertyDescriptor) {
        return new PropertyDescriptorCreated(
                uniqueId,
                propertyDescriptor.sequence,
                name,
                details
        )
    }
}
