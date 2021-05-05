package modzo.compare.query.core.domain.events

class ItemPropertyAdded implements Event {
    static final String NAME = 'ITEM_PROPERTY_ADDED'

    String uniqueId

    String itemUniqueId

    String propertyDescriptorUniqueId

    String value

    ItemPropertyAdded() {
        //deserialization
    }

    ItemPropertyAdded(String uniqueId, String itemUniqueId, String propertyDescriptorUniqueId, String value) {
        this.uniqueId = uniqueId
        this.itemUniqueId = itemUniqueId
        this.propertyDescriptorUniqueId = propertyDescriptorUniqueId
        this.value = value
    }

    @Override
    String topic() {
        return NAME
    }

    @Override
    String uniqueId() {
        uniqueId
    }
}
