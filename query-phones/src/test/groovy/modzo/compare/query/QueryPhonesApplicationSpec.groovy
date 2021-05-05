package modzo.compare.query

import modzo.compare.query.core.domain.item.Items
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class QueryPhonesApplicationSpec extends Specification {

    @Autowired
    Items items

    def 'should loan context'() {
        expect:
            items.count() >= 0
    }
}
