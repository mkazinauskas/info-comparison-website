package modzo.compare.command.core.domain.item.commands.create.property_descriptor

import modzo.compare.command.core.domain.item.PropertyDescriptors
import modzo.compare.command.core.domain.validators.DomainException
import modzo.compare.command.core.domain.validators.UniqueIdValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreatePropertyDescriptorValidator {

    @Autowired
    private UniqueIdValidator uniqueIdValidator

    @Autowired
    private PropertyDescriptors propertyDescriptors

    void validate(CreatePropertyDescriptor command) {
        uniqueIdValidator.validate(command.uniqueId)

        if(propertyDescriptors.findByName(command.name).isPresent()){
            throw new DomainException('PROPERTY_DESCRIPTOR_NAME_IS_PRESENT', "Property descriptor name is present")
        }

        if(propertyDescriptors.findByUniqueId(command.uniqueId).isPresent()){
            throw new DomainException('PROPERTY_DESCRIPTOR_UNIQUE_ID_IS_PRESENT', "Property descriptor unique id is present")
        }

        if (!command.uniqueId) {
            throw new DomainException('PROPERTY_DESCRIPTOR_UNIQUE_ID_HAS_TO_BE_PRESENT', "Property descriptor unique id has to be present")
        }

        if (!command.name) {
            throw new DomainException('PROPERTY_DESCRIPTOR_NAME_HAS_TO_BE_PRESENT', "Property descriptor name has to be present")
        }
    }
}
