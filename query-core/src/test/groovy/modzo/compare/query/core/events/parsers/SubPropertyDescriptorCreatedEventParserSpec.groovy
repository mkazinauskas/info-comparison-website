package modzo.compare.query.core.events.parsers

import com.fasterxml.jackson.databind.ObjectMapper
import modzo.compare.query.core.domain.events.ItemPropertyAdded
import modzo.compare.query.core.domain.events.SubPropertyDescriptorCreated
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.query.core.domain.item.PropertyDescriptor
import modzo.compare.query.core.domain.item.PropertyDescriptors
import modzo.compare.query.core.domain.item.commands.DummyPropertyDescriptor
import org.apache.commons.lang.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class SubPropertyDescriptorCreatedEventParserSpec extends Specification {

    @Autowired
    private SubPropertyDescriptorCreatedEventParser subPropertyDescriptorCreatedEventParser

    @Autowired
    private PropertyDescriptors propertyDescriptors

    @Autowired
    private DummyPropertyDescriptor dummyPropertyDescriptor

    def 'should save added item property to database'() {
        given:
            String subPropertyUniqueId = RandomStringUtils.randomAlphanumeric(40)
        and:
            String parentPropertyDescriptorUniqueId = dummyPropertyDescriptor.create()
        and:
            EventService.RetrievedEvent retrievedEvent = new EventService.RetrievedEvent(
                    uniqueId: subPropertyUniqueId,
                    type: ItemPropertyAdded.NAME,
                    data: new ObjectMapper().writeValueAsString(
                            new SubPropertyDescriptorCreated(
                                    uniqueId: subPropertyUniqueId,
                                    parentPropertyDescriptorUniqueId: parentPropertyDescriptorUniqueId,
                                    sequence: 1,
                                    name: 'name some value',
                                    details: 'details new'
                            )
                    )
            )
        when:
            subPropertyDescriptorCreatedEventParser.parse(retrievedEvent)
        then:
            PropertyDescriptor propertyDescriptor = propertyDescriptors.findByUniqueId(subPropertyUniqueId).get()
            propertyDescriptor.uniqueId == subPropertyUniqueId
            propertyDescriptor.parentPropertyDescriptorUniqueId == parentPropertyDescriptorUniqueId
            propertyDescriptor.sequence == 1
            propertyDescriptor.name == 'name some value'
            propertyDescriptor.details == 'details new'
    }
}


