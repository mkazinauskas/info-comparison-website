package modzo.compare.query.core.domain.item.commands.item

import modzo.compare.query.core.domain.item.Item
import modzo.compare.query.core.domain.item.Items
import org.apache.commons.lang.RandomStringUtils
import org.apache.lucene.queryparser.classic.QueryParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreateItemHandlerSpec extends Specification {

    @Autowired
    private CreateItemHandler testTarget

    @Autowired
    private Items items

    def 'should create new item'() {
        given:
            CreateItem command = new CreateItem().with {
                uniqueId = RandomStringUtils.randomAlphanumeric(40)
                name = RandomStringUtils.randomAlphanumeric(50)
                description = RandomStringUtils.randomAlphanumeric(50)
                it
            }
        when:
            testTarget.handle(command)
        then:
            Item item = items.findByUniqueId(command.uniqueId).get()
            item.name == command.name
            item.description == command.description
    }
}
