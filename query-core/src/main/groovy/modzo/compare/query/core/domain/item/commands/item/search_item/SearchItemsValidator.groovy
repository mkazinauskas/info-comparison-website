package modzo.compare.query.core.domain.item.commands.item.search_item

import modzo.compare.query.core.domain.validators.DomainException
import org.springframework.stereotype.Component

import static org.apache.commons.lang.StringUtils.isBlank

@Component
class SearchItemsValidator {
    void validate(SearchItems command) {
        if (isBlank(command.name)) {
            throw new DomainException('SEARCH_ITEM_NAME_IS_NOT_PRESENT', 'Search item name has to be present')
        }
    }
}
