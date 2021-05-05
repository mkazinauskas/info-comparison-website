package modzo.compare.command.core.domain.item.commands.create.item

import modzo.compare.command.core.domain.item.Item
import modzo.compare.command.core.domain.item.Items
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreateItemSpec extends Specification {

    @Autowired
    private CreateItemHandler testTarget

    @Autowired
    private Items items

    @Unroll
    def 'should create new item'() {
        given:
            CreateItem command = new CreateItem(
                    name: name,
                    description: randomAlphanumeric(50),
                    identifier: randomAlphanumeric(10)
            )
        when:
            testTarget.handle(command)
        then:
            Item item = items.findByUniqueId(command.uniqueId).get()
            item.name == resultName
            item.description == command.description
            item.identifier == command.identifier
        where:
            name              || resultName
            'test Name 300 @' || 'test Name 300'
            '!@#$%^&*()_+\'t' || '\'t'
    }
}
