package modzo.compare.query.core.domain.item.commands.photo

import modzo.compare.query.core.domain.events.services.EventPublisher
import modzo.compare.query.core.domain.item.Photo
import modzo.compare.query.core.domain.item.Photos
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreatePhotoHandler {

    private CreatePhotoValidator validator

    private EventPublisher eventPublisher

    private Photos photos

    CreatePhotoHandler(CreatePhotoValidator validator, EventPublisher eventPublisher, Photos photos) {
        this.validator = validator
        this.eventPublisher = eventPublisher
        this.photos = photos
    }

    @Transactional
    void handle(CreatePhoto command) {
        validator.validate(command)

        Photo photo = new Photo().with {
            uniqueId = command.uniqueId
            itemUniqueId = command.itemUniqueId
            name = command.name
            description = command.description
            urlMD5 = command.urlAsMD5
            sequence = command.sequence
            it
        }

        Photo savedPhoto = photos.save(photo)
    }
}
