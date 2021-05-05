package modzo.compare.query.core.api.compare_items

import modzo.compare.query.core.api.item_properties.service.ItemPropertiesService
import modzo.compare.query.core.api.utils.TestContext
import modzo.compare.query.core.domain.item.commands.DummyItem
import modzo.compare.query.core.domain.item.commands.DummyItemProperty
import modzo.compare.query.core.domain.item.commands.DummyPropertyDescriptor
import modzo.compare.query.core.domain.item.commands.DummyPropertySubDescriptor
import org.spockframework.util.Pair
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.OK

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CompareItemsControllerSpec extends Specification {
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

    @Autowired
    private TestContext testContext

    def 'should compare the same items'() {
        given:
            String firstItemUniqueId = createFirstItem()
        when:
            ResponseEntity<CompareItemsBean> response = testContext.compareItems(firstItemUniqueId, firstItemUniqueId)
        then:
            response.statusCode == OK
        and:
            CompareItemsBean body = response.body

            body.firstItem.uniqueId == firstItemUniqueId
            body.secondItem.uniqueId == firstItemUniqueId

            body.comboProperties.size() == 1
            body.comboProperties.first().name == 'descriptor 11'
            body.comboProperties.first().details == 'descriptor details 11'

            body.comboProperties.first().firstItemProperties.size() == 1
            body.comboProperties.first().firstItemProperties.first().value == 'property name 11'
            body.comboProperties.first().firstItemProperties.first().uniqueId.length() == 40

            body.comboProperties.first().subDescriptors.size() == 1
            body.comboProperties.first().subDescriptors.first().subDescriptors == null
            body.comboProperties.first().subDescriptors.first().name == 'sub descriptor 11'
            body.comboProperties.first().subDescriptors.first().details == 'sub descriptor details 11'

            body.comboProperties.first().subDescriptors.first().firstItemProperties.size() == 1
            body.comboProperties.first().subDescriptors.first().firstItemProperties.first().value == 'sub descriptor property name 11'
            body.comboProperties.first().subDescriptors.first().firstItemProperties.first().uniqueId.length() == 40
    }

    def 'should compare two separate items'() {
        given:
            String secondItemUniqueId = createSecondItem()
            String firstItemUniqueId = createFirstItem()
        when:
            ResponseEntity<CompareItemsBean> response = testContext.compareItems(firstItemUniqueId, secondItemUniqueId)
        then:
            response.statusCode == OK
        and:
            CompareItemsBean body = response.body

            body.firstItem.uniqueId == firstItemUniqueId
            body.secondItem.uniqueId == secondItemUniqueId

            body.comboProperties.size() == 2
            def firstItem = body.comboProperties.find{it.name == 'descriptor 11'}
            firstItem.name == 'descriptor 11'
            firstItem.details == 'descriptor details 11'

            firstItem.firstItemProperties.size() == 1
            firstItem.firstItemProperties.first().uniqueId.length() == 40
            firstItem.firstItemProperties.first().value == 'property name 11'

            firstItem.subDescriptors.size() == 1
            firstItem.subDescriptors[0].subDescriptors == null
            firstItem.subDescriptors[0].name == 'sub descriptor 11'
            firstItem.subDescriptors[0].details == 'sub descriptor details 11'
            firstItem.subDescriptors[0].firstItemProperties.size() == 1
            firstItem.subDescriptors[0].firstItemProperties[0].uniqueId.length() == 40
            firstItem.subDescriptors[0].firstItemProperties[0].value == 'sub descriptor property name 11'
            firstItem.subDescriptors[0].secondItemProperties == null

            def secondItem = body.comboProperties.find{it.name == 'descriptor 22'}
            secondItem.name == 'descriptor 22'
            secondItem.details == 'descriptor details 22'

            secondItem.subDescriptors.size() == 1
            secondItem.subDescriptors[0].subDescriptors == null
            secondItem.subDescriptors[0].name == 'sub descriptor 22'
            secondItem.subDescriptors[0].details == 'sub descriptor details 22'
            secondItem.subDescriptors[0].firstItemProperties == null
            secondItem.subDescriptors[0].secondItemProperties.size() == 1
            secondItem.subDescriptors[0].secondItemProperties[0].uniqueId.length() == 40
            secondItem.subDescriptors[0].secondItemProperties[0].value == 'sub descriptor property name 22'
    }

    def 'should compare two almost identical items'() {
        given:
            Pair<String, String> twoItems = createTwoSimilarItems()
        when:
            ResponseEntity<CompareItemsBean> response = testContext.compareItems(twoItems.first(), twoItems.second())
        then:
            response.statusCode == OK
        and:
            CompareItemsBean body = response.body

            body.firstItem.uniqueId == twoItems.first()
            body.secondItem.uniqueId == twoItems.second()

            body.comboProperties.size() == 1
            body.comboProperties[0].name == 'descriptor double'
            body.comboProperties[0].details == 'descriptor details double'

            body.comboProperties[0].firstItemProperties.size() == 1
            body.comboProperties[0].firstItemProperties.first().uniqueId.length() == 40
            body.comboProperties[0].firstItemProperties.first().value == 'property name double first'

            body.comboProperties[0].secondItemProperties.size() == 1
            body.comboProperties[0].secondItemProperties.first().uniqueId.length() == 40
            body.comboProperties[0].secondItemProperties.first().value == 'property name double second'

            body.comboProperties[0].subDescriptors.size() == 1
            body.comboProperties[0].subDescriptors[0].subDescriptors == null
            body.comboProperties[0].subDescriptors[0].name == 'sub descriptor double'
            body.comboProperties[0].subDescriptors[0].details == 'sub descriptor details double'

            body.comboProperties[0].subDescriptors[0].firstItemProperties.size() == 1
            body.comboProperties[0].subDescriptors[0].firstItemProperties[0].uniqueId.length() == 40
            body.comboProperties[0].subDescriptors[0].firstItemProperties[0].value == 'sub descriptor property name double first'
            body.comboProperties[0].subDescriptors[0].secondItemProperties[0].uniqueId.length() == 40
            body.comboProperties[0].subDescriptors[0].secondItemProperties[0].value == 'sub descriptor property name double second'
    }

    private String createFirstItem() {
        String itemId = dummyItem.create()

        String descriptorId = dummyPropertyDescriptor.create('descriptor 11', 'descriptor details 11')
        dummyItemProperty.create(itemId, descriptorId, 'property name 11')

        String subDescriptorId = dummyPropertySubDescriptor.create(descriptorId, 'sub descriptor 11', 'sub descriptor details 11')
        dummyItemProperty.create(itemId, subDescriptorId, 'sub descriptor property name 11')
        return itemId
    }

    private String createSecondItem() {
        String itemId = dummyItem.create()

        String descriptorId = dummyPropertyDescriptor.create('descriptor 22', 'descriptor details 22')
        dummyItemProperty.create(itemId, descriptorId, 'property name 22')

        String subDescriptorId = dummyPropertySubDescriptor.create(descriptorId, 'sub descriptor 22', 'sub descriptor details 22')
        dummyItemProperty.create(itemId, subDescriptorId, 'sub descriptor property name 22')
        return itemId
    }

    private Pair<String, String> createTwoSimilarItems() {
        String firstItem = dummyItem.create()
        String secondItem = dummyItem.create()

        String descriptorId = dummyPropertyDescriptor.create('descriptor double', 'descriptor details double')
        dummyItemProperty.create(firstItem, descriptorId, 'property name double first')
        dummyItemProperty.create(secondItem, descriptorId, 'property name double second')

        String subDescriptorId = dummyPropertySubDescriptor.create(descriptorId, 'sub descriptor double', 'sub descriptor details double')
        dummyItemProperty.create(firstItem, subDescriptorId, 'sub descriptor property name double first')
        dummyItemProperty.create(secondItem, subDescriptorId, 'sub descriptor property name double second')

        return Pair.of(firstItem, secondItem)
    }
}
