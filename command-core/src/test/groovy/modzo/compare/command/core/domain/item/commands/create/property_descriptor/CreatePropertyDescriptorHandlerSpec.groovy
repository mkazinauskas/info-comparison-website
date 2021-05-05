package modzo.compare.command.core.domain.item.commands.create.property_descriptor

import modzo.compare.command.core.domain.item.PropertyDescriptor
import modzo.compare.command.core.domain.item.PropertyDescriptors
import modzo.compare.command.core.domain.validators.DomainException
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreatePropertyDescriptorHandlerSpec extends Specification {

    @Autowired
    CreatePropertyDescriptorHandler createPropertyDescriptorHandler

    @Autowired
    PropertyDescriptors propertyDescriptors

    def 'should save property descriptor'() {
        given:
            CreatePropertyDescriptor createPropertyDescriptor = new CreatePropertyDescriptor(
                    name: RandomStringUtils.randomAlphanumeric(20),
                    details: RandomStringUtils.randomAlphanumeric(20)
            )
        when:
            createPropertyDescriptorHandler.handle(createPropertyDescriptor)
        then:
            PropertyDescriptor createdPropertyDescriptor = propertyDescriptors.findByUniqueId(createPropertyDescriptor.uniqueId).get()
            createdPropertyDescriptor.uniqueId == createPropertyDescriptor.uniqueId
            createdPropertyDescriptor.name == createPropertyDescriptor.name
            createdPropertyDescriptor.sequence > 0
            createdPropertyDescriptor.parentPropertyDescriptorUniqueId == null
            createdPropertyDescriptor.details == createdPropertyDescriptor.details
    }

    def 'should fail to save sub property descriptor for non existing name'() {
        given:
            CreatePropertyDescriptor propertyDescriptor = new CreatePropertyDescriptor(
                    name: null
            )
        when:
            createPropertyDescriptorHandler.handle(propertyDescriptor)
        then:
            DomainException exception = thrown(DomainException)
            exception.id == 'PROPERTY_DESCRIPTOR_NAME_HAS_TO_BE_PRESENT'
    }
}
