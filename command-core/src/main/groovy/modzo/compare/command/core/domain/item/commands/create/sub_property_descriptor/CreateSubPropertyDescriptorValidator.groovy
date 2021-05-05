package modzo.compare.command.core.domain.item.commands.create.sub_property_descriptor

import modzo.compare.command.core.domain.item.PropertyDescriptor
import modzo.compare.command.core.domain.item.PropertyDescriptors
import modzo.compare.command.core.domain.validators.DomainException
import modzo.compare.command.core.domain.validators.UniqueIdValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreateSubPropertyDescriptorValidator {

    @Autowired
    private UniqueIdValidator uniqueIdValidator

    @Autowired
    private PropertyDescriptors propertyDescriptors

    void validate(CreateSubPropertyDescriptor command) {
        uniqueIdValidator.validate(command.uniqueId)
        uniqueIdValidator.validate(command.parentPropertyDescriptorUniqueId)

        Optional<PropertyDescriptor> parentDescriptor = propertyDescriptors.findByUniqueId(command.parentPropertyDescriptorUniqueId)

        if (parentDescriptor.isPresent()) {
            if (parentDescriptor.get().parentPropertyDescriptorUniqueId != null) {
                throw new DomainException('PARENT_PROPERTY_DESCRIPTOR_ALREADY_SUB_DESCRIPTOR', 'Parent property descriptor is already sub descriptor')
            }
        } else {
            throw new DomainException('PARENT_PROPERTY_DESCRIPTOR_HAS_TO_BE_PRESENT', 'Property descriptor with such unique id is not present')
        }

        if (propertyDescriptors.findByParentPropertyDescriptorUniqueIdAndName(
                command.parentPropertyDescriptorUniqueId,
                command.name).isPresent()
        ) {
            throw new DomainException('SUB_PROPERTY_DESCRIPTOR_IS_ALREADY_PRESENT', 'Sub property descriptor name is already present')
        }

        if (!command.name) {
            throw new DomainException('SUB_PROPERTY_DESCRIPTOR_NAME_HAS_TO_BE_PRESENT', 'Sub property descriptor name is already present')
        }
    }
}
