package modzo.compare.query.core.api.item_properties

class ItemPropertyDescriptorBean {
    String propertyDescriptorUniqueId

    String name

    String details

    int sequence

    List<ItemPropertyDescriptorBean> subDescriptors = []

    List<ItemPropertyBean> itemProperties = []

    static class ItemPropertyBean {
        String uniqueId
        String value
    }

    boolean hasSubDescriptors() {
        return subDescriptors
    }

}