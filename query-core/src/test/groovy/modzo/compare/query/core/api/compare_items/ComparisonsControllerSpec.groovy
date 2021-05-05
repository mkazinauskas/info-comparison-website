package modzo.compare.query.core.api.compare_items

import modzo.compare.query.core.api.utils.TestContext
import modzo.compare.query.core.domain.item.Comparisons
import modzo.compare.query.core.domain.item.commands.DummyComparison
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.PagedResources
import org.springframework.hateoas.Resources
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.OK

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ComparisonsControllerSpec extends Specification {
    @Autowired
    private DummyComparison dummyComparison

    @Autowired
    private TestContext testContext

    @Autowired
    private Comparisons comparisons

    def 'should get latest comparisons'() {
        given:
            String comparisonUniqueId = dummyComparison.create()
        when:
            ResponseEntity<String> response = testContext.getComparisons(0, 100)
        then:
            response.statusCode == OK
        and:
            response.body.contains(comparisonUniqueId)
    }
}
