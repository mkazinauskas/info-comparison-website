package modzo.compare.command.core.domain.item.commands.create.property

import modzo.compare.command.core.domain.DummyItem
import modzo.compare.command.core.domain.DummyPropertyDescriptor
import modzo.compare.command.core.domain.item.ItemProperties
import modzo.compare.command.core.domain.item.ItemProperty
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AddItemPropertyHandlerSpec extends Specification {
    @Autowired
    AddItemPropertyHandler addItemPropertyHandler

    @Autowired
    DummyItem dummyItem

    @Autowired
    DummyPropertyDescriptor dummyPropertyDescriptor

    @Autowired
    ItemProperties itemProperties

    def 'should add item property'() {
        given:
            String dummyItemId = dummyItem.create()
        and:
            String dummyPropertyDesriptorId = dummyPropertyDescriptor.create()
        and:
            AddItemProperty command = new AddItemProperty(
                    itemUniqueId: dummyItemId,
                    propertyDescriptorUniqueId: dummyPropertyDesriptorId,
                    value: RandomStringUtils.randomAlphanumeric(40)
            )
        when:
            addItemPropertyHandler.handle(command)
        then:
            ItemProperty property = itemProperties.findByUniqueId(command.uniqueId).get()
            property.itemUniqueId == command.itemUniqueId
            property.propertyDescriptorUniqueId == command.propertyDescriptorUniqueId
            property.value == command.value
    }
}
