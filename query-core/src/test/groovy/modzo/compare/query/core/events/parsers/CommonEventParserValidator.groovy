package modzo.compare.query.core.events.parsers

import modzo.compare.query.core.domain.events.Event
import org.reflections.Reflections
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CommonEventParserValidator extends Specification {
    @Autowired
    private List<EventParser> eventParsers

    @Unroll
    def '`#eventName` event should have parser'() {
        expect:
            eventParsers.find { it.type() == eventName }
        where:
            eventName << new Reflections('modzo.compare')
                    .getSubTypesOf(Event)
                    .collect { it.newInstance().topic() }
    }
}
