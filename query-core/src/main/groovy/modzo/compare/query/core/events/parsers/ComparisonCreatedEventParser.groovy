package modzo.compare.query.core.events.parsers

import modzo.compare.query.core.domain.events.ComparisonCreated
import modzo.compare.query.core.domain.events.services.EventDeserializer
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.query.core.domain.item.commands.comparison.CreateComparison
import modzo.compare.query.core.domain.item.commands.comparison.CreateComparisonHandler
import org.springframework.stereotype.Component

@Component
class ComparisonCreatedEventParser implements EventParser {

    private final CreateComparisonHandler createItemHandler
    private final EventDeserializer eventDeserializer

    ComparisonCreatedEventParser(CreateComparisonHandler createItemHandler, EventDeserializer eventDeserializer) {
        this.createItemHandler = createItemHandler
        this.eventDeserializer = eventDeserializer
    }

    @Override
    String type() {
        return ComparisonCreated.NAME
    }

    @Override
    void parse(EventService.RetrievedEvent event) {
        ComparisonCreated comparisonCreated = eventDeserializer.deserialize(event, ComparisonCreated)
        createItemHandler.handle(
                new CreateComparison(
                        uniqueId: comparisonCreated.uniqueId,
                        firstItemUniqueId: comparisonCreated.firstItemUniqueId,
                        secondItemUniqueId: comparisonCreated.secondItemUniqueId
                )
        )
    }
}
