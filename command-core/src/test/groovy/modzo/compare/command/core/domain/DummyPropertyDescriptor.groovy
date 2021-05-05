package modzo.compare.command.core.domain

import modzo.compare.command.core.domain.item.commands.create.property_descriptor.CreatePropertyDescriptor
import modzo.compare.command.core.domain.item.commands.create.property_descriptor.CreatePropertyDescriptorHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
class DummyPropertyDescriptor {

    @Autowired
    private CreatePropertyDescriptorHandler createPropertyDescriptorHandler

    static CreatePropertyDescriptor newPropertyDescriptor() {
        return new CreatePropertyDescriptor(
                name: randomAlphanumeric(30)
        )
    }

    String create(CreatePropertyDescriptor command = newPropertyDescriptor()) {
        createPropertyDescriptorHandler.handle(command)
        return command.uniqueId
    }
}
