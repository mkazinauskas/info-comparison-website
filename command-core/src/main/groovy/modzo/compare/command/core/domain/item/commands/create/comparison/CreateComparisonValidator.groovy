package modzo.compare.command.core.domain.item.commands.create.comparison

import groovy.transform.PackageScope
import modzo.compare.command.core.domain.item.Comparisons
import modzo.compare.command.core.domain.item.Items
import modzo.compare.command.core.domain.validators.DomainException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@PackageScope
class CreateComparisonValidator {

    @Autowired
    private Items items

    @Autowired
    private Comparisons comparisons

    void validate(CreateComparison command) {
        if (!items.findByUniqueId(command.firstItemUniqueId).isPresent()) {
            throw new DomainException(
                    'ITEM_DOES_NOT_EXIST',
                    "Item with unique id = `$command.firstItemUniqueId` does not exist")
        }
        if (!items.findByUniqueId(command.secondItemUniqueId).isPresent()) {
            throw new DomainException(
                    'ITEM_DOES_NOT_EXIST',
                    "Item with unique id = `$command.secondItemUniqueId` does not exist")
        }

        if (comparisons.findByFirstItemUniqueIdAndSecondItemUniqueId(command.firstItemUniqueId, command.secondItemUniqueId).isPresent()) {
            throw new DomainException(
                    'COMPARISON_ALREADY_EXIST',
                    "Comparison with = `$command.firstItemUniqueId` and `$command.secondItemUniqueId` already exist")
        }
    }
}
