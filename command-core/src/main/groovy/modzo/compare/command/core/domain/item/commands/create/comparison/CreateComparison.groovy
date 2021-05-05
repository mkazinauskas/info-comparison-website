package modzo.compare.command.core.domain.item.commands.create.comparison

import modzo.compare.command.core.domain.item.Comparison
import modzo.compare.query.core.domain.events.ComparisonCreated

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic

class CreateComparison {

    String uniqueId = randomAlphabetic(40)

    String firstItemUniqueId

    String secondItemUniqueId

    CreateComparison(String firstItemUniqueId, String secondItemUniqueId) {
        this.firstItemUniqueId = firstItemUniqueId
        this.secondItemUniqueId = secondItemUniqueId
    }

    ComparisonCreated asEvent(Comparison comparison) {
        return new ComparisonCreated(uniqueId, firstItemUniqueId, secondItemUniqueId, comparison.sequence)
    }
}
