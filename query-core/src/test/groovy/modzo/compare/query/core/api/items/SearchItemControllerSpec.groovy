package modzo.compare.query.core.api.items

import groovy.json.JsonSlurper
import modzo.compare.query.core.api.utils.TestContext
import modzo.compare.query.core.domain.item.commands.item.CreateItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class SearchItemControllerSpec extends Specification {

    @Autowired
    private TestContext testContext

    def 'should return saved item'() {
        given:
            CreateItem item = testContext.item()
            String uniqueId = testContext.createItem(item)
        when:
            ResponseEntity<String> response = testContext.searchItems(item.name)
        then:
            response.statusCode == HttpStatus.OK

            def body = new JsonSlurper().parseText(response.body)
            def retrieveItem = body._embedded.itemBeanList.find { it.uniqueId == uniqueId }
            retrieveItem.name == item.name
    }
}
