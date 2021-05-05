package modzo.compare.query.core.domain.parser.services

import modzo.compare.query.core.domain.parser.ParsedEvent
import modzo.compare.query.core.domain.parser.ParsedEvents
import org.elasticsearch.search.sort.SortBuilders
import org.elasticsearch.search.sort.SortOrder
import org.springframework.data.domain.Page
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery

@Service
class LatestParsedEventService {
    private final ParsedEvents parsedEvents

    LatestParsedEventService(ParsedEvents parsedEvents) {
        this.parsedEvents = parsedEvents
    }

    Optional<ParsedEvent> latestParsedEvent() {
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())

        searchQuery.withSort(SortBuilders.fieldSort('id').unmappedType('long').order(SortOrder.DESC))
        Page<ParsedEvent> events = parsedEvents.search(searchQuery.build())
        if (events.hasContent()) {
            return Optional.of(events.content.first())
        } else {
            return Optional.empty()
        }

    }
}
