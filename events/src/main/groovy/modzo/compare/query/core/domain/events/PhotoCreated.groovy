package modzo.compare.query.core.domain.events

class PhotoCreated implements Event {
    static final String NAME = 'PHOTO_CREATED'

    String uniqueId

    String itemUniqueId

    String name

    String description

    String urlMD5

    int sequence

    PhotoCreated() {
    }

    PhotoCreated(String uniqueId,
                 String itemUniqueId,
                 String name,
                 String description,
                 String urlMD5,
                 int sequence) {
        this.uniqueId = uniqueId
        this.itemUniqueId = itemUniqueId
        this.name = name
        this.description = description
        this.urlMD5 = urlMD5
        this.sequence = sequence
    }

    @Override
    String topic() {
        return NAME
    }

    @Override
    String uniqueId() {
        return uniqueId
    }
}
