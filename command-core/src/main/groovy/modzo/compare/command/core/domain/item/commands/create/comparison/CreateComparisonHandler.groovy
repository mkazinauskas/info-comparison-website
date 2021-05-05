package modzo.compare.command.core.domain.item.commands.create.comparison

import modzo.compare.command.core.domain.item.Comparison
import modzo.compare.command.core.domain.item.Comparisons
import modzo.compare.command.core.domain.item.Item
import modzo.compare.command.core.domain.item.Items
import modzo.compare.query.core.domain.events.services.EventPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreateComparisonHandler {

    @Autowired
    private Comparisons comparisons

    @Autowired
    private CreateComparisonValidator validator

    @Autowired
    private EventPublisher eventPublisher

    @Transactional
    void handle(CreateComparison command) {
        validator.validate(command)

        Comparison comparison = new Comparison().with {
            uniqueId = command.uniqueId
            firstItemUniqueId = command.firstItemUniqueId
            secondItemUniqueId = command.secondItemUniqueId
            sequence = (comparisons.count() + 1) * 10

            it
        }

        def savedComparison = comparisons.saveAndFlush(comparison)
        eventPublisher.publish(command.asEvent(savedComparison))
    }
}
