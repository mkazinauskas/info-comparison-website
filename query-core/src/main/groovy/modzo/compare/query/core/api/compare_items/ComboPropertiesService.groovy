package modzo.compare.query.core.api.compare_items

import modzo.compare.query.core.api.item_properties.ItemPropertyDescriptorBean
import org.springframework.stereotype.Component

@Component
class ComboPropertiesService {

    List<CompareItemsBean.ComboProperties> map(List<ItemPropertyDescriptorBean> firstItemProperties,
                                               List<ItemPropertyDescriptorBean> secondItemProperties) {
        if (!firstItemProperties && !secondItemProperties) {
            return null
        }
        Set<String> filteredDescriptors = []

        List<CompareItemsBean.ComboProperties> doneItems = []
        firstItemProperties?.each { firstItemDescriptor ->
            filteredDescriptors << firstItemDescriptor.propertyDescriptorUniqueId

            ItemPropertyDescriptorBean secondItemValues = secondItemProperties
                    .find {
                it.propertyDescriptorUniqueId == firstItemDescriptor.propertyDescriptorUniqueId
            }

            CompareItemsBean.ComboProperties comboProperties = new CompareItemsBean.ComboProperties(
                    uniqueId: firstItemDescriptor.propertyDescriptorUniqueId,
                    sequence: firstItemDescriptor.sequence,
                    name: firstItemDescriptor.name,
                    details: firstItemDescriptor.details,
                    firstItemProperties: firstItemDescriptor.itemProperties,
                    secondItemProperties: secondItemValues?.itemProperties,
                    subDescriptors: map(firstItemDescriptor.subDescriptors, secondItemValues?.subDescriptors)
            )

            doneItems << comboProperties
        }

        secondItemProperties?.findAll { !filteredDescriptors.contains(it.propertyDescriptorUniqueId) }
                ?.each { secondItemDescriptor ->
            CompareItemsBean.ComboProperties comboProperties = new CompareItemsBean.ComboProperties(
                    uniqueId: secondItemDescriptor.propertyDescriptorUniqueId,
                    sequence: secondItemDescriptor.sequence,
                    name: secondItemDescriptor.name,
                    details: secondItemDescriptor.details,
                    secondItemProperties: secondItemDescriptor.itemProperties,
                    subDescriptors: map(null, secondItemDescriptor.subDescriptors)
            )

            doneItems << comboProperties
        }

        return doneItems.sort { it.sequence }
    }
}
