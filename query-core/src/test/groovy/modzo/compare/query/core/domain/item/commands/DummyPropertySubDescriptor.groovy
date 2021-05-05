package modzo.compare.query.core.domain.item.commands

import modzo.compare.query.core.domain.item.commands.sub_property_descriptor.CreateSubPropertyDescriptor
import modzo.compare.query.core.domain.item.commands.sub_property_descriptor.CreateSubPropertyDescriptorHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric
import static org.apache.commons.lang.RandomStringUtils.randomNumeric

@Component
class DummyPropertySubDescriptor {
    @Autowired
    private CreateSubPropertyDescriptorHandler handler

    String create(
            String parentUniqueId,
            String name = randomAlphanumeric(10),
            String details = randomAlphanumeric(10)) {
        CreateSubPropertyDescriptor createSubPropertyDescriptor = new CreateSubPropertyDescriptor(
                uniqueId: randomAlphanumeric(40),
                parentPropertyDescriptorUniqueId: parentUniqueId,
                sequence: randomNumeric(5) as int,
                name: name,
                details: details
        )
        handler.handle(createSubPropertyDescriptor)

        return createSubPropertyDescriptor.uniqueId
    }
}
