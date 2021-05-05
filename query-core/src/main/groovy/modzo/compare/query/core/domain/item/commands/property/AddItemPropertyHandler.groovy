package modzo.compare.query.core.domain.item.commands.property

import modzo.compare.query.core.domain.item.ItemProperties
import modzo.compare.query.core.domain.item.ItemProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AddItemPropertyHandler {

    @Autowired
    private ItemProperties properties

    void handle(AddItemProperty command){
        ItemProperty property = new ItemProperty().with {
            uniqueId = command.uniqueId
            itemUniqueId = command.itemUniqueId
            propertyDescriptorUniqueId = command.propertyDescriptorUniqueId
            value = command.value

            it
        }

        properties.save(property)

    }
}
