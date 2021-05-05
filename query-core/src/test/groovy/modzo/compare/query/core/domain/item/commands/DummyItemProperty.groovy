package modzo.compare.query.core.domain.item.commands

import modzo.compare.query.core.domain.item.commands.property.AddItemProperty
import modzo.compare.query.core.domain.item.commands.property.AddItemPropertyHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

@Component
class DummyItemProperty {
    @Autowired
    private AddItemPropertyHandler handler

    String create(String itemUniqueId,
                  String propertyDescriptorUniqueId,
                  String value = randomAlphanumeric(10)) {
        AddItemProperty request = new AddItemProperty(
                uniqueId: randomAlphanumeric(40),
                itemUniqueId: itemUniqueId,
                propertyDescriptorUniqueId: propertyDescriptorUniqueId,
                value: value
        )
        handler.handle(request)

        return request.uniqueId
    }
}
