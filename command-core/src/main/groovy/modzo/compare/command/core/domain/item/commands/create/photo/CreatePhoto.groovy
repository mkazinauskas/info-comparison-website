package modzo.compare.command.core.domain.item.commands.create.photo

import modzo.compare.command.core.domain.item.Photo
import modzo.compare.query.core.domain.events.PhotoCreated

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic

class CreatePhoto {
    String uniqueId = randomAlphabetic(40)

    String itemUniqueId

    String name

    String description

    String url

    PhotoCreated asEvent(Photo photo) {
        return new PhotoCreated(uniqueId, itemUniqueId, name, description, photo.urlMD5, photo.sequence)
    }
}
