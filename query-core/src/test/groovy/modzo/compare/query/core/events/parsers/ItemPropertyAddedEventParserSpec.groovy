package modzo.compare.query.core.events.parsers

import com.fasterxml.jackson.databind.ObjectMapper
import modzo.compare.query.core.domain.events.ItemPropertyAdded
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.query.core.domain.item.Item
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
class ItemPropertyAddedEventParserSpec extends Specification {

    @Autowired
    private ItemPropertyAddedEventParser itemCreatedEventParser

    @Autowired
    private ItemProperties itemProperties

    @Autowired
    private DummyItem dummyItem

    @Autowired
    private DummyPropertyDescriptor dummyPropertyDescriptor

    def 'should save added item property to database'() {
        given:
            String itemPropertyUniqueId = RandomStringUtils.randomAlphanumeric(30)
        and:
            String itemUniqueId = dummyItem.create()
        and:
            String propertyDescriptorUniqueId = dummyPropertyDescriptor.create()
        and:
            EventService.RetrievedEvent retrievedEvent = new EventService.RetrievedEvent(
                    uniqueId: itemPropertyUniqueId,
                    type: ItemPropertyAdded.NAME,
                    data: new ObjectMapper().writeValueAsString(
                            new ItemPropertyAdded(
                                    uniqueId: itemPropertyUniqueId,
                                    itemUniqueId: itemUniqueId,
                                    propertyDescriptorUniqueId: propertyDescriptorUniqueId,
                                    value: 'my random value'
                            )
                    )
            )
        when:
            itemCreatedEventParser.parse(retrievedEvent)
        then:
            ItemProperty itemProperty = itemProperties.findByUniqueId(itemPropertyUniqueId).get()
            itemProperty.uniqueId == itemPropertyUniqueId
            itemProperty.itemUniqueId == itemUniqueId
            itemProperty.propertyDescriptorUniqueId == propertyDescriptorUniqueId
            itemProperty.value == 'my random value'
    }
}


