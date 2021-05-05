package modzo.compare.query.core.domain.item.commands.comparison

import modzo.compare.query.core.domain.item.Comparison
import modzo.compare.query.core.domain.item.Comparisons
import org.springframework.stereotype.Component

@Component
class CreateComparisonHandler {

    private final Comparisons comparisons

    private final CreateComparisonValidator validator

    CreateComparisonHandler(Comparisons comparisons, CreateComparisonValidator validator) {
        this.comparisons = comparisons
        this.validator = validator
    }

    void handle(CreateComparison createComparison) {
        validator.validate(createComparison)

        Comparison comparison = new Comparison().with {
            uniqueId = createComparison.uniqueId
            firstItemUniqueId = createComparison.firstItemUniqueId
            secondItemUniqueId = createComparison.secondItemUniqueId
            sequence = createComparison.sequence
            it
        }

        comparisons.save(comparison)
    }
}
