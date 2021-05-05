package modzo.compare.query.core.domain.events

class PropertyDescriptorCreated implements Event {
    static final String NAME = 'PROPERTY_DESCRIPTOR_CREATED'

    String uniqueId

    long sequence

    String name

    String details

    PropertyDescriptorCreated() {
    }

    PropertyDescriptorCreated(String uniqueId, long sequence, String name, String details) {
        this.uniqueId = uniqueId
        this.sequence = sequence
        this.name = name
        this.details = details
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
