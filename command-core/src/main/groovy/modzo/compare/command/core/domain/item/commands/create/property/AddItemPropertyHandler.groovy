package modzo.compare.command.core.domain.item.commands.create.property

import modzo.compare.query.core.domain.events.services.EventPublisher
import modzo.compare.command.core.domain.item.ItemProperties
import modzo.compare.command.core.domain.item.ItemProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.transaction.Transactional

@Component
class AddItemPropertyHandler {

    @Autowired
    private ItemProperties properties

    @Autowired
    private EventPublisher eventPublisher

    @Autowired
    private AddItemPropertyValidator validator

    @Transactional
    void handle(AddItemProperty command){
        validator.validate(command)

        ItemProperty property = new ItemProperty().with {
            uniqueId = command.uniqueId
            itemUniqueId = command.itemUniqueId
            propertyDescriptorUniqueId = command.propertyDescriptorUniqueId
            value = command.value

            it
        }

        properties.saveAndFlush(property)

        eventPublisher.publish(command.asEvent())
    }
}
