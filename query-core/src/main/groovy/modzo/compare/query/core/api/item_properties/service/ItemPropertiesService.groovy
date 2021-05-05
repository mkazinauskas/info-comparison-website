package modzo.compare.query.core.api.item_properties.service

import groovy.transform.CompileStatic
import modzo.compare.query.core.api.item_properties.ItemPropertyDescriptorBean
import modzo.compare.query.core.domain.item.ItemProperties
import org.springframework.stereotype.Service

@Service
@CompileStatic
class ItemPropertiesService {
    private final ItemProperties itemProperties

    private final PropertyDescriptorRetrieval propertyDescriptorRetrieval

    ItemPropertiesService(ItemProperties itemProperties, PropertyDescriptorRetrieval propertyDescriptorRetrieval) {
        this.itemProperties = itemProperties
        this.propertyDescriptorRetrieval = propertyDescriptorRetrieval
    }

    List<ItemPropertyDescriptorBean> getItemProperties(String itemUniqueId) {
        List<ItemPropertyDescriptorBean> collectedDescriptors = itemProperties
                .findByItemUniqueId(itemUniqueId).collect {
            propertyDescriptorRetrieval.getPropertyDescriptor(it.propertyDescriptorUniqueId,
                    new ItemPropertyDescriptorBean.ItemPropertyBean(
                            uniqueId: it.uniqueId,
                            value: it.value
                    )
            )
        }.findAll().sort { it.sequence }

        return PropertyDescriptorMerger.merge(collectedDescriptors)
    }
}
