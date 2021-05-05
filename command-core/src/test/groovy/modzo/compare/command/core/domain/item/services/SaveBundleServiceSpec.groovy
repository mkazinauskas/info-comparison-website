package modzo.compare.command.core.domain.item.services

import modzo.compare.command.core.domain.item.*
import modzo.compare.command.core.domain.item.commands.create.photo.PhotoUploader
import modzo.compare.query.core.domain.events.services.EventPublisher
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.mockito.Matchers.any
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class SaveBundleServiceSpec extends Specification {

    @Autowired
    SaveBundleService testTarget

    @Autowired
    Items items

    @Autowired
    Photos photos

    @Autowired
    EventPublisher eventPublisher

    @Autowired
    PhotoUploader photoUploader

    @Autowired
    PropertyDescriptors propertyDescriptors

    @Autowired
    ItemProperties itemProperties

    void setup() {
        photoUploader.upload(any(PhotoUploader.Photo))
    }

    def 'should persist whole bundle'() {
        given:
            SaveBundleService.Bundle bundle = new SaveBundleService.Bundle(
                    identifier: RandomStringUtils.randomAlphanumeric(3),
                    name: RandomStringUtils.randomAlphanumeric(100),
                    description: 'some bundle description',
            )
            bundle.photos.add(
                    new SaveBundleService.Bundle.Photo(
                            'bundle photo',
                            'photo description',
                            'http://some.url.com'
                    )
            )
            bundle.details.add(
                    new SaveBundleService.Bundle.Property(
                            key: 'My Key 1',
                            subKey: 'My Sub Key 1',
                            value: 'Value 1'
                    )
            )
        when:
            testTarget.save(bundle)
        then:
            Item item = items.findByIdentifier(bundle.identifier).get()
            item.uniqueId.size() == 40
            item.identifier == bundle.identifier
            item.name == bundle.name
            item.description == bundle.description
        and:
            Photo photo = photos.findByItemUniqueId(item.uniqueId).first()
            photo.uniqueId.size() == 40
            photo.name == bundle.photos.first().name
            photo.description == bundle.photos.first().description
            photo.urlMD5 == DigestUtils.md5Hex(bundle.photos.first().url)
            photo.sequence == 0
        and:
            PropertyDescriptor firstPropertyDescriptor = propertyDescriptors.findByName('My Key 1').get()
            firstPropertyDescriptor.parentPropertyDescriptorUniqueId == null
            firstPropertyDescriptor.sequence >= 0
            firstPropertyDescriptor.name == 'My Key 1'
            firstPropertyDescriptor.uniqueId.size() == 40
        and:
            PropertyDescriptor subPropertyDescriptor = propertyDescriptors.findByName('My Sub Key 1').get()
            subPropertyDescriptor.parentPropertyDescriptorUniqueId == firstPropertyDescriptor.uniqueId
            subPropertyDescriptor.sequence >= 1
            subPropertyDescriptor.name == 'My Sub Key 1'
            subPropertyDescriptor.uniqueId.size() == 40
            subPropertyDescriptor
        and:
            List<ItemProperty> properties = itemProperties.findByItemUniqueId(item.uniqueId)
            properties.size() == 1
            ItemProperty property = properties.first()
            property.value == 'Value 1'
            property.propertyDescriptorUniqueId == subPropertyDescriptor.uniqueId
            property.itemUniqueId == item.uniqueId
    }

    def 'should persist only item and not fail to persist other items'() {
        given:
            SaveBundleService.Bundle bundle = new SaveBundleService.Bundle(
                    identifier: RandomStringUtils.randomAlphanumeric(3),
                    name: RandomStringUtils.randomAlphanumeric(100),
                    description: 'some bundle description'
            )
            bundle.photos.add(
                    new SaveBundleService.Bundle.Photo(
                            null,
                            'photo description',
                            'http://some.url.com'
                    )
            )
            bundle.details.add(
                    new SaveBundleService.Bundle.Property(
                            key: null,
                            subKey: 'My Sub Key 2',
                            value: 'Value 2'
                    )
            )
        when:
            testTarget.save(bundle)
        then:
            Item item = items.findByIdentifier(bundle.identifier).get()
            item.uniqueId.size() == 40
            item.identifier == bundle.identifier
            item.name == bundle.name
            item.description == bundle.description
        and:
            photos.findByItemUniqueId(item.uniqueId).isEmpty()
        and:
            !propertyDescriptors.findByName('My Key 2').isPresent()
        and:
            !propertyDescriptors.findByName('My Sub Key 2').isPresent()
        and:
            itemProperties.findByItemUniqueId(item.uniqueId).size() == 0
    }

    def 'should persist item details without sub category'() {
        given:
            SaveBundleService.Bundle bundle = new SaveBundleService.Bundle(
                    identifier: RandomStringUtils.randomAlphanumeric(3),
                    name: RandomStringUtils.randomAlphanumeric(100),
                    description: 'some bundle description'
            )

            bundle.photos.add(
                    new SaveBundleService.Bundle.Photo(
                            'bundle photo',
                            'photo description',
                            'http://some.url.com'
                    )
            )

            bundle.details.add(
                    new SaveBundleService.Bundle.Property(
                            key: 'My Key 3',
                            subKey: null,
                            value: 'Value 3'
                    )
            )
        when:
            testTarget.save(bundle)
        then:
            Item item = items.findByIdentifier(bundle.identifier).get()
            item.uniqueId.size() == 40
            item.identifier == bundle.identifier
            item.name == bundle.name
            item.description == bundle.description
        and:
            Photo photo = photos.findByItemUniqueId(item.uniqueId).first()
            photo.uniqueId.size() == 40
            photo.name == bundle.photos.first().name
            photo.description == bundle.photos.first().description
            photo.urlMD5 == DigestUtils.md5Hex(bundle.photos.first().url)
            photo.sequence == 0
        and:
            PropertyDescriptor firstPropertyDescriptor = propertyDescriptors.findByName('My Key 3').get()
            firstPropertyDescriptor.parentPropertyDescriptorUniqueId == null
            firstPropertyDescriptor.sequence >= 0
            firstPropertyDescriptor.name == 'My Key 3'
            firstPropertyDescriptor.uniqueId.size() == 40
        and:
            List<ItemProperty> properties = itemProperties.findByItemUniqueId(item.uniqueId)
            properties.size() == 1
            ItemProperty property = properties.first()
            property.value == 'Value 3'
            property.propertyDescriptorUniqueId == firstPropertyDescriptor.uniqueId
            property.itemUniqueId == item.uniqueId
    }

}
