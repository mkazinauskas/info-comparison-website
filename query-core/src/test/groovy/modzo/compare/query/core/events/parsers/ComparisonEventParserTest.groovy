package modzo.compare.query.core.events.parsers

import com.fasterxml.jackson.databind.ObjectMapper
import modzo.compare.query.core.domain.events.ComparisonCreated
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.query.core.domain.item.Comparison
import modzo.compare.query.core.domain.item.Comparisons
import org.apache.commons.lang.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ComparisonEventParserTest extends Specification {

    @Autowired
    private ComparisonCreatedEventParser comparisonCreatedEventParser

    @Autowired
    private Comparisons comparisons

    def 'should save create item to database'() {
        given:
            String comparisonUniqueId = RandomStringUtils.randomAlphanumeric(40)
            String firstItemUniqueId = RandomStringUtils.randomAlphanumeric(40)
            String secondItemUniqueId = RandomStringUtils.randomAlphanumeric(40)
        and:
            EventService.RetrievedEvent retrievedEvent = new EventService.RetrievedEvent(
                    uniqueId: comparisonUniqueId,
                    type: ComparisonCreated.NAME,
                    data: new ObjectMapper().writeValueAsString(
                            new ComparisonCreated(
                                    uniqueId: comparisonUniqueId,
                                    firstItemUniqueId: firstItemUniqueId,
                                    secondItemUniqueId: secondItemUniqueId
                            )
                    )
            )
        when:
            comparisonCreatedEventParser.parse(retrievedEvent)
        then:
            Comparison comparison = comparisons.findByUniqueId(comparisonUniqueId).get()
            comparison.uniqueId == comparisonUniqueId
            comparison.firstItemUniqueId == firstItemUniqueId
            comparison.secondItemUniqueId == secondItemUniqueId
    }
}


