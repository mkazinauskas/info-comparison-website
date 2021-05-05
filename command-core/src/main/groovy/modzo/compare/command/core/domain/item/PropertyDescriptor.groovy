package modzo.compare.command.core.domain.item

import modzo.compare.command.core.domain.AbstractEntity
import org.hibernate.validator.constraints.NotBlank

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'property_descriptors')
class PropertyDescriptor extends AbstractEntity {
    @Column(name = 'parent_property_descriptor_unique_id', nullable = true)
    String parentPropertyDescriptorUniqueId

    @Column(name = 'sequence')
    int sequence

    @NotBlank
    @Column(name = 'name', nullable = false)
    String name

    @Column(name = 'details', nullable = true)
    String details

}
