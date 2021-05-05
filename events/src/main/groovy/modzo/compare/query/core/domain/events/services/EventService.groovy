package modzo.compare.query.core.domain.events.services

import groovy.transform.ToString;

interface EventService {

    void publish(Event event)

    Optional<RetrievedEvent> retrieveNext(Long currentEventId)

    @ToString(includeFields = true)
    static class Event {
        String uniqueId
        String type
        String data
    }

    @ToString(includeFields = true)
    static class RetrievedEvent extends Event {
        long id
    }
}
