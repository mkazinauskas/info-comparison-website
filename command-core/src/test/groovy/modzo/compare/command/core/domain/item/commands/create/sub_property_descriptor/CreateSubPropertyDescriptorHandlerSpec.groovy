package modzo.compare.command.core.domain.item.commands.create.sub_property_descriptor

import modzo.compare.command.core.domain.DummyPropertyDescriptor
import modzo.compare.command.core.domain.item.PropertyDescriptor
import modzo.compare.command.core.domain.item.PropertyDescriptors
import modzo.compare.command.core.domain.validators.DomainException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreateSubPropertyDescriptorHandlerSpec extends Specification {

    @Autowired
    CreateSubPropertyDescriptorHandler createSubPropertyDescriptorHandler

    @Autowired
    PropertyDescriptors propertyDescriptors

    @Autowired
    DummyPropertyDescriptor dummyPropertyDescriptor

    def 'should save sub property descriptor'() {
        given:
            String propertyDescriptorUniqueId = dummyPropertyDescriptor.create()
        and:
            CreateSubPropertyDescriptor subPropertyDescriptor = new CreateSubPropertyDescriptor(
                    parentPropertyDescriptorUniqueId: propertyDescriptorUniqueId,
                    name: randomAlphanumeric(20)
            )
        when:
            createSubPropertyDescriptorHandler.handle(subPropertyDescriptor)
        then:
            PropertyDescriptor createdPropertyDescriptor = propertyDescriptors.findByUniqueId(subPropertyDescriptor.uniqueId).get()
            createdPropertyDescriptor.uniqueId == subPropertyDescriptor.uniqueId
            createdPropertyDescriptor.name == subPropertyDescriptor.name
            createdPropertyDescriptor.parentPropertyDescriptorUniqueId == subPropertyDescriptor.parentPropertyDescriptorUniqueId
            createdPropertyDescriptor.sequence > 0
    }

    @Unroll
    def 'should fail to save sub property descriptor for non existing descriptor `#result`'() {
        given:
            CreateSubPropertyDescriptor subPropertyDescriptor = new CreateSubPropertyDescriptor(
                    parentPropertyDescriptorUniqueId: parrentId,
                    name: randomAlphanumeric(20)
            )
        when:
            createSubPropertyDescriptorHandler.handle(subPropertyDescriptor)
        then:
            DomainException exception = thrown(DomainException)
            exception.id == result
        where:
            parrentId              || result
            null                   || 'UNIQUE_ID_IS_BLANK'
            ''                     || 'UNIQUE_ID_IS_BLANK'
            '13'                   || 'UNIQUE_ID_IS_LENGTH_IS_NOT_CORRECT'
            randomAlphanumeric(40) || 'PARENT_PROPERTY_DESCRIPTOR_HAS_TO_BE_PRESENT'
    }

    def 'should save the same property descriptor for several property descriptors'() {
        given:
            String subPropertyDescriptorName = randomAlphanumeric(20)
        and:
            fistSubPropertyDescriptorExists(subPropertyDescriptorName)
        and:
            def secondDummyPropertyDescriptor = DummyPropertyDescriptor.newPropertyDescriptor()
            dummyPropertyDescriptor.create(secondDummyPropertyDescriptor)
        and:
            CreateSubPropertyDescriptor secondSubPropertyDescriptor = new CreateSubPropertyDescriptor(
                    parentPropertyDescriptorUniqueId: secondDummyPropertyDescriptor.uniqueId,
                    name: subPropertyDescriptorName
            )
        when:
            createSubPropertyDescriptorHandler.handle(secondSubPropertyDescriptor)
        then:
            propertyDescriptors.findByUniqueId(secondDummyPropertyDescriptor.uniqueId).isPresent()
    }

    private void fistSubPropertyDescriptorExists(String subPropertyDescriptorName) {
        def firstDummyPropertyDescriptor = DummyPropertyDescriptor.newPropertyDescriptor()
        dummyPropertyDescriptor.create(firstDummyPropertyDescriptor)

        createSubPropertyDescriptorHandler.handle(
                new CreateSubPropertyDescriptor(
                        parentPropertyDescriptorUniqueId: firstDummyPropertyDescriptor.uniqueId,
                        name: subPropertyDescriptorName
                )
        )
    }

    def 'should fail to save sub property descriptor for non existing name'() {
        given:
            CreateSubPropertyDescriptor subPropertyDescriptor = new CreateSubPropertyDescriptor(
                    parentPropertyDescriptorUniqueId: dummyPropertyDescriptor.create(),
                    name: null
            )
        when:
            createSubPropertyDescriptorHandler.handle(subPropertyDescriptor)
        then:
            DomainException exception = thrown(DomainException)
            exception.id == 'SUB_PROPERTY_DESCRIPTOR_NAME_HAS_TO_BE_PRESENT'
    }
}
