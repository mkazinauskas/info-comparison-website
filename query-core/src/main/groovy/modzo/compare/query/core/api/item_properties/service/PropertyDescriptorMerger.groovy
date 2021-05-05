package modzo.compare.query.core.api.item_properties.service

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import modzo.compare.query.core.api.item_properties.ItemPropertyDescriptorBean

@PackageScope
@CompileStatic
class PropertyDescriptorMerger {
    static List<ItemPropertyDescriptorBean> merge(List<ItemPropertyDescriptorBean> itemDescriptorBeans) {
        Set<String> uniqueIds = itemDescriptorBeans.collect { it.propertyDescriptorUniqueId }.toSet()
        List<ItemPropertyDescriptorBean> result = uniqueIds
                .collect { uniqueId ->
            ItemPropertyDescriptorBean existingItem = new ItemPropertyDescriptorBean()
            itemDescriptorBeans.findAll {
                it.propertyDescriptorUniqueId == uniqueId
            }.each {
                remap(existingItem, it)

            }
            return existingItem
        }.findAll { it.propertyDescriptorUniqueId }

        result
                .findAll { it.subDescriptors }
                .each { it.subDescriptors = merge(it.subDescriptors) }
        return result
    }

    private static void remap(ItemPropertyDescriptorBean remapTo, ItemPropertyDescriptorBean remapFrom) {
        remapTo.propertyDescriptorUniqueId = remapFrom.propertyDescriptorUniqueId
        remapTo.name = remapFrom.name
        remapTo.details = remapFrom.details
        remapTo.itemProperties.addAll(remapFrom.itemProperties)
        remapTo.sequence = remapFrom.sequence
        remapTo.subDescriptors.addAll(remapFrom.subDescriptors)
    }
}
