package modzo.compare.query.core.api.compare_items

import modzo.compare.query.core.api.item_properties.ItemPropertyDescriptorBean
import modzo.compare.query.core.api.items.ItemBean

class CompareItemsBean {

    ItemBean firstItem
    ItemBean secondItem

    List<ComboProperties> comboProperties

    static class ComboProperties {
        String uniqueId
        int sequence
        String name
        String details

        List<ComboProperties> subDescriptors

        List<ItemPropertyDescriptorBean.ItemPropertyBean> firstItemProperties
        List<ItemPropertyDescriptorBean.ItemPropertyBean> secondItemProperties
    }

    boolean hasDescriptions() {
        return firstItem.description || secondItem.description
    }
}
