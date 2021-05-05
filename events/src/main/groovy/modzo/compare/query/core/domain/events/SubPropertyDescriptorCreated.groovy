package modzo.compare.query.core.domain.events

class SubPropertyDescriptorCreated implements Event {
    static final String NAME = 'SUB_PROPERTY_DESCRIPTOR_CREATED'

    String uniqueId

    String parentPropertyDescriptorUniqueId

    long sequence

    String name

    String details

    SubPropertyDescriptorCreated() {
        //For deserialization
    }

    SubPropertyDescriptorCreated(String uniqueId,
                                 String parentPropertyDescriptorUniqueId,
                                 long sequence,
                                 String name,
                                 String details) {
        this.uniqueId = uniqueId
        this.parentPropertyDescriptorUniqueId = parentPropertyDescriptorUniqueId
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
