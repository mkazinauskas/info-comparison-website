package modzo.compare.command.core.domain.item

import modzo.compare.command.core.domain.AbstractEntity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'item_properties')
class ItemProperty extends AbstractEntity {

    @Column(name = 'item_uniqueId', nullable = false)
    String itemUniqueId

    @Column(name = 'property_descriptor_uniqueId', nullable = false)
    String propertyDescriptorUniqueId

    @Column(name = 'value', nullable = false)
    String value
}
