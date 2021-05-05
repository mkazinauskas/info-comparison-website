package modzo.compare.query.core.events.parsers

import com.fasterxml.jackson.databind.ObjectMapper
import modzo.compare.query.core.domain.events.PropertyDescriptorCreated
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.query.core.domain.item.ItemProperty
import modzo.compare.query.core.domain.item.PropertyDescriptor
import modzo.compare.query.core.domain.item.PropertyDescriptors
import org.apache.commons.lang.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PropertyDescriptorCreatedEventParserSpec extends Specification {

    @Autowired
    private PropertyDescriptorCreatedEventParser propertyDescriptorCreatedEventParser

    @Autowired
    private PropertyDescriptors propertyDescriptors

    def 'should save added item property to database'() {
        given:
            String propertyDescriptorUniqueId = RandomStringUtils.randomAlphanumeric(30)
        and:
            EventService.RetrievedEvent retrievedEvent = new EventService.RetrievedEvent(
                    uniqueId: propertyDescriptorUniqueId,
                    type: PropertyDescriptorCreated.NAME,
                    data: new ObjectMapper().writeValueAsString(
                            new PropertyDescriptorCreated(
                                    uniqueId: propertyDescriptorUniqueId,
                                    sequence: 1,
                                    name: 'my random property 300',
                                    details: 'details 159'
                            )
                    )
            )
        when:
            propertyDescriptorCreatedEventParser.parse(retrievedEvent)
        then:
            PropertyDescriptor propertyDescriptor = propertyDescriptors.findByUniqueId(propertyDescriptorUniqueId).get()
            propertyDescriptor.uniqueId == propertyDescriptorUniqueId
            propertyDescriptor.sequence == 1
            propertyDescriptor.name == 'my random property 300'
            propertyDescriptor.details == 'details 159'
            propertyDescriptor.parentPropertyDescriptorUniqueId == null
    }
}


