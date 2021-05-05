package modzo.compare.query.core.domain.parser.command

import modzo.compare.query.core.domain.parser.ParsedEvents
import modzo.compare.query.core.domain.parser.services.LatestParsedEventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.apache.commons.lang.RandomStringUtils.randomNumeric
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreateParsedItemHandlerSpec extends Specification {

    @Autowired
    CreateParsedEventHandler createParsedItemHandler

    @Autowired
    ParsedEvents parsedEvents

    @Autowired
    LatestParsedEventService latestParsedEventService

    def 'should save parsed event'() {
        given:
            CreateParsedEvent request = new CreateParsedEvent(randomNumeric(10) as long)
        when:
            createParsedItemHandler.handle(request)
        then:
            parsedEvents.findOne(request.id) != null
    }

    def 'should retrieve latest parsed event'() {
        given:
            parsedEvents.deleteAll()
        and:
            createParsedItemHandler.handle(new CreateParsedEvent(9))
        when:
            createParsedItemHandler.handle(new CreateParsedEvent(10))
        then:
            latestParsedEventService.latestParsedEvent().get().id == 10
    }
}