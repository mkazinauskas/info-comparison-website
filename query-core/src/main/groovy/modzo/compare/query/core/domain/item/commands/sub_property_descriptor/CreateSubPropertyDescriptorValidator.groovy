package modzo.compare.query.core.domain.item.commands.sub_property_descriptor

import modzo.compare.query.core.domain.item.PropertyDescriptors
import modzo.compare.query.core.domain.validators.DomainException
import org.springframework.stereotype.Component

@Component
class CreateSubPropertyDescriptorValidator {

    private final PropertyDescriptors propertyDescriptors

    CreateSubPropertyDescriptorValidator(PropertyDescriptors propertyDescriptors) {
        this.propertyDescriptors = propertyDescriptors
    }

    void validate(CreateSubPropertyDescriptor command) {
        if (!command.uniqueId) {
            throw new DomainException('SUB_PROPERTY_DESCRIPTOR_UNIQUE_ID_HAS_TO_BE_PRESENT', 'Sub property descriptor unique id has to be present')
        }

        if (!propertyDescriptors.findByUniqueId(command.parentPropertyDescriptorUniqueId).isPresent()) {
            throw new DomainException('PARENT_PROPERTY_DESCRIPTOR_HAS_TO_BE_PRESENT', "Property descriptor with such unique id is not present")
        }

        if (propertyDescriptors.findByParentPropertyDescriptorUniqueIdAndName(
                command.parentPropertyDescriptorUniqueId,
                command.name).isPresent()
        ) {
            throw new DomainException('SUB_PROPERTY_DESCRIPTOR_IS_ALREADY_PRESENT', 'Sub property descriptor name is already present')
        }
    }
}
