package modzo.compare.query.core.domain.item.commands.comparison

import modzo.compare.query.core.domain.item.Comparisons
import modzo.compare.query.core.domain.validators.DomainException
import org.springframework.stereotype.Component

@Component
class CreateComparisonValidator {

    private final Comparisons comparisons

    CreateComparisonValidator(Comparisons comparisons) {
        this.comparisons = comparisons
    }

    void validate(CreateComparison createComparison) {
        if (!createComparison.uniqueId) {
            throw new DomainException('COMPARISON_DOES_NOT_HAVE_UNIQUE_ID', 'Comparison does not have uniqueId')
        }
        comparisons.findByUniqueId(createComparison.uniqueId).ifPresent {
            item -> throw new DomainException('COMPARISON_ALREADY_EXISTS',
                    "Comparison with uniqueId `$createComparison.uniqueId` already exists")
        }
    }
}
