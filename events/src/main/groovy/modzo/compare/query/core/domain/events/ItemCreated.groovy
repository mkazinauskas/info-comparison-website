package modzo.compare.query.core.domain.events

class ItemCreated implements Event {
    static final String NAME = 'ITEM_CREATED'

    String uniqueId

    String identifier

    String name

    String description

    ItemCreated() {
        //For Json object mapper
    }

    ItemCreated(String uniqueId, String identifier, String name, String description) {
        this.uniqueId = uniqueId
        this.identifier = identifier
        this.name = name
        this.description = description
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
