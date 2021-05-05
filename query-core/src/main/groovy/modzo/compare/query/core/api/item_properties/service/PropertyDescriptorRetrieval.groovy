package modzo.compare.query.core.api.item_properties.service

import groovy.transform.PackageScope
import modzo.compare.query.core.api.item_properties.ItemPropertyDescriptorBean
import modzo.compare.query.core.domain.item.PropertyDescriptor
import modzo.compare.query.core.domain.item.PropertyDescriptors
import org.springframework.stereotype.Component

@PackageScope
@Component
class PropertyDescriptorRetrieval {

    private final PropertyDescriptors propertyDescriptors

    PropertyDescriptorRetrieval(PropertyDescriptors propertyDescriptors) {
        this.propertyDescriptors = propertyDescriptors
    }

    ItemPropertyDescriptorBean getPropertyDescriptor(String propertyDescriptorUniqueId, ItemPropertyDescriptorBean.ItemPropertyBean property) {
        ItemPropertyDescriptorBean foundPropertyDescriptor

        propertyDescriptors.findByUniqueId(propertyDescriptorUniqueId).ifPresent {
            foundPropertyDescriptor = map(it)
            foundPropertyDescriptor.itemProperties.add(property)

            PropertyDescriptor descriptor = it
            ItemPropertyDescriptorBean descriptorBean = foundPropertyDescriptor
            while (descriptor.parentPropertyDescriptorUniqueId) {
                propertyDescriptors
                        .findByUniqueId(descriptor.parentPropertyDescriptorUniqueId)
                        .ifPresent { parentDescriptor ->
                    descriptor = parentDescriptor
                    ItemPropertyDescriptorBean mappedParent = map(parentDescriptor)
                    mappedParent.subDescriptors.add(descriptorBean)
                    descriptorBean = mappedParent
                }
            }
            foundPropertyDescriptor = descriptorBean
        }
        return foundPropertyDescriptor
    }

    private static ItemPropertyDescriptorBean map(PropertyDescriptor propertyDescriptor) {
        new ItemPropertyDescriptorBean(
                propertyDescriptorUniqueId: propertyDescriptor.uniqueId,
                name: propertyDescriptor.name,
                details: propertyDescriptor.details,
                sequence: propertyDescriptor.sequence
        )
    }


}
