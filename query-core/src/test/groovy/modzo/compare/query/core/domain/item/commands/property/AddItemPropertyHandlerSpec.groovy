package modzo.compare.query.core.domain.item.commands.property

import modzo.compare.query.core.domain.item.ItemProperties
import modzo.compare.query.core.domain.item.ItemProperty
import modzo.compare.query.core.domain.item.commands.DummyItem
import modzo.compare.query.core.domain.item.commands.DummyPropertyDescriptor
import org.apache.commons.lang.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AddItemPropertyHandlerSpec extends Specification {

    @Autowired
    private AddItemPropertyHandler testTarget;

    @Autowired
    private ItemProperties itemProperties

    @Autowired
    private DummyPropertyDescriptor dummyPropertyDescriptor

    @Autowired
    private DummyItem dummyItem

    def 'should add new property'() {
        given:
            AddItemProperty command = new AddItemProperty().with {
                uniqueId = RandomStringUtils.randomAlphanumeric(5)
                itemUniqueId = dummyItem.create()
                propertyDescriptorUniqueId = dummyPropertyDescriptor.create()
                value = 'best-value'
                it
            }
        when:
            testTarget.handle(command)
        then:
            ItemProperty itemProperty = itemProperties.findByUniqueId(command.uniqueId).get()
            itemProperty.itemUniqueId == command.itemUniqueId
            itemProperty.propertyDescriptorUniqueId == command.propertyDescriptorUniqueId
            itemProperty.value == 'best-value'

    }
}
