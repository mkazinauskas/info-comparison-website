package modzo.compare.query.core.domain.item.commands.photo

import modzo.compare.query.core.domain.item.Items
import modzo.compare.query.core.domain.validators.DomainException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreatePhotoValidator {

    @Autowired
    private Items items

    void validate(CreatePhoto command) {
        if (!command.uniqueId) {
            throw new DomainException('PHOTO_UNIQUE_ID_HAS_TO_BE_PRESENT', "Photo unique id has to be present")
        }

        if (!items.findByUniqueId(command.itemUniqueId).isPresent()) {
            throw new DomainException('ITEM_WITH_UNIQUE_ID_DOES_NOT_EXIST', "Item with unique id does not exists")
        }

        if (!command.name) {
            throw new DomainException('PHOTO_NAME_HAS_TO_BE_PRESENT', "Photo name has to be present")
        }

        if (!command.description) {
            throw new DomainException('PHOTO_DESCRIPTION_HAS_TO_BE_PRESENT', "Photo description has to be present")
        }

        if (!command.urlAsMD5) {
            throw new DomainException('PHOTO_URL_AS_MD5_HAS_TO_BE_PRESENT', "Photo url as MD5 has to be present")
        }
    }
}
