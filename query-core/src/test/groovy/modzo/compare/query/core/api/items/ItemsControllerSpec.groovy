package modzo.compare.query.core.api.items

import groovy.json.JsonSlurper
import modzo.compare.query.core.api.utils.TestContext
import modzo.compare.query.core.domain.item.commands.DummyItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ItemsControllerSpec extends Specification {

    @Autowired
    private TestContext testContext

    @Autowired
    private TestRestTemplate testRestTemplate

    @Autowired
    private DummyItem dummyItem

    def 'should return saved item'() {
        given:
            dummyItem.create()
        when:
            ResponseEntity<String> response = testContext.retrieveItems()
        then:
            response.statusCode == HttpStatus.OK

            def body = new JsonSlurper().parseText(response.body)
            body._embedded.itemBeanList.findAll { it.uniqueId.size() > 0 }
    }
}
