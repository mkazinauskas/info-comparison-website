package modzo.compare.query.core.events.parsers

import com.fasterxml.jackson.databind.ObjectMapper
import modzo.compare.query.core.domain.events.ItemCreated
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.query.core.domain.item.Item
import modzo.compare.query.core.domain.item.Items
import org.apache.commons.lang.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ItemCreatedEventParserSpec extends Specification {

    @Autowired
    private ItemCreatedEventParser itemCreatedEventParser

    @Autowired
    private Items items

    def 'should save create item to database'() {
        given:
            String itemUniqueId = RandomStringUtils.randomAlphanumeric(40)
        and:
            EventService.RetrievedEvent retrievedEvent = new EventService.RetrievedEvent(
                    uniqueId: itemUniqueId,
                    type: ItemCreated.NAME,
                    data: new ObjectMapper().writeValueAsString(
                            new ItemCreated(
                                    uniqueId: itemUniqueId,
                                    identifier: RandomStringUtils.randomAlphanumeric(6),
                                    name: 'Do Something',
                                    description: 'Description'
                            )
                    )
            )
        when:
            itemCreatedEventParser.parse(retrievedEvent)
        then:
            Item item = items.findByUniqueId(itemUniqueId).get()
            item.uniqueId == itemUniqueId
            item.name == 'Do Something'
            item.description == 'Description'
    }
}


