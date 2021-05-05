package modzo.compare.query.core.domain.item.commands

import modzo.compare.query.core.domain.item.commands.property_descriptor.CreatePropertyDescriptor
import modzo.compare.query.core.domain.item.commands.property_descriptor.CreatePropertyDescriptorHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric
import static org.apache.commons.lang.RandomStringUtils.randomNumeric

@Component
class DummyPropertyDescriptor {
    @Autowired
    private CreatePropertyDescriptorHandler handler

    String create(String name = randomAlphanumeric(10),
                  String details = randomAlphanumeric(10)) {
        CreatePropertyDescriptor createPropertyDescriptor = new CreatePropertyDescriptor(
                uniqueId: randomAlphanumeric(40),
                sequence: randomNumeric(5) as int,
                name: name,
                details: details
        )
        handler.handle(createPropertyDescriptor)

        return createPropertyDescriptor.uniqueId
    }
}
