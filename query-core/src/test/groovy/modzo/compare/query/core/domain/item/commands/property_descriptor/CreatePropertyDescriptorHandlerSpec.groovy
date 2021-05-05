package modzo.compare.query.core.domain.item.commands.property_descriptor

import modzo.compare.query.core.domain.item.PropertyDescriptor
import modzo.compare.query.core.domain.item.PropertyDescriptors
import org.apache.commons.lang.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreatePropertyDescriptorHandlerSpec extends Specification {
    @Autowired
    private CreatePropertyDescriptorHandler testTarget

    @Autowired
    private PropertyDescriptors propertyDescriptors

    def 'should persist new property descriptor'() {
        given:
            CreatePropertyDescriptor command = new CreatePropertyDescriptor(
                    uniqueId: RandomStringUtils.randomAlphanumeric(40),
                    sequence: 10,
                    name: 'test'
            )
        when:
            testTarget.handle(command)
        then:
            PropertyDescriptor result = propertyDescriptors.findByUniqueId(command.uniqueId).get()
            result.name == command.name
            result.sequence == 10
    }
}
