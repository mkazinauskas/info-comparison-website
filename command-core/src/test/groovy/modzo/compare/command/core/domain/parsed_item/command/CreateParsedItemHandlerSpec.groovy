package modzo.compare.command.core.domain.parsed_item.command

import modzo.compare.command.core.domain.parsed_item.ParsedItem
import modzo.compare.command.core.domain.parsed_item.ParsedItems
import modzo.compare.command.core.domain.validators.DomainException
import modzo.compare.query.core.domain.events.services.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import static java.util.Optional.empty
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric
import static org.mockito.Matchers.any
import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreateParsedItemHandlerSpec extends Specification {
    @Autowired
    CreateParsedItemHandler parsedItemHandler

    @Autowired
    ParsedItems parsedItems

    @Autowired
    EventService eventService

    def 'should create parsed item with success status'() {
        given:
            CreateParsedItem parsedItem = new CreateParsedItem(
                    itemUniqueId: Optional.of(randomAlphanumeric(40)),
                    identifier: randomNumeric(5),
                    status: ParsedItem.Status.SUCCESS
            )
        expect:
            parsedItem.uniqueId.length() == 40
        when:
            parsedItemHandler.handle(parsedItem)
        then:
            ParsedItem savedParsedItem = parsedItems.findByUniqueId(parsedItem.uniqueId).get()
            savedParsedItem.itemUniqueId == parsedItem.itemUniqueId
            savedParsedItem.identifier == parsedItem.identifier
            savedParsedItem.status == parsedItem.status
    }

    def 'should create parsed item with failed status'() {
        given:
            CreateParsedItem parsedItem = new CreateParsedItem(
                    itemUniqueId: empty(),
                    identifier: randomNumeric(5),
                    status: ParsedItem.Status.FAILED
            )
        when:
            parsedItemHandler.handle(parsedItem)
        then:
            ParsedItem savedParsedItem = parsedItems.findByUniqueId(parsedItem.uniqueId).get()
            savedParsedItem.itemUniqueId == empty()
            savedParsedItem.identifier == parsedItem.identifier
            savedParsedItem.status == parsedItem.status
    }

    @Unroll
    def 'should fail to create parsed item because of #expectedError'() {
        when:
            parsedItemHandler.handle(item)
        then:
            DomainException exception = thrown(DomainException)
            exception.id == expectedError
        and:
            !parsedItems.findByUniqueId(item.uniqueId).isPresent()
        and:
            verify(eventService, times(0)).publish(any(EventService.Event))
        where:
            item               || expectedError
            noStatus()         || 'PARSED_ITEM_STATUS_SHOULD_EXIST'
            noIdentifier()     || 'PARSED_ITEM_IDENTIFIER_SHOULD_EXIST'
            nullItemUniqueId() || 'PARSED_ITEM_UNIQUE_ID_HAS_TO_BE_NOT_NULL'
    }

    private static CreateParsedItem noStatus() {
        new CreateParsedItem(
                itemUniqueId: empty(),
                identifier: randomNumeric(5),
                status: null
        )
    }

    private static CreateParsedItem noIdentifier() {
        new CreateParsedItem(
                itemUniqueId: empty(),
                identifier: null,
                status: ParsedItem.Status.FAILED
        )
    }

    private static CreateParsedItem nullItemUniqueId() {
        new CreateParsedItem(
                itemUniqueId: null,
                identifier: randomNumeric(5),
                status: ParsedItem.Status.FAILED
        )
    }
}
