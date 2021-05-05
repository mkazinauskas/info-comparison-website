package modzo.compare.query.core.events.parsers

import modzo.compare.query.core.domain.events.PhotoCreated
import modzo.compare.query.core.domain.events.services.EventDeserializer
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.query.core.domain.item.commands.photo.CreatePhoto
import modzo.compare.query.core.domain.item.commands.photo.CreatePhotoHandler
import org.springframework.stereotype.Component

@Component
class PhotoCreatedEventParser implements EventParser {

    private final EventDeserializer eventDeserializer
    private final CreatePhotoHandler createPhotoHandler

    PhotoCreatedEventParser(EventDeserializer eventDeserializer, CreatePhotoHandler createPhotoHandler) {
        this.eventDeserializer = eventDeserializer
        this.createPhotoHandler = createPhotoHandler
    }

    @Override
    String type() {
        return PhotoCreated.NAME
    }

    @Override
    void parse(EventService.RetrievedEvent event) {
        PhotoCreated addItemProperty = eventDeserializer.deserialize(event, PhotoCreated)
        createPhotoHandler.handle(
                new CreatePhoto(
                        uniqueId: addItemProperty.uniqueId,
                        itemUniqueId: addItemProperty.itemUniqueId,
                        name: addItemProperty.name,
                        description: addItemProperty.description,
                        urlAsMD5: addItemProperty.urlMD5,
                        sequence: addItemProperty.sequence,
                )
        )
    }
}
