package modzo.compare.command.core.domain.item.commands.create.sub_property_descriptor

import groovy.transform.CompileStatic
import modzo.compare.command.core.domain.item.PropertyDescriptor
import modzo.compare.query.core.domain.events.SubPropertyDescriptorCreated

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

@CompileStatic
class CreateSubPropertyDescriptor {
    final String uniqueId = randomAlphanumeric(40)

    String parentPropertyDescriptorUniqueId

    String name

    String details

    SubPropertyDescriptorCreated asEvent(PropertyDescriptor propertyDescriptor) {
        return new SubPropertyDescriptorCreated(
                uniqueId,
                parentPropertyDescriptorUniqueId,
                propertyDescriptor.sequence,
                name,
                details
        )
    }
}
