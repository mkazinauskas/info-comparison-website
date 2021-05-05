package modzo.compare.command.core.domain.item.commands.create.photo

import modzo.compare.command.core.domain.item.Photo
import modzo.compare.command.core.domain.item.Photos
import modzo.compare.query.core.domain.events.services.EventPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreatePhotoHandler {

    @Autowired
    private CreatePhotoValidator validator

    @Autowired
    private EventPublisher eventPublisher

    @Autowired
    private PhotoUploader photoUploader

    @Autowired
    private Photos photos

    @Transactional
    void handle(CreatePhoto command) {
        validator.validate(command)

        Photo photo = new Photo().with {
            uniqueId = command.uniqueId
            itemUniqueId = command.itemUniqueId
            name = command.name
            description = command.description
            assignUrlMd5(command.url)
            sequence = photos.findByItemUniqueId(command.itemUniqueId).size()
            it
        }

        photoUploader.upload(new PhotoUploader.Photo(photo.urlMD5, command.url))

        Photo savedPhoto = photos.saveAndFlush(photo)
        eventPublisher.publish(command.asEvent(savedPhoto))
    }
}
