package modzo.compare.query.core.domain.item.commands.property_descriptor

import modzo.compare.query.core.domain.item.PropertyDescriptors
import modzo.compare.query.core.domain.validators.DomainException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreatePropertyDescriptorValidator {

    @Autowired
    private PropertyDescriptors propertyDescriptors

    void validate(CreatePropertyDescriptor command) {
        if (!command.uniqueId) {
            throw new DomainException('SUB_PROPERTY_DESCRIPTOR_UNIQUE_ID_HAS_TO_BE_PRESENT', 'Sub property descriptor unique id has to be present')
        }

        if (propertyDescriptors.findByUniqueId(command.uniqueId).isPresent()) {
            throw new DomainException('PROPERTY_DESCRIPTOR_IS_ALREADY_PRESENT', "Property descriptor is already present")
        }
    }
}
