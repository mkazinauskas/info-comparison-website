package modzo.compare.command.api.compare

import modzo.compare.command.core.domain.DummyItem
import modzo.compare.command.core.domain.item.Comparisons
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ComparisonControllerSpec extends Specification {
    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    DummyItem dummyItem

    @Autowired
    Comparisons comparisons

    def 'should correctly save comparison data'() {
        given:
            ComparisonRequest comparisonRequest = new ComparisonRequest(
                    firstItemUniqueId: dummyItem.create(),
                    secondItemUniqueId: dummyItem.create()
            )
        when:
            ResponseEntity response = restTemplate.postForEntity('/comparison', comparisonRequest, String)
        then:
            response.statusCode == HttpStatus.CREATED
        and:
            def comparison = comparisons.findByFirstItemUniqueIdAndSecondItemUniqueId(
                    comparisonRequest.firstItemUniqueId,
                    comparisonRequest.secondItemUniqueId
            ).get()
            comparison.sequence % 10 == 0
            comparison.firstItemUniqueId == comparisonRequest.firstItemUniqueId
            comparison.secondItemUniqueId == comparisonRequest.secondItemUniqueId
            comparison.uniqueId.length() == 40

    }
}
