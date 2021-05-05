package modzo.compare.query.core.events.parsers

import com.fasterxml.jackson.databind.ObjectMapper
import modzo.compare.query.core.domain.events.PhotoCreated
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.query.core.domain.item.Photo
import modzo.compare.query.core.domain.item.Photos
import modzo.compare.query.core.domain.item.commands.DummyItem
import modzo.compare.query.core.domain.item.commands.DummyPropertyDescriptor
import org.apache.commons.lang.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PhotoCreatedEventParserSpec extends Specification {

    @Autowired
    private PhotoCreatedEventParser itemCreatedEventParser

    @Autowired
    private Photos photos

    @Autowired
    private DummyItem dummyItem

    @Autowired
    private DummyPropertyDescriptor dummyPropertyDescriptor

    def 'should save added item property to database'() {
        given:
            String photoUniqueId = RandomStringUtils.randomAlphanumeric(30)
        and:
            String itemUniqueId = dummyItem.create()
        and:
            EventService.RetrievedEvent retrievedEvent = new EventService.RetrievedEvent(
                    uniqueId: photoUniqueId,
                    type: PhotoCreated.NAME,
                    data: new ObjectMapper().writeValueAsString(
                            new PhotoCreated(
                                    uniqueId: photoUniqueId,
                                    itemUniqueId: itemUniqueId,
                                    name: 'photo name',
                                    description: 'photo description',
                                    urlMD5: 'urlMd5',
                                    sequence: 1
                            )
                    )
            )
        when:
            itemCreatedEventParser.parse(retrievedEvent)
        then:
            Photo photo = photos.findByUniqueId(photoUniqueId).get()
            photo.uniqueId == photoUniqueId
            photo.itemUniqueId == itemUniqueId
            photo.name == 'photo name'
            photo.description == 'photo description'
            photo.urlMD5 == 'urlMd5'
            photo.sequence == 1
    }
}


