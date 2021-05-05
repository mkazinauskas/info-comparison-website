package modzo.compare.command.core.domain.item.commands.create.photo

import modzo.compare.command.core.domain.DummyItem
import modzo.compare.command.core.domain.item.Photo
import modzo.compare.command.core.domain.item.Photos
import modzo.compare.command.core.domain.validators.DomainException
import org.apache.commons.codec.digest.DigestUtils
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import spock.lang.Specification
import spock.lang.Unroll

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreatePhotoHandlerSpec extends Specification {
    @Autowired
    CreatePhotoHandler createPhotoHandler

    @Autowired
    Photos photos

    @Autowired
    DummyItem dummyItem

    @Autowired
    PhotoUploader photoUploader

    def 'should save photo'() {
        given:
            String itemUniqueId = dummyItem.create()
        and:
            CreatePhoto command = new CreatePhoto(
                    itemUniqueId: itemUniqueId,
                    name: randomAlphanumeric(5),
                    description: randomAlphanumeric(5),
                    url: "http://www.some.page.com/${randomAlphanumeric(5)}"
            )
        when:
            createPhotoHandler.handle(command)
        then:
            verify(photoUploader, times(1)).upload(Mockito.any(PhotoUploader.Photo))
        and:
            Photo savedPhoto = photos.findByUniqueId(command.uniqueId).get()
            savedPhoto.itemUniqueId == command.itemUniqueId
            savedPhoto.description == command.description
            savedPhoto.name == command.name
            savedPhoto.urlMD5 == DigestUtils.md5Hex(command.url)
    }

    def 'should save more than one photo for item'() {
        given:
            String itemUniqueId = dummyItem.create()
        and:
            CreatePhoto firstCommand = new CreatePhoto(
                    itemUniqueId: itemUniqueId,
                    name: randomAlphanumeric(5),
                    description: randomAlphanumeric(5),
                    url: "http://www.some.page.com/${randomAlphanumeric(5)}"
            )
        and:
            CreatePhoto secondCommand = new CreatePhoto(
                    itemUniqueId: itemUniqueId,
                    name: randomAlphanumeric(5),
                    description: randomAlphanumeric(5),
                    url: "http://www.some.page.com/${randomAlphanumeric(5)}"
            )
        when:
            createPhotoHandler.handle(firstCommand)
        and:
            createPhotoHandler.handle(secondCommand)
        then:
            photos.findByUniqueId(firstCommand.uniqueId).isPresent()
        and:
            photos.findByUniqueId(secondCommand.uniqueId).isPresent()
    }

    @Unroll
    def 'should fail to save photo with `#result`'() {
        given:
            CreatePhoto command = photo
            command.itemUniqueId = dummyItem.create()
        when:
            createPhotoHandler.handle(command)
        then:
            DomainException exception = thrown(DomainException)
            exception.id == result
        and:
            verify(photoUploader, times(0)).upload(Mockito.any(PhotoUploader.Photo))
        and:
            !photos.findByUniqueId(command.uniqueId).isPresent()
        where:
            photo           || result
            noName()        || 'PHOTO_NAME_HAS_TO_BE_PRESENT'
            noDescription() || 'PHOTO_DESCRIPTION_HAS_TO_BE_PRESENT'
            noUrl()         || 'PHOTO_URL_HAS_TO_BE_PRESENT'
    }

    @Unroll
    def 'should fail to save photo with `ITEM_WITH_UNIQUE_ID_DOES_NOT_EXIST`'() {
        given:
            CreatePhoto command = noItem()
        when:
            createPhotoHandler.handle(noItem())
        then:
            DomainException exception = thrown(DomainException)
            exception.id == 'ITEM_WITH_UNIQUE_ID_DOES_NOT_EXIST'
        and:
            verify(photoUploader, times(0)).upload(Mockito.any(PhotoUploader.Photo))
        and:
            !photos.findByUniqueId(command.uniqueId).isPresent()
    }

    private static CreatePhoto noItem() {
        new CreatePhoto(
                itemUniqueId: null,
                name: randomAlphanumeric(5),
                description: randomAlphanumeric(5),
                url: "http://www.some.page.com/${randomAlphanumeric(5)}"
        )
    }

    private static CreatePhoto noName() {
        new CreatePhoto(
                name: null,
                description: randomAlphanumeric(5),
                url: "http://www.some.page.com/${randomAlphanumeric(5)}"
        )
    }

    private static CreatePhoto noDescription() {
        new CreatePhoto(
                name: randomAlphanumeric(5),
                description: null,
                url: "http://www.some.page.com/${randomAlphanumeric(5)}"
        )
    }

    private static CreatePhoto noUrl() {
        new CreatePhoto(
                name: randomAlphanumeric(5),
                description: randomAlphanumeric(5),
                url: null
        )
    }
}
