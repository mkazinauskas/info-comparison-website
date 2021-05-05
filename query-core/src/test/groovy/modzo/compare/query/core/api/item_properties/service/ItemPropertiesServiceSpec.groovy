package modzo.compare.query.core.api.item_properties.service

import modzo.compare.query.core.api.item_properties.ItemPropertyDescriptorBean
import modzo.compare.query.core.domain.item.commands.DummyItem
import modzo.compare.query.core.domain.item.commands.DummyItemProperty
import modzo.compare.query.core.domain.item.commands.DummyPropertyDescriptor
import modzo.compare.query.core.domain.item.commands.DummyPropertySubDescriptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ItemPropertiesServiceSpec extends Specification {
    @Autowired
    ItemPropertiesService testTarget

    @Autowired
    DummyItem dummyItem

    @Autowired
    DummyItemProperty dummyItemProperty

    @Autowired
    DummyPropertyDescriptor dummyPropertyDescriptor

    @Autowired
    DummyPropertySubDescriptor dummyPropertySubDescriptor

    def 'should retrieve descriptor with property'() {
        given:
            String itemId = dummyItem.create()
        and:
            String descriptorId = dummyPropertyDescriptor.create('descriptor', 'descriptor details')
        and:
            String itemPropertyId = dummyItemProperty.create(itemId, descriptorId, 'property name')
        when:
            List<ItemPropertyDescriptorBean> result = testTarget.getItemProperties(itemId)
        then:
            result.size() == 1
            result.first().propertyDescriptorUniqueId == descriptorId
            result.first().name == 'descriptor'
            result.first().details == 'descriptor details'
            result.first().sequence > 0
            result.first().subDescriptors == []
            result.first().itemProperties.size() == 1
            result.first().itemProperties.first().uniqueId == itemPropertyId
            result.first().itemProperties.first().value == 'property name'
    }

    def 'should retrieve descriptor with sub descriptors'() {
        given:
            String itemId = dummyItem.create()
        and:
            String firstDescriptorId = dummyPropertyDescriptor.create('1st descriptor', '1st descriptor details')
        and:
            String secondDescriptorId = dummyPropertySubDescriptor.create(firstDescriptorId,'2nd descriptor', '2nd descriptor details')
        and:
            String itemPropertyId = dummyItemProperty.create(itemId, secondDescriptorId, '1st property name')
        when:
            List<ItemPropertyDescriptorBean> result = testTarget.getItemProperties(itemId)
        then:
            result.size() == 1
            result.first().propertyDescriptorUniqueId == firstDescriptorId
            result.first().name == '1st descriptor'
            result.first().details == '1st descriptor details'
            result.first().sequence > 0

            result.first().subDescriptors.size() == 1
            result.first().subDescriptors.first().propertyDescriptorUniqueId == secondDescriptorId
            result.first().subDescriptors.first().name == '2nd descriptor'
            result.first().subDescriptors.first().details == '2nd descriptor details'
            result.first().subDescriptors.first().sequence > 0

            result.first().subDescriptors.first().itemProperties.size() == 1
            result.first().subDescriptors.first().itemProperties.first().uniqueId == itemPropertyId
            result.first().subDescriptors.first().itemProperties.first().value == '1st property name'
    }
}
