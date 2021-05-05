package modzo.compare.ui

import modzo.compare.ui.items.compare.CompareItemsClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.OK

@SpringBootTest(webEnvironment = RANDOM_PORT)
class RenderIndexPage extends Specification {

    @Autowired
    TestRestTemplate testRestTemplate

    @Autowired
    CompareItemsClient compareItemsClient

    @Unroll
    def 'Index page should contain `#string`'() {
        given:
            CompareItemsClientStubber.stub(compareItemsClient)
        when:
            ResponseEntity<String> response = testRestTemplate.getForEntity('/', String)
        then:
            response.statusCode == OK
        and:
            response.body.contains(string)
        where:
            string << [
                    'Compare',
                    'Compare mobile phones!',
                    '<a class="nav-link" href="/phones">All phones</a>'
            ]
    }
}
